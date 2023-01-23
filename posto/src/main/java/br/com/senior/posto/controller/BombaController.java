package br.com.senior.posto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.util.UriComponentsBuilder;

import br.com.senior.posto.bomba.Bomba;
import br.com.senior.posto.bomba.BombaService;
import br.com.senior.posto.bomba.DadosAtualizacaoBomba;
import br.com.senior.posto.bomba.DadosCadastroBomba;
import br.com.senior.posto.bomba.DadosListagemBomba;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/bomba")
public class BombaController {
	
	private BombaService service;
	
	@Autowired
	public BombaController (BombaService service) {
		this.service = service;
	}
	
	@PostMapping
	@Transactional
	@ResponseStatus(HttpStatus.CREATED)
	public DadosListagemBomba cadastrar(@RequestBody @Valid DadosCadastroBomba dados) {
		return service.cadastrar(dados);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<DadosListagemBomba> listar(Pageable paginacao){
		return service.listar(paginacao);
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoBomba dados) {
		Bomba bomba = service.atualizar(dados);
		
		 return ResponseEntity.ok(new DadosAtualizacaoBomba(bomba));
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity ativar(@PathVariable Long id) {
		Bomba bomba = service.ativar(id);
		
		 return ResponseEntity.ok(new DadosAtualizacaoBomba(bomba));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity excluir(@PathVariable Long id) {
		service.inativar(id);
		
		return ResponseEntity.noContent().build();
	}
	
	

}
