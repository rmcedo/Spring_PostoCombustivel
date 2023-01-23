package br.com.senior.posto.reservatorio;

import java.math.BigDecimal;

import br.com.senior.posto.enums.TipoCombustivel;
import br.com.senior.posto.fornecedor.DadosListagemFornecedor;

public record DadosListagemReservatorio(Long id, BigDecimal qtdCombustivel, TipoCombustivel tipoCombustivel,
		BigDecimal capacidade, DadosListagemFornecedor fornecedor, Boolean ativo) {

	public DadosListagemReservatorio(Reservatorio reservatorio) {
		this(reservatorio.getId(), reservatorio.getQtdCombustivel(), reservatorio.getTipoCombustivel(),
				reservatorio.getCapacidade(), new DadosListagemFornecedor(reservatorio.getIdFornecedor()),
				reservatorio.getAtivo());
	}
}
