package br.com.senior.posto.fornecedor;

import br.com.senior.posto.endereco.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoFornecedor(

		@NotNull
		Long id,

		String nome,
		
		String contato,
		
		String telefone,
		
		String cnpj,

		DadosEndereco endereco) {

}
		
		
		
