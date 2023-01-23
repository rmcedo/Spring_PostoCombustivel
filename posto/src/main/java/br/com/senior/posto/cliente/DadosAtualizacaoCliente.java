package br.com.senior.posto.cliente;

import br.com.senior.posto.endereco.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoCliente (
		@NotNull
        Long id,
        String nome,
        String telefone,
        String documento,
        DadosEndereco endereco
       
       ) {

}
