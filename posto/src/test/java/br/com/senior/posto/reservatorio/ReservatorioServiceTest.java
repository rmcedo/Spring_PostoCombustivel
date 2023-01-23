package br.com.senior.posto.reservatorio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.senior.posto.endereco.Endereco;
import br.com.senior.posto.enums.TipoCombustivel;
import br.com.senior.posto.fornecedor.Fornecedor;

@ExtendWith(MockitoExtension.class)
class ReservatorioServiceTest {

	@Mock
	private ReservatorioRepository repository;

	@InjectMocks
	private ReservatorioService service;

	@Test
	public void retornaListaDeReservatorios() {
		Pageable paginacao = PageRequest.of(0, 10);
		List<Reservatorio> reservatorios = criarListaDeReservatorios();

		when(repository.findAll(paginacao)).thenReturn(new PageImpl<>(reservatorios));

		Page<DadosListagemReservatorio> retorno = service.listar(paginacao);

		assertEquals(reservatorios.size(), retorno.getTotalElements());
		assertEquals(reservatorios.get(0).getCapacidade(), retorno.getContent().get(0).capacidade());
		assertEquals(reservatorios.get(1).getCapacidade(), retorno.getContent().get(1).capacidade());
	}

	@Test
	public void retornaReservatorio() {
		Reservatorio reservatorio = criarReservatorio();
		when(repository.getReferenceById(1L)).thenReturn(reservatorio);

		Reservatorio retorno = service.getById(1L);

		assertEquals(reservatorio.getId(), retorno.getId());
		assertEquals(reservatorio.getTipoCombustivel(), retorno.getTipoCombustivel());
		assertEquals(reservatorio.getCapacidade(), retorno.getCapacidade());
		assertEquals(reservatorio.getQtdCombustivel(), retorno.getQtdCombustivel());
	}

	@Test
	public void atualizaReservatorio() {
		DadosAtualizacaoReservatorio dados = new DadosAtualizacaoReservatorio(1L, new BigDecimal("999"),
				TipoCombustivel.DIESEL, new BigDecimal("1000"), false, null);
		Reservatorio reservatorio = criarReservatorio();
		when(repository.getReferenceById(1L)).thenReturn(reservatorio);

		var retorno = service.atualizar(dados);

		assertEquals(1l, retorno.getId());
		assertEquals(TipoCombustivel.DIESEL, retorno.getTipoCombustivel());
		assertEquals(new BigDecimal("1000"), retorno.getCapacidade());
		assertEquals(new BigDecimal("999"), retorno.getQtdCombustivel());
		assertTrue(retorno.getAtivo());
	}

	@Test
	public void retornaListaDeReservatoriosComCapacidadeMaior() {
		List<Reservatorio> reservatorios = criarListaDeReservatorios();

		when(repository.findByCapacidadeGreaterThan(new BigDecimal("150"))).thenReturn(reservatorios);

		List<Reservatorio> retorno = service.findByCapacidadeGreaterThan(new BigDecimal("150"));

		assertEquals(reservatorios.size(), retorno.size());
		assertEquals(reservatorios.get(0).getCapacidade(), retorno.get(0).getCapacidade());
		assertEquals(reservatorios.get(1).getCapacidade(), retorno.get(1).getCapacidade());
	}

	@Test
	public void retornaListaDeReservatoriosComTipoCombustivelEspecifico() {
		List<Reservatorio> reservatorios = criarListaDeReservatorios();

		when(repository.findByTipoCombustivelEquals(TipoCombustivel.GASOLINA)).thenReturn(reservatorios);

		List<Reservatorio> retorno = service.findByTipoCombustivelEquals(TipoCombustivel.GASOLINA);

		assertEquals(reservatorios.size(), retorno.size());
		assertEquals(reservatorios.get(0).getTipoCombustivel(), retorno.get(0).getTipoCombustivel());
		assertEquals(reservatorios.get(1).getTipoCombustivel(), retorno.get(1).getTipoCombustivel());
	}

	// -----------------> CRIAÇÃO DE RESERVATÓRIOS <-----------------

	private List<Reservatorio> criarListaDeReservatorios() {
		Endereco endereco = new Endereco("Rua abc", "bairro", "cep", "uf", "cidade", "num", "complemento");

		Fornecedor fornecedor = Fornecedor.builder().idFornecedor(1L).nome("Petrobras").contato("Josias")
				.telefone("47999884415").cnpj("00900800715").endereco(endereco).build();

		List<Reservatorio> reservatorios = new ArrayList<>();

		Reservatorio reservatorio1 = Reservatorio.builder().id(1l).ativo(true).capacidade(new BigDecimal("1000"))
				.qtdCombustivel(new BigDecimal("500")).idFornecedor(fornecedor)
				.tipoCombustivel(TipoCombustivel.GASOLINA).build();

		Reservatorio reservatorio2 = Reservatorio.builder().id(1l).ativo(true).capacidade(new BigDecimal("1500"))
				.qtdCombustivel(new BigDecimal("1000")).tipoCombustivel(TipoCombustivel.ETANOL).idFornecedor(fornecedor)
				.build();

		reservatorios.add(reservatorio1);

		reservatorios.add(reservatorio2);

		return reservatorios;

	}

	private Reservatorio criarReservatorio() {
		Endereco endereco = new Endereco("Rua abc", "bairro", "cep", "uf", "cidade", "num", "complemento");

		Fornecedor fornecedor = Fornecedor.builder().idFornecedor(1L).nome("Petrobras").contato("Josias")
				.telefone("47999884415").cnpj("00900800715").endereco(endereco).build();

		Reservatorio reservatorio = Reservatorio.builder().id(1l).ativo(true).capacidade(new BigDecimal("1000"))
				.qtdCombustivel(new BigDecimal("500")).idFornecedor(fornecedor)
				.tipoCombustivel(TipoCombustivel.GASOLINA).build();

		return reservatorio;

	}

}
