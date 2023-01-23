package br.com.senior.posto.cliente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.senior.posto.endereco.DadosEndereco;
import br.com.senior.posto.endereco.Endereco;


@ExtendWith(MockitoExtension.class)
class ClienteTest {

	@InjectMocks
	ClienteService service;

	@Mock
	ClienteRepository repository;

	@Captor
	ArgumentCaptor<Cliente> clienteCaptor;

	@Test
	void deveriaCadastrarUmCliente() {

		DadosCadastroCliente dadosCadastroCliente = this.gerarDadosCadastroCliente();
		Cliente clienteCadastrado = new Cliente(dadosCadastroCliente);

		when(service.cadastrar(dadosCadastroCliente)).thenReturn(clienteCadastrado);

		Cliente clienteParaComparar = service.cadastrar(dadosCadastroCliente);

		verify(repository).save(clienteCadastrado);
		assertEquals(clienteParaComparar.getNome(), clienteCadastrado.getNome());
		assertEquals(clienteParaComparar.getTelefone(), clienteCadastrado.getTelefone());
		assertEquals(clienteParaComparar.getDocumento(), clienteCadastrado.getDocumento());
		assertEquals(clienteParaComparar.getEndereco().getLogradouro(), clienteCadastrado.getEndereco().getLogradouro());
		assertEquals(clienteParaComparar.getEndereco().getBairro(), clienteCadastrado.getEndereco().getBairro());
		assertEquals(clienteParaComparar.getEndereco().getCep(), clienteCadastrado.getEndereco().getCep());
		assertEquals(clienteParaComparar.getEndereco().getCidade(), clienteCadastrado.getEndereco().getCidade());
	}

	@Test
	public void deveriaRetornarClientePeloId() {
	    Cliente cliente = criarCliente();
	    when(repository.getReferenceById(1L)).thenReturn(cliente);

	    Cliente result = service.getById(1L);

	    assertEquals(1l, result.getId());
	    assertEquals(cliente.getNome(), result.getNome());
	    assertEquals(cliente.getTelefone(), result.getTelefone());
	    assertEquals(cliente.getDocumento(), result.getDocumento());
	}

	@Test
	public void deveriaRetornarUmaListaDeClientesAtivos() {
	    Pageable paginacao = PageRequest.of(0, 10);
	    List<Cliente> clientes = criarListaDeClientes();

	    when(repository.findAllByAtivoTrue(paginacao)).thenReturn(new PageImpl<>(clientes));

	    Page<DadosListagemCliente> result = service.listar(paginacao);

	    assertEquals(2, result.getTotalElements());
	    assertEquals(clientes.get(0).getNome(), result.getContent().get(0).nome());
	   assertEquals(clientes.get(1).getNome(), result.getContent().get(1).nome());
	}

	@Test
	public void deveriaAtualizarUmCliente() {
	    DadosAtualizacaoCliente dados = gerarAtualizacao();
	    when(repository.getReferenceById(1L)).thenReturn(this.cliente2);

	    Cliente result = service.atualizar(dados);

	   
	    assertEquals(cliente2.getId(), result.getId());
	    assertEquals(cliente2.getNome(), result.getNome());
	    assertEquals(cliente2.getTelefone(), result.getTelefone());
	    assertEquals(cliente2.getDocumento(), result.getDocumento());
	    verify(cliente2).atualizarInformacoes(gerarAtualizacao());
	}

	@Test
	void deveriaDeletarUmCliente() {
		Cliente cliente = criarCliente();

		Mockito.when(repository.getReferenceById(3L)).thenReturn(cliente);

		service.deletar(3L);

		assertFalse(repository.getReferenceById(3L).isAtivo());

	}
	
	@Test
	public void deveriaRetornarUmaExcecaoAoCadastrarCliente() {
		DadosCadastroCliente dadosCadastroCliente = this.gerarDadosCadastroCliente();
		
		Mockito.when(repository.existsByDocumento(any())).thenThrow(new IllegalArgumentException("CPF ja cadastrado!"));
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> service.cadastrar(dadosCadastroCliente));
		
	}
	
//	@Test
//	public void deveriaRetornarUmaExcecaoAoAtualizarClienteQueNaoTemId() {
//		DadosAtualizacaoCliente dadosAtualizacaoCliente = this.gerarAtualizacao();
//		
//		Mockito.when(repository.existsById(any())).thenThrow(new IllegalArgumentException("Id de cliente não localizado!"));
//		
//		Assertions.assertThrows(IllegalArgumentException.class, () -> service.atualizar(dadosAtualizacaoCliente));
//		
//	}

	 private DadosCadastroCliente gerarDadosCadastroCliente() {
		 DadosCadastroCliente dados = new DadosCadastroCliente("Joel", "47998", "053633", this.gerarDadosEndereco());
		 return dados;

	}
	 
	 private DadosEndereco gerarDadosEndereco() {
		 DadosEndereco dados = new DadosEndereco("Rua abc", "bairro", "89070260", "Blumenau", "SC", "200", "complemento");
		 return dados;
	 }
	 
	 private DadosAtualizacaoCliente gerarAtualizacao() {
		 DadosAtualizacaoCliente dados = new DadosAtualizacaoCliente(1L, "Rubens", "47999", "05366328941", gerarDadosEndereco());
		 return dados;
	 }

	

	 
	 private Cliente criarCliente() {
	
	 Cliente cliente = Cliente.builder()
	                .id(1L)
	                .nome("Eduardo")
	                .telefone("47999884415")
					.documento("00900800715")
					.endereco(new Endereco(gerarDadosEndereco()))
	                .build();

		 return cliente;

	 }
	 
	@Mock
	private Cliente cliente2 = Cliente.builder()
             .id(1L)
             .nome("Eduardo")
             .telefone("47999884415")
			 .documento("00900800715")
			 .endereco(new Endereco(gerarDadosEndereco()))
             .build();
	 

	 private List<Cliente> criarListaDeClientes(){

			List<Cliente> clientes = new ArrayList<>();
			Cliente cliente1 =
					Cliente.builder()
					.id(1l)
					.nome("Pedro")
					.telefone("47")
					.documento("099015")
					.ativo(true)
					.build();

			Cliente cliente2 =
					Cliente.builder()
					.id(2l)
					.nome("Maria")
					.telefone("479")
					.documento("0990150")
					.ativo(true)
					.build();


			clientes.add(cliente1);
			clientes.add(cliente2);
			return clientes;

		}



}

