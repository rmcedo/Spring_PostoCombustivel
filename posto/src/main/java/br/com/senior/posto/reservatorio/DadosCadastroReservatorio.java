package br.com.senior.posto.reservatorio;

import java.math.BigDecimal;
import br.com.senior.posto.enums.TipoCombustivel;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroReservatorio(
		@NotNull
		Long idReservatorio,
		@NotNull
		BigDecimal qtdCombustivel,
		@NotNull
		TipoCombustivel tipoCombustivel,
		@NotNull
		BigDecimal capacidade,
		
		Boolean ativo,
		
		Long idFornecedor	
		
		) {
	


}
