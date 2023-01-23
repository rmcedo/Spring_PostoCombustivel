package br.com.senior.posto.bomba;

import br.com.senior.posto.enums.TipoCombustivel;
import br.com.senior.posto.reservatorio.DadosListagemReservatorio;

public record DadosListagemBomba(Long id, DadosListagemReservatorio idReservatorio, TipoCombustivel tipocombustivel) {
	
	public DadosListagemBomba (Bomba bomba) {
		this(bomba.getId(), new DadosListagemReservatorio(bomba.getIdReservatorio()), bomba.getTipoCombustivel());
	}

}
