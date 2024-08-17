package edu.ifba.saj.demo_crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ifba.saj.demo_crud.domain.entity.Pessoa;
import java.util.List;


public interface PessoaRepository extends JpaRepository<Pessoa, Long>  {

    List<Pessoa> findByNome(String nome);

}
