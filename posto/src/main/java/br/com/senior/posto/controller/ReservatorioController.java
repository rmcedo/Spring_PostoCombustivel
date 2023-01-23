package br.com.senior.posto.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.senior.posto.enums.TipoCombustivel;
import br.com.senior.posto.reservatorio.DadosAtualizacaoReservatorio;
import br.com.senior.posto.reservatorio.DadosCadastroReservatorio;
import br.com.senior.posto.reservatorio.DadosListagemReservatorio;
import br.com.senior.posto.reservatorio.Reservatorio;
import br.com.senior.posto.reservatorio.ReservatorioService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/reservatorios")
public class ReservatorioController {

	@Autowired
	private ReservatorioService service;

	@PostMapping
	@Transactional
	@ResponseStatus(HttpStatus.CREATED)
	public DadosListagemReservatorio cadastrar(@RequestBody DadosCadastroReservatorio dados) {
		return service.cadastrarReservatorio(dados);

	}

	@GetMapping
	public ResponseEntity<Page<DadosListagemReservatorio>> listar(@PageableDefault(size = 10) Pageable paginacao) {
		Page page = service.listar(paginacao);
		return ResponseEntity.ok(page);

	}

	@PutMapping
	@Transactional
	public ResponseEntity<DadosListagemReservatorio> atualizar(@RequestBody DadosAtualizacaoReservatorio dados) {
		Reservatorio reservatorio = service.atualizar(dados);
		return ResponseEntity.ok(new DadosListagemReservatorio(reservatorio));

	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Reservatorio> delete(@PathVariable Long id) {
		service.deletar(id);
		return ResponseEntity.noContent().build();
	}
	@PutMapping("/reativar/{id}")
	@Transactional
	public ResponseEntity<Reservatorio> reativar(@PathVariable Long id) {
		service.reativar(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/capacidade/{capacidade}")
	@ResponseStatus(HttpStatus.OK)
	public List<Reservatorio> findByCapacidadeGreaterThan(@PathVariable("capacidade") BigDecimal capacidade) {
		return service.findByCapacidadeGreaterThan(capacidade);

	}

	@GetMapping("/tipo/{tipoCombustivel}")
	@ResponseStatus(HttpStatus.OK)
	public List<Reservatorio> findByTipoCombustivelEquals(
			@PathVariable("tipoCombustivel") TipoCombustivel tipoCombustivel) {
		return service.findByTipoCombustivelEquals(tipoCombustivel);

	}

}
