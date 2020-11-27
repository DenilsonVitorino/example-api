package br.com.denilson.api.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto extends AbstractEntity {

    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
