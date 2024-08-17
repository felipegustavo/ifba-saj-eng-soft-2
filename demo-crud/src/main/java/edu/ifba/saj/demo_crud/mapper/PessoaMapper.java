package edu.ifba.saj.demo_crud.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ifba.saj.demo_crud.util.DateUtils;

import edu.ifba.saj.demo_crud.domain.dto.PessoaDTO;
import edu.ifba.saj.demo_crud.domain.entity.Pessoa;

@Mapper(componentModel = "spring")
public abstract class PessoaMapper {

    @Autowired
    protected DateUtils dateUtils;

    @Mapping(target = "idade", expression = "java(dateUtils.getIdadeEmAnos(source.getDataNascimento()))")
    public abstract PessoaDTO toPessoaDTO(Pessoa source);

    public abstract Pessoa toPessoa(PessoaDTO source);

}
