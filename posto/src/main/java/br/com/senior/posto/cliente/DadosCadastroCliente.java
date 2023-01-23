package br.com.senior.posto.cliente;

import br.com.senior.posto.endereco.DadosEndereco;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


public record DadosCadastroCliente (
		
		@NotBlank
		String nome,
		String telefone,
		@NotBlank
		String documento,
		DadosEndereco endereco){
		

}


