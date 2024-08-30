package edu.ifba.saj.demo_crud.mapper;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import edu.ifba.saj.demo_crud.domain.dto.PessoaDTO;
import edu.ifba.saj.demo_crud.domain.entity.Pessoa;

@Mapper(componentModel = "spring")
public abstract class PessoaMapper {

    @Mapping(target = "idade", expression = "java(getIdadeEmAnos(source.getDataNascimento()))")
    public abstract PessoaDTO toPessoaDTO(Pessoa source);

    public abstract Pessoa toPessoa(PessoaDTO source);

    public Long getIdadeEmAnos(LocalDate dataAniversario) {
        return ChronoUnit.YEARS.between(dataAniversario, LocalDate.now());
    }

}
