package br.com.senior.posto.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.senior.posto.fornecedor.Fornecedor;
import br.com.senior.posto.funcionario.Funcionario;
import br.com.senior.posto.reservatorio.Reservatorio;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	
	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	public Cliente cadastrar(DadosCadastroCliente dadosCadastroCliente) {
		if (clienteRepository.existsByDocumento(dadosCadastroCliente.documento())) {
			throw new IllegalArgumentException("CPF ja cadastrado!");
		}
		Cliente cliente = new Cliente(dadosCadastroCliente);
		clienteRepository.save(cliente);
		return cliente;

	}

	public DadosVisualizacaoCliente detalharCliente(Long id) {
		return new DadosVisualizacaoCliente(clienteRepository.getReferenceById(id));
	}

	public void deletar(Long id) {
		clienteRepository.delete(clienteRepository.getReferenceById(id));
	}

	public Page<DadosListagemCliente> listar(@PageableDefault Pageable paginacao) {
		return clienteRepository.findAllByAtivoTrue(paginacao).map(DadosListagemCliente::new);
	}

	public Cliente atualizar(DadosAtualizacaoCliente dados) {
			Cliente cliente = clienteRepository.getReferenceById(dados.id());
			cliente.atualizarInformacoes(dados);
			return cliente;

	}

	
	
	public Cliente getById(Long id) {
		return clienteRepository.getReferenceById(id);
	}
	
	public Cliente buscarPorDocumento(String documento) {
	    return clienteRepository.findByDocumento(documento);
	}
	
	public Page<Cliente> buscarPorNome(String nome, Pageable paginacao) {
	    return clienteRepository.findByNome(nome, paginacao);
	} 
	
	public Cliente buscarClienteComMaiorNumeroDeRegistros() {
        return clienteRepository.findClienteComMaiorNumeroDeRegistros();
	    }
	
	
}
