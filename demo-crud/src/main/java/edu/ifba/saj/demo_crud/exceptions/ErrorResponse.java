package edu.ifba.saj.demo_crud.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private String titulo;
    private String descricao;
    private List<String> campos;

}
