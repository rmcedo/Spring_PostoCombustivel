package br.com.senior.posto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.senior.posto.fornecedor.DadosAtualizacaoFornecedor;
import br.com.senior.posto.fornecedor.DadosCadastroFornecedor;
import br.com.senior.posto.fornecedor.DadosListagemFornecedor;
import br.com.senior.posto.fornecedor.DadosVisualizacaoFornecedor;
import br.com.senior.posto.fornecedor.Fornecedor;
import br.com.senior.posto.fornecedor.FornecedorService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("fornecedor")
public class FornecedorController {

	
	private FornecedorService service;
	
	@Autowired
	public FornecedorController(FornecedorService service) {
		this.service = service;
	}
	
	
    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroFornecedor dadosCadastroFornecedor, UriComponentsBuilder uriBuilder) {
        Fornecedor fornecedor = service.cadastrar(dadosCadastroFornecedor);
        var uri = uriBuilder.path("/fornecedor/{id}").buildAndExpand(fornecedor.getIdFornecedor()).toUri();

        return ResponseEntity.created(uri).body(new DadosVisualizacaoFornecedor(fornecedor));
    }

    @GetMapping("/fornecedores")
    public ResponseEntity<Page<DadosListagemFornecedor>> listar(@PageableDefault(size = 5, sort = {"nome"})Pageable paginacao) {
        Page page = service.listar(paginacao);

        return ResponseEntity.ok(page);
	}

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoFornecedor dadosAtualizacaoFornecedor) {
        Fornecedor fornecedor = service.atualizar(dadosAtualizacaoFornecedor);

        return ResponseEntity.ok(new DadosVisualizacaoFornecedor(fornecedor));
    }
	

    @DeleteMapping("/{idFornecedor}")
    @Transactional
    public ResponseEntity deletar(@PathVariable long idFornecedor) {
        service.excluir(idFornecedor);

        return ResponseEntity.noContent().build();
	}

	@GetMapping("listar/{nome}")
	public ResponseEntity<Page<DadosListagemFornecedor>> buscarPorNome(@PathVariable String nome, @PageableDefault(size = 5, sort = {"nome"})Pageable paginacao) {
        Page page = service.buscarPorNome(nome, paginacao).map(DadosListagemFornecedor::new);

        return ResponseEntity.ok(page);
	}

	@GetMapping("/{idFornecedor}")
    public ResponseEntity detalhar(@PathVariable Long idFornecedor) {

        return ResponseEntity.ok(service.detalharFornecedor(idFornecedor));

	}
	
	  @GetMapping("buscar-por-cnpj/{cnpj}")
	  public ResponseEntity buscarPorCnpj(@PathVariable String cnpj) {

      return ResponseEntity.ok(service.buscarPorCnpj(cnpj));
	}
}