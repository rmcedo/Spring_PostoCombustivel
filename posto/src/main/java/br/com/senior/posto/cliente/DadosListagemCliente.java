package br.com.senior.posto.cliente;

import br.com.senior.posto.endereco.DadosEndereco;
import br.com.senior.posto.endereco.Endereco;

import java.time.LocalDate;

public record DadosListagemCliente(Long id, String nome, Endereco endereco, String telefone, String documento) {

    public DadosListagemCliente (Cliente cliente){
        this(cliente.getId(), cliente.getNome(), cliente.getEndereco(), cliente.getTelefone(), cliente.getDocumento());
    }

}
