package br.com.senior.posto.bomba;

import br.com.senior.posto.reservatorio.Reservatorio;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoBomba (
		@NotNull
		Long id,
		@NotNull
		Long idReservatorio
		){

	public DadosAtualizacaoBomba(Bomba bomba) {
		this(bomba.getId(), bomba.getIdReservatorio().getId());
	}

	
}
