package edu.ifba.saj.demo_crud.domain.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PessoaDTO {

    private Long id;

    @NotEmpty
    @Size(max = 100)
    private String nome;

    @NotNull
    private LocalDate dataNascimento;

    private Long idade;


}
