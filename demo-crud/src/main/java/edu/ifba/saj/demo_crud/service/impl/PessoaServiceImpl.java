package edu.ifba.saj.demo_crud.service.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.ifba.saj.demo_crud.domain.dto.PessoaDTO;
import edu.ifba.saj.demo_crud.domain.entity.Pessoa;
import edu.ifba.saj.demo_crud.exceptions.NotFoundException;
import edu.ifba.saj.demo_crud.mapper.PessoaMapper;
import edu.ifba.saj.demo_crud.repository.PessoaRepository;
import edu.ifba.saj.demo_crud.service.PessoaService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository repository;
    private final PessoaMapper mapper;

    @Override
    public List<PessoaDTO> findAll() {
        List<Pessoa> pessoas = repository.findAll();
        List<PessoaDTO> result = new ArrayList<>();
        
        pessoas.forEach(p -> result.add(mapper.toPessoaDTO(p)));
        return result;
    }

    @Override
    public PessoaDTO findById(Long id) {
        Optional<Pessoa> p = repository.findById(id);
        if (p.isPresent()) {
            return mapper.toPessoaDTO(p.get());
        }
        return null;
    }

    @Override
    public List<PessoaDTO> findByNome(String nome) {
        List<Pessoa> pessoas = repository.findByNome(nome);
        List<PessoaDTO> result = new ArrayList<>();
        
        pessoas.forEach(p -> result.add(mapper.toPessoaDTO(p)));
        return result;
    }

    @Override
    public void remove(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Pessoa não encontrada com id " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public PessoaDTO create(PessoaDTO pessoaDTO) {
        Pessoa pessoa = repository.save(mapper.toPessoa(pessoaDTO));
        return mapper.toPessoaDTO(pessoa);
    }

    @Override
    public PessoaDTO update(PessoaDTO p) {
        if (!repository.existsById(p.getId())) {
            throw new NotFoundException("Pessoa não encontrada com id " + p.getId());
        }
        return create(p);
    }

}
