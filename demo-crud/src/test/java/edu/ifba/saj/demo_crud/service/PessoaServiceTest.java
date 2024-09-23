package edu.ifba.saj.demo_crud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import edu.ifba.saj.demo_crud.builder.PessoaBuilder;
import edu.ifba.saj.demo_crud.builder.PessoaDTOBuilder;
import edu.ifba.saj.demo_crud.domain.dto.PessoaDTO;
import edu.ifba.saj.demo_crud.mapper.PessoaMapper;
import edu.ifba.saj.demo_crud.mapper.PessoaMapperImpl;
import edu.ifba.saj.demo_crud.repository.PessoaRepository;
import edu.ifba.saj.demo_crud.service.impl.PessoaServiceImpl;
import edu.ifba.saj.demo_crud.domain.entity.Pessoa;
import edu.ifba.saj.demo_crud.exceptions.NotFoundException;

@ContextConfiguration(classes = {PessoaServiceImpl.class, PessoaMapperImpl.class})
@ExtendWith(SpringExtension.class)
public class PessoaServiceTest {

    @Autowired
    private PessoaService service;

    @Autowired
    private PessoaMapper mapper;

    @MockBean
    private PessoaRepository repository;

    @Test
    void deveBuscarTodos() throws Exception {
        var pessoa = PessoaBuilder.buildPessoa();

        given(repository.findAll()).willReturn(List.of(pessoa));

        List<PessoaDTO> response = service.findAll();
        var pessoaEsperada = response.get(0);

        comparaPessoa(pessoaEsperada, pessoa);
    }

    @Test
    void deveBuscarUmaPessoaPorId() throws Exception {
        var pessoa = PessoaBuilder.buildPessoa();
        given(repository.findById(1L)).willReturn(Optional.of(pessoa));
        comparaPessoa(service.findById(1L), pessoa);
    }

    @Test
    void deveNaoAcharPessoaPorId() throws Exception {
        given(repository.findById(1L)).willReturn(Optional.empty());
        var pessoa = service.findById(1L);
        assertEquals(pessoa, null);
    }

    @Test
    void deveBuscarTodosPorNome() throws Exception {
        var pessoa = PessoaBuilder.buildPessoa();
        var pessoa2 = PessoaBuilder.buildPessoa();
        pessoa2.setId(2L);
        pessoa2.setDataNascimento(LocalDate.of(1980, 1, 20));

        var nome = "Felipe";

        given(repository.findByNome(nome)).willReturn(List.of(pessoa, pessoa2));

        List<PessoaDTO> response = service.findByNome(nome);
        var pessoaEsperada = response.get(0);

        assertEquals(response.size(), 2);
        comparaPessoa(pessoaEsperada, pessoa);
    }

    @Test
    void deveNaoAcharPessoaPorNome() throws Exception {
        var nome = "Felipe";
        given(repository.findByNome(nome)).willReturn(new ArrayList<Pessoa>());

        List<PessoaDTO> response = service.findByNome(nome);

        assertEquals(response.size(), 0);
    }

    void comparaPessoa(PessoaDTO pessoaEsperada, Pessoa pessoaMock) {
        assertEquals(pessoaEsperada.getId(), 1L);
        assertEquals(pessoaEsperada.getNome(), "Felipe");
        assertEquals(pessoaEsperada.getDataNascimento(), LocalDate.of(1992, 8, 31));
        assertEquals(pessoaEsperada.getIdade(), mapper.getIdadeEmAnos(pessoaMock.getDataNascimento()));
    }

    @Test
    void deveRemoverPessoa() throws Exception {
        Long id = 1L;

        doNothing().when(repository).deleteById(id);
        given(repository.existsById(id)).willReturn(true);

        service.remove(id);

        verify(repository, times(1)).deleteById(id);
    }

    @Test 
    void deveGerarErrorAoTentarRemoverPessoa() throws Exception {
        Long id = 1L;
        given(repository.existsById(id)).willReturn(false);
        assertThrows(NotFoundException.class, () -> service.remove(id));
    }

    @Test
    void deveCriarPessoa() throws Exception {
        var pessoaEsperada = PessoaBuilder.buildPessoa();
        var pessoaDTO = PessoaDTOBuilder.buildRequest();

        given(repository.save(any(Pessoa.class))).willReturn(pessoaEsperada);

        var pessoaResponse = service.create(pessoaDTO);
        comparaPessoa(pessoaResponse, pessoaEsperada);       
    }

    @Test
    void deveGerarErrorAoAtualizarPessoa() throws Exception {
        var pessoaDTO = PessoaDTOBuilder.buildRequest();

        given(repository.existsById(pessoaDTO.getId())).willReturn(false);
        assertThrows(NotFoundException.class, () -> service.update(pessoaDTO));       
    }

    @Test
    void deveAtualizarPessoa() throws Exception {
        var pessoaEsperada = PessoaBuilder.buildPessoa();
        var pessoaDTO = PessoaDTOBuilder.buildRequest();

        given(repository.existsById(pessoaDTO.getId())).willReturn(true);
        given(repository.save(any(Pessoa.class))).willReturn(pessoaEsperada);

        var pessoaResponse = service.update(pessoaDTO);
        comparaPessoa(pessoaResponse, pessoaEsperada);       
    }

}
