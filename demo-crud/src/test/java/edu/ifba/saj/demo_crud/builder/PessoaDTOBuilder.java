package edu.ifba.saj.demo_crud.builder;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import edu.ifba.saj.demo_crud.domain.dto.PessoaDTO;

public class PessoaDTOBuilder {


    public static PessoaDTO buildRequest() {
        var dataNascimento = LocalDate.of(1992, 8, 31);
        return PessoaDTO
                .builder()
                .id(1L)
                .nome("Felipe")
                .dataNascimento(dataNascimento)
                .idade(ChronoUnit.YEARS.between(dataNascimento, LocalDate.now()))
                .build();
    }

    public static PessoaDTO buildResponse() {
        var pessoa = buildRequest();
        pessoa.setId(null);
        pessoa.setIdade(null);
        return pessoa;
    }

}
