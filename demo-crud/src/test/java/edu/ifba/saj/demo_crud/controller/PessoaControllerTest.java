package edu.ifba.saj.demo_crud.controller;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static edu.ifba.saj.demo_crud.constants.ApiPathConstants.API_PESSOA;
import static org.mockito.Answers.valueOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.ifba.saj.demo_crud.builder.PessoaDTOBuilder;
import edu.ifba.saj.demo_crud.controller.impl.PessoaController;
import edu.ifba.saj.demo_crud.domain.dto.PessoaDTO;
import edu.ifba.saj.demo_crud.exceptions.DefaultControllerAdvice;
import edu.ifba.saj.demo_crud.exceptions.NotFoundException;
import edu.ifba.saj.demo_crud.service.PessoaService;

@ContextConfiguration(classes = {PessoaController.class, DefaultControllerAdvice.class})
@WebMvcTest(controllers = {PessoaController.class})
@AutoConfigureMockMvc
public class PessoaControllerTest {

    @MockBean
    private PessoaService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void deveCriarPessoaComSucesso() throws Exception {
        var request = PessoaDTOBuilder.buildRequest();
        var responseEsperada = PessoaDTOBuilder.buildResponse();

        given(service.create(Mockito.any(PessoaDTO.class)))
            .willReturn(responseEsperada);

        mockMvc.perform(
            post(API_PESSOA)
            .content(mapper.writeValueAsString(request))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(mapper.writeValueAsString(responseEsperada)));
    }

    @Test
    void deveRetornarErroComRequestInvalido() throws Exception {
        var request = new PessoaDTO();

        mockMvc.perform(
            post(API_PESSOA)
            .content(mapper.writeValueAsString(request))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void deveAtualizarComSucesso() throws Exception {
        var request = PessoaDTOBuilder.buildRequest();
        var responseEsperada = PessoaDTOBuilder.buildResponse();

        given(service.update(request)).willReturn(responseEsperada);

        mockMvc.perform(
            put(API_PESSOA + "/1")
            .content(mapper.writeValueAsString(request))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(mapper.writeValueAsString(responseEsperada)));
    }

    @Test
    void deveFalharAoTentarAtualizarPessoa() throws Exception {
        var request = PessoaDTOBuilder.buildRequest();
        given(service.update(any(PessoaDTO.class))).willThrow(new NotFoundException(""));

        mockMvc.perform(
            put(API_PESSOA + "/2")
            .content(mapper.writeValueAsString(request))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deveBuscarTodasAsPessoas() throws Exception {
        var p1 = PessoaDTOBuilder.buildResponse();
        p1.setId(1L);
        var p2 = PessoaDTOBuilder.buildResponse();
        p2.setId(2L);
        p2.setNome("João");

        var responseEsperada = List.of(p1, p2);

        given(service.findAll()).willReturn(responseEsperada);

        mockMvc.perform(
            get(API_PESSOA)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath("$[0].id").value("1"))
        .andExpect(jsonPath("$[1].nome").value("João"));
    }

    @Test
    void deveAcharUmaPessoa() throws Exception {
        var pessoa = PessoaDTOBuilder.buildRequest();
        given(service.findById(1L)).willReturn(pessoa);

        mockMvc.perform(
            get(API_PESSOA + "/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath("$.id").value("1"))
        .andExpect(jsonPath("$.nome").value("Felipe"));
    }
    
}
