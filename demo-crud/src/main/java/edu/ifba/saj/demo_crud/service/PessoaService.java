package edu.ifba.saj.demo_crud.service;

import edu.ifba.saj.demo_crud.domain.dto.PessoaDTO;
import java.util.List;

public interface PessoaService {

    PessoaDTO create(PessoaDTO p);

    PessoaDTO update(PessoaDTO p);

    void remove(Long id);

    List<PessoaDTO> findAll();

    PessoaDTO findById(Long id);

    List<PessoaDTO> findByNome(String nome);

}
