package com.teste.primeiroexemplo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import com.teste.primeiroexemplo.model.Produto;
import com.teste.primeiroexemplo.model.exception.ResourceNotFoundException;

@Repository
public class ProdutoRepository_old {

    private List<Produto> produtos = new ArrayList<Produto>();
    private Integer ultimoId = 0;

    /**
     * Método para retornar uma lista de produtos
     * 
     * @return Lista de produtos.
     */
    public List<Produto> obterTodos() {
        return produtos;
    }

    /**
     * Método que retorna o produto encontrado pelo seu ID.
     * 
     * @param id do produto que será localizado.
     * @return Retorna um produto caso seja encontrado.
     */
    public Optional<Produto> obterPorId(Integer id) {
        return produtos
                .stream()
                .filter(produto -> produto.getId() == id)
                .findFirst();
    }

    /**
     * Método para adicionar um novo produto na lista.
     * 
     * @param produto que sera adicionado.
     * @return o produto que foi adicionado na lista.
     */
    public Produto adicionar(Produto produto) {
        ultimoId++;

        produto.setId(ultimoId);
        produtos.add(produto);

        return produto;
    }

    /**
     * Método para atualizar o produto na lista.
     * 
     * @param produto a ser atualizado.
     * @return O Produto atualizado.
     */
    public Produto atualizar(Produto produto) {
        // Encontrar o produto na lista
        Optional<Produto> produtoEncontrado = obterPorId(produto.getId());

        if (produtoEncontrado.isEmpty()) {
            throw new ResourceNotFoundException("Produto não encontrado!");
        }

        // Remover o Produto Antigo da Lista
        deletar(produto.getId());

        // Adicionar o Produto Atualizado.
        produtos.add(produto);

        return produto;
    }

    /**
     * Método para deletar o produto por id
     * 
     * @param id do produto a ser deletado
     */
    public void deletar(Integer id) {
        produtos.removeIf(produto -> produto.getId() == id);
    }

}
