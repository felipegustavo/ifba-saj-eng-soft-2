package edu.ifba.saj.demo_crud.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static edu.ifba.saj.demo_crud.constants.ApiPathConstants.API_PESSOA;

import edu.ifba.saj.demo_crud.domain.dto.PessoaDTO;
import jakarta.validation.Valid;

@RequestMapping(API_PESSOA)
public interface PessoaControllerApi {

    @PostMapping
    PessoaDTO create(@RequestBody @Valid PessoaDTO pessoaDTO);
    
    @PutMapping("/{id}")
    PessoaDTO update(@PathVariable("id") Long id,
                            @RequestBody @Valid PessoaDTO pessoaDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Long id);

    @GetMapping
    List<PessoaDTO> getAll();

    @GetMapping("/{id}")
    ResponseEntity<PessoaDTO> getById(@PathVariable("id") Long id);

    @GetMapping("/nome/{nome}")
    public List<PessoaDTO> getAll(@PathVariable("nome") String nome);

}
