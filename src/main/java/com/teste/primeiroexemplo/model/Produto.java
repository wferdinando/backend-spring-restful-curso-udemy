package com.teste.primeiroexemplo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Produto {

    // #region Atributos
    @Id // marca esse atributo como chave primaria da tabela
    @GeneratedValue(strategy = GenerationType.AUTO) // deixa a cargo do banco de dados como deve ser a incremetação do ID
    private Integer id;

    private String nome;

    private Integer quantidade;

    private Double valor;

    private String observacao;
    // #endregion Atributos

    // #region Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    // #endregion Getters and Setters

}
