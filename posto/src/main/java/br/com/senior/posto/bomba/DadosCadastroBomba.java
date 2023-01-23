package br.com.senior.posto.bomba;

import br.com.senior.posto.reservatorio.Reservatorio;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroBomba (
		@NotNull
		Long idReservatorio
		){

}
