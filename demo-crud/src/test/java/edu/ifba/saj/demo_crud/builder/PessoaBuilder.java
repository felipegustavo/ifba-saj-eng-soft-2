package edu.ifba.saj.demo_crud.builder;

import java.time.LocalDate;

import edu.ifba.saj.demo_crud.domain.entity.Pessoa;

public final class PessoaBuilder {

    private PessoaBuilder() {}

    public static Pessoa buildPessoa() {
        return Pessoa
                .builder()
                .id(1L)
                .nome("Felipe")
                .dataNascimento(LocalDate.of(1992, 8, 31))
                .build();
    }
    
}
