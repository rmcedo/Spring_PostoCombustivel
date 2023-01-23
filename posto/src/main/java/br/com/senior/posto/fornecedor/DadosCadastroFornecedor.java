package br.com.senior.posto.fornecedor;

import br.com.senior.posto.endereco.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroFornecedor(
	
	 @NotBlank
     String nome,
     @NotBlank
     String contato,
     @NotBlank
     String telefone,
     @NotBlank
     @Pattern(regexp = "\\d{11}")
     String cnpj,

     @NotNull @Valid DadosEndereco endereco) {

}
