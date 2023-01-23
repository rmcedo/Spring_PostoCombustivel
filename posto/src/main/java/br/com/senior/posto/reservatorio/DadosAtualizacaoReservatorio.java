package br.com.senior.posto.reservatorio;

import java.math.BigDecimal;
import br.com.senior.posto.enums.TipoCombustivel;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoReservatorio(
		
		@NotNull
		Long id,
		
		BigDecimal qtdCombustivel,
		
		TipoCombustivel tipoCombustivel,
		
		BigDecimal capacidade,
		
		Boolean ativo,
		
		Long idFornecedor) {


}
