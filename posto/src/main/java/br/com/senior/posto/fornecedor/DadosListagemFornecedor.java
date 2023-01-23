package br.com.senior.posto.fornecedor;

public record DadosListagemFornecedor(Long idFornecedor, String nome, String contato, String telefone, String cnpj) {
	
	public DadosListagemFornecedor(Fornecedor fornecedor){
        this(fornecedor.getIdFornecedor(), fornecedor.getNome(), fornecedor.getContato(), fornecedor.getTelefone(), fornecedor.getCnpj());
    }

}
