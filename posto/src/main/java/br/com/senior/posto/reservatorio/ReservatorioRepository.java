package br.com.senior.posto.reservatorio;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.senior.posto.enums.TipoCombustivel;

public interface ReservatorioRepository extends JpaRepository<Reservatorio, Long> {
	
	List<Reservatorio> findByCapacidadeGreaterThan(BigDecimal capacidade);
	
	List<Reservatorio> findByTipoCombustivelEquals(TipoCombustivel tipoCombustivel);
	

}
