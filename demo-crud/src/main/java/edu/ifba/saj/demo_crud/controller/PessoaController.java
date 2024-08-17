package edu.ifba.saj.demo_crud.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ifba.saj.demo_crud.domain.dto.PessoaDTO;
import edu.ifba.saj.demo_crud.service.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/pessoa")
@RequiredArgsConstructor
@Validated
public class PessoaController {

    private final PessoaService service;

    @PostMapping
    public PessoaDTO create(@RequestBody @Valid PessoaDTO pessoaDTO) {
        return service.create(pessoaDTO);
    }
    
    @PutMapping("/{id}")
    public PessoaDTO update(@PathVariable("id") Long id,
                            @RequestBody @Valid PessoaDTO pessoaDTO) {
        pessoaDTO.setId(id);
        return service.update(pessoaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.remove(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<PessoaDTO> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> getById(@PathVariable("id") Long id) {
        PessoaDTO p = service.findById(id);
        if (p != null) {
            return ResponseEntity.ok(p);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nome/{nome}")
    public List<PessoaDTO> getAll(@PathVariable("nome") String nome) {
        return service.findByNome(nome);
    }

}
