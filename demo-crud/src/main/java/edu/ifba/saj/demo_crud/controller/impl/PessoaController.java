package edu.ifba.saj.demo_crud.controller.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import edu.ifba.saj.demo_crud.controller.PessoaControllerApi;
import edu.ifba.saj.demo_crud.domain.dto.PessoaDTO;
import edu.ifba.saj.demo_crud.service.PessoaService;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PessoaController implements PessoaControllerApi {

    private final PessoaService service;

    public PessoaDTO create(PessoaDTO pessoaDTO) {
        return service.create(pessoaDTO);
    }
    
    public PessoaDTO update(Long id, PessoaDTO pessoaDTO) {
        pessoaDTO.setId(id);
        return service.update(pessoaDTO);
    }

    public ResponseEntity<Void> delete(Long id) {
        service.remove(id);
        return ResponseEntity.noContent().build();
    }

    public List<PessoaDTO> getAll() {
        return service.findAll();
    }

    public ResponseEntity<PessoaDTO> getById(Long id) {
        PessoaDTO p = service.findById(id);
        if (p != null) {
            return ResponseEntity.ok(p);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public List<PessoaDTO> getAll(String nome) {
        return service.findByNome(nome);
    }

}
