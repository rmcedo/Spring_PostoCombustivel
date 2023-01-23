package br.com.senior.posto.fornecedor;

import br.com.senior.posto.endereco.Endereco;

public record DadosVisualizacaoFornecedor(Long idFornecedor, String nome, String contato, String telefone, String cnpj, Endereco endereco ) {

	public DadosVisualizacaoFornecedor(Fornecedor fornecedor) {
		this(fornecedor.getIdFornecedor(), fornecedor.getNome(), fornecedor.getContato(), fornecedor.getTelefone(), fornecedor.getCnpj(), fornecedor.getEndereco());
	}
}
