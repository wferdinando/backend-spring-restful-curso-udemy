package com.teste.primeiroexemplo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teste.primeiroexemplo.model.Produto;
import com.teste.primeiroexemplo.model.exception.ResourceNotFoundException;
import com.teste.primeiroexemplo.repository.ProdutoRepository;
import com.teste.primeiroexemplo.shared.ProdutoDTO;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    /**
     * Método para retornar uma lista de produtos
     * 
     * @return Lista de produtos.
     */
    public List<ProdutoDTO> obterTodos() {

        // Retorna uma Lista de ProdutoModel
        List<Produto> produtos = produtoRepository.findAll();
        // Converte um ModelProduto para um ProdutoDTO
        return produtos.stream()
                .map(produto -> new ModelMapper().map(produto, ProdutoDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Método que retorna o produto encontrado pelo seu ID.
     * 
     * @param id do produto que será localizado.
     * @return Retorna um produto caso seja encontrado.
     */
    public Optional<ProdutoDTO> obterPorId(Integer id) {

        // Obtem um Optional de ProdutoModel pelo id
        Optional<Produto> produto = produtoRepository.findById(id);

        // Caso não encontrado lança uma exception
        if (produto.isEmpty()) {
            throw new ResourceNotFoundException("Produto com o id: " + id + " não encontrado!");
        }

        // Converte um Optional<Produto> em um ProdutoDTO
        ProdutoDTO dto = new ModelMapper().map(produto.get(), ProdutoDTO.class);

        // Criando um Optional<ProdutoDTO>
        return Optional.of(dto);

    }

    /**
     * Método para adicionar um novo produto na lista.
     * 
     * @param produto que sera adicionado.
     * @return o produto que foi adicionado na lista.
     */
    public ProdutoDTO adicionar(ProdutoDTO produtoDTO) {
        // Poderia ter alguma regra de negócio para validar o produto
        // Ex. Verificar se a quantidade é maior que 0.

        // Remove o ID
        produtoDTO.setId(null);

        // Cria um objeto de mapeamento.
        ModelMapper mapper = new ModelMapper();

        // Converter o ProdutDTO em um ProdutoModel
        Produto produto = mapper.map(produtoDTO, Produto.class);

        // Salvar o ProdutoModel no banco de dados
        produto = produtoRepository.save(produto);
        produtoDTO.setId(produto.getId());

        // Retornar o ProdutoDTO atualizado.
        return produtoDTO;
    }

    /**
     * Método para atualizar o produto na lista.
     * 
     * @param produto a ser atualizado.
     * @param id      do produto.
     * @return O Produto atualizado.
     */
    public ProdutoDTO atualizar(Integer id, ProdutoDTO produtoDTO) {
        // Passar o id para o produtoDTO
        produtoDTO.setId(id);
        // Criar um objeto de mapeamento ModelMapper().
        ModelMapper mapper = new ModelMapper();
        // Converter o ProdutoDTO e um ProdutoModel.
        Produto produto = mapper.map(produtoDTO, Produto.class);
        // Atualizar o produto no banco de dados.
        produtoRepository.save(produto);
        // Retornar o produtoDTO atualizado.
        return produtoDTO;
    }

    /**
     * Método para deletar o produto por id
     * 
     * @param id do produto a ser deletado
     */
    public void deletar(Integer id) {
        // Verificar se o produto existe
        Optional<Produto> produto = produtoRepository.findById(id);

        // Se não existir o produto ou id lança uma exception
        if (produto.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Não foi possível deletar o Produto com o id: " + id + ". Produto não existe!");
        }
        produtoRepository.deleteById(id);
    }
}
