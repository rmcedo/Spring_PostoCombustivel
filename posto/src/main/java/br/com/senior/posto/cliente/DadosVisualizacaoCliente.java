package br.com.senior.posto.cliente;


import br.com.senior.posto.endereco.Endereco;

public record DadosVisualizacaoCliente(Long id, String nome, String telefone, String documento, Endereco endereco) {
	
	   public DadosVisualizacaoCliente (Cliente cliente) {
	        this(cliente.getId(), cliente.getNome(), cliente.getTelefone(), cliente.getDocumento(),cliente.getEndereco());
	        		 
	    }

}
