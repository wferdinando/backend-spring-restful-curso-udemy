package com.teste.primeiroexemplo.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.primeiroexemplo.services.ProdutoService;
import com.teste.primeiroexemplo.shared.ProdutoDTO;
import com.teste.primeiroexemplo.view.model.ProdutoRequest;
import com.teste.primeiroexemplo.view.model.ProdutoResponse;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> obterTodos() {
        List<ProdutoDTO> produtos = produtoService.obterTodos();

        ModelMapper mapper = new ModelMapper();

        List<ProdutoResponse> resposta = produtos.stream()
                .map(produtoDTO -> mapper.map(produtoDTO, ProdutoResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProdutoResponse>> obterporId(@PathVariable Integer id) {

        Optional<ProdutoDTO> dto = produtoService.obterPorId(id);

        ProdutoResponse produto = new ModelMapper().map(dto.get(), ProdutoResponse.class);

        return new ResponseEntity<>(Optional.of(produto), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> adicionar(@RequestBody ProdutoRequest produtoReq) {
        // Instancio um Objeto ModelMapper
        ModelMapper mapper = new ModelMapper();
        // Converto o Produto que está vindo na requisição em um ProdutoDTO
        ProdutoDTO produtoDTO = mapper.map(produtoReq, ProdutoDTO.class);
        // Salvo os dados dentro do ProdutoDTO
        produtoDTO = produtoService.adicionar(produtoDTO);
        // Retona um ResponseEntity convertendo um produtoDTO em um ProdutoResponse
        return new ResponseEntity<>(mapper.map(produtoDTO, ProdutoResponse.class), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(@PathVariable Integer id, @RequestBody ProdutoRequest produtoReq) {

        ModelMapper mapper = new ModelMapper();
        ProdutoDTO produtoDTO = mapper.map(produtoReq, ProdutoDTO.class);

        produtoDTO = produtoService.atualizar(id, produtoDTO);

        return new ResponseEntity<>(mapper.map(produtoDTO, ProdutoResponse.class), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        produtoService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
