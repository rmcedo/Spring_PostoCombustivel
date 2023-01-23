package br.com.senior.posto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.senior.posto.cliente.Cliente;
import br.com.senior.posto.cliente.ClienteService;
import br.com.senior.posto.cliente.DadosAtualizacaoCliente;
import br.com.senior.posto.cliente.DadosCadastroCliente;
import br.com.senior.posto.cliente.DadosListagemCliente;
import br.com.senior.posto.cliente.DadosVisualizacaoCliente;
import br.com.senior.posto.fornecedor.Fornecedor;
import br.com.senior.posto.funcionario.DadosAtualizacaoFuncionario;
import br.com.senior.posto.funcionario.DadosListagemFuncionario;
import br.com.senior.posto.funcionario.DadosVisualizacaoFuncionario;
import br.com.senior.posto.funcionario.Funcionario;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

	private ClienteService clienteService;

	@Autowired
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@PostMapping("/cadastrar")
	@Transactional
	public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroCliente dadosCadastroCliente,
			UriComponentsBuilder uriBuilder) {
		Cliente cliente = clienteService.cadastrar(dadosCadastroCliente);
		var uri = uriBuilder.path("/cliente/{id}").buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).body(new DadosVisualizacaoCliente(cliente));
	}

	@GetMapping("/{idCliente}")
	public ResponseEntity detalhar(@PathVariable Long idCliente) {
		return ResponseEntity.ok(clienteService.detalharCliente(idCliente));
	}

	@DeleteMapping("/{idCliente}")
	@Transactional
	public ResponseEntity deletar(@PathVariable long idCliente) {
		clienteService.deletar(idCliente);

		return ResponseEntity.noContent().build();
	}

    @PutMapping("/atualizar/{idCliente}")
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoCliente dadosAtualizacaoCliente) {
        Cliente cliente = clienteService.atualizar(dadosAtualizacaoCliente);

        return ResponseEntity.ok(new DadosVisualizacaoCliente(cliente));
    }

	@GetMapping("/cliente")
	public ResponseEntity<Page<DadosListagemCliente>> listar(
			@PageableDefault(size = 5, sort = { "nome" }) Pageable paginacao) {
		Page page = clienteService.listar(paginacao);

		return ResponseEntity.ok(page);
	}
	
	@GetMapping("/cliente/{id}")
	public ResponseEntity<Cliente> clienteFindById(@PathVariable("id") Long id) {
		Cliente entity = clienteService.getById(id);
		return ResponseEntity.ok(entity);

	}
	
	  @GetMapping("buscar-por-documento/{documento}")
	  public ResponseEntity buscarPorDocumento(@PathVariable String documento) {
      return ResponseEntity.ok(clienteService.buscarPorDocumento(documento));
	}
	
	  @GetMapping("/buscar-por-nome/{nome}")
	  public ResponseEntity<Page<DadosListagemCliente>> buscarPorNome(@PathVariable String nome, @PageableDefault(size = 5, sort = {"nome"})Pageable paginacao) {
	  Page page = clienteService.buscarPorNome(nome, paginacao).map(DadosListagemCliente::new);

      return ResponseEntity.ok(page);
    }
	  
	   @GetMapping("maior-cliente")
	   public ResponseEntity buscarClienteComMaisRegistros() {
	     return ResponseEntity.ok(new DadosVisualizacaoCliente(clienteService.buscarClienteComMaiorNumeroDeRegistros()));
	} 
	
}
