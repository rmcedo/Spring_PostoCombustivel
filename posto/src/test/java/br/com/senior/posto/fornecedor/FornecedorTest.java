package br.com.senior.posto.fornecedor;

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

import br.com.senior.posto.cliente.DadosAtualizacaoCliente;
import br.com.senior.posto.cliente.DadosCadastroCliente;
import br.com.senior.posto.endereco.DadosEndereco;
import br.com.senior.posto.endereco.Endereco;

@ExtendWith(MockitoExtension.class)
class FornecedorTest {
	
	@InjectMocks
	FornecedorService service;
	
	@Mock
	FornecedorRepository repository;
	
	@Captor
	ArgumentCaptor<Fornecedor> fornecedorCaptor;

	@Test
	void deveriaCadastrarUmFornecedor() {
		DadosCadastroFornecedor dadosCadastroFornecedor = this.gerarDadosCadastroFornecedor();
		Fornecedor fornecedorCadastrado = new Fornecedor(dadosCadastroFornecedor);
		
		when(service.cadastrar(dadosCadastroFornecedor)).thenReturn(fornecedorCadastrado);
		
		Fornecedor fornecedorParaComparar = service.cadastrar(dadosCadastroFornecedor);
		
		verify(repository).save(fornecedorCadastrado);
		assertEquals(fornecedorParaComparar.getIdFornecedor(), fornecedorCadastrado.getIdFornecedor());
		assertEquals(fornecedorParaComparar.getNome(), fornecedorCadastrado.getNome());
		assertEquals(fornecedorParaComparar.getContato(), fornecedorCadastrado.getContato());
		assertEquals(fornecedorParaComparar.getTelefone(), fornecedorCadastrado.getTelefone());
		assertEquals(fornecedorParaComparar.getCnpj(), fornecedorCadastrado.getCnpj());
		assertEquals(fornecedorParaComparar.getEndereco().getLogradouro(), fornecedorCadastrado.getEndereco().getLogradouro());
		assertEquals(fornecedorParaComparar.getEndereco().getBairro(), fornecedorCadastrado.getEndereco().getBairro());
		assertEquals(fornecedorParaComparar.getEndereco().getCep(), fornecedorCadastrado.getEndereco().getCep());
		assertEquals(fornecedorParaComparar.getEndereco().getCidade(), fornecedorCadastrado.getEndereco().getCidade());
		
		
	}
	@Test
	public void deveriaRetornarForneedorPeloId() {
	    Fornecedor fornecedor = criarFornecedor();
	    when(repository.getReferenceById(1L)).thenReturn(fornecedor);

	    Fornecedor result = service.getById(1L);

	    assertEquals(fornecedor.getIdFornecedor(), result.getIdFornecedor());
	    assertEquals(fornecedor.getNome(), result.getNome());
	    assertEquals(fornecedor.getContato(), result.getContato());
	    assertEquals(fornecedor.getTelefone(), result.getTelefone());
	    assertEquals(fornecedor.getCnpj(), result.getCnpj());
	}
	
	@Test
	public void deveriaRetornarUmaListaDeFornecedoresAtivos() {
	    Pageable paginacao = PageRequest.of(0, 10);
	    List<Fornecedor> fornecedores = criarListaDeFornecedores();

	    when(repository.findAllByAtivoTrue(paginacao)).thenReturn(new PageImpl<>(fornecedores));

	    Page<DadosListagemFornecedor> result = service.listar(paginacao);

	    assertEquals(2, result.getTotalElements());
	    assertEquals(fornecedores.get(0).getNome(), result.getContent().get(0).nome());
	   assertEquals(fornecedores.get(1).getNome(), result.getContent().get(1).nome());
	}
	
	@Test
	public void deveriaAtualizarUmFornecedor() {
		DadosEndereco endereco = gerarDadosEndereco();
	    DadosAtualizacaoFornecedor dados = new DadosAtualizacaoFornecedor(1L, "Rod Oil", "Sandro", "4798888", "000901489",  endereco);
	    Fornecedor fornecedor = criarFornecedor();
	    when(repository.getReferenceById(1L)).thenReturn(fornecedor);

	    Fornecedor result = service.atualizar(dados);

	    assertEquals(fornecedor.getIdFornecedor(), result.getIdFornecedor());
	    assertEquals(fornecedor.getNome(), result.getNome());
	    assertEquals(fornecedor.getContato(), result.getContato());
	    assertEquals(fornecedor.getTelefone(), result.getTelefone());
	    assertEquals(fornecedor.getCnpj(), result.getCnpj());
	    assertEquals(fornecedor.getEndereco().getLogradouro(), result.getEndereco().getLogradouro());
	    assertEquals(fornecedor.getEndereco().getBairro(), result.getEndereco().getBairro());
	    assertEquals(fornecedor.getEndereco().getCep(), result.getEndereco().getCep());
	    assertEquals(fornecedor.getEndereco().getCidade(), result.getEndereco().getCidade());
	    assertEquals(fornecedor.getEndereco().getUf(), result.getEndereco().getUf());
	}
	
	@Test
	void deveriaDeletarUmFornecedor() {
		Fornecedor fornecedor = criarFornecedor();

		Mockito.when(repository.getReferenceById(3L)).thenReturn(fornecedor);

		service.excluir(3L);

		assertFalse(repository.getReferenceById(3L).getAtivo());

	}
	
	@Test
	public void deveriaRetornarUmaExcecaoAoCadastrarFornecedorCnpjRepetido() {
		DadosCadastroFornecedor dadosCadastroFornecedor = this.gerarDadosCadastroFornecedor();
		
		Mockito.when(repository.existsByCnpj(any())).thenThrow(new IllegalArgumentException("Cnpj ja cadastrado!"));
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> service.cadastrar(dadosCadastroFornecedor));
		
	}
	
//    @Test
//	public void deveriaRetornarUmaExcecaoAoAtualizarFornecedorQueNaoTemId() {
//		DadosAtualizacaoFornecedor dadosAtualizacaoFornecedor = this.gerarAtualizacao();
//		
//		Mockito.when(repository.findById(1L)).thenThrow(new IllegalArgumentException("Id de fornecedor não localizado!"));
//		
//		Assertions.assertThrows(IllegalArgumentException.class, () -> service.detalharFornecedor(1L));
//		
//	}
    
	 private DadosAtualizacaoFornecedor gerarAtualizacao() {
		 DadosAtualizacaoFornecedor dados = new DadosAtualizacaoFornecedor(1L, "Ipiranga", "Osmar", "47999852", "0010080000150", this.gerarDadosEndereco());
		 return dados;
	 }

	
	 private Fornecedor criarFornecedor() {
		 Endereco endereco = new Endereco("Rua abc", "bairro", "cep", "uf", "cidade", "num", "complemento");
		 Fornecedor fornecedor = Fornecedor.builder()
	                .idFornecedor(1L)
	                .nome("Petrobras")
	                .contato("Josias")
	                .telefone("47999884415")
					.cnpj("00900800715")
					.endereco(endereco)
	                .build();		 
		 
		 return fornecedor;
	 }
	 
	 private DadosEndereco gerarDadosEndereco() {
		 DadosEndereco dados = new DadosEndereco("Rua abc", "bairro", "89070260", "Blumenau", "SC", "200", "complemento");
		 return dados;
	 }
	 
	 private DadosCadastroFornecedor gerarDadosCadastroFornecedor() {
		 DadosCadastroFornecedor dados = new DadosCadastroFornecedor("Ipiranga", "Lucas", "479998", "009005147", this.gerarDadosEndereco());
		 return dados;
		 
	 }
	 
	 private List<Fornecedor> criarListaDeFornecedores(){
			
			List<Fornecedor> fornecedores = new ArrayList<>();
			Fornecedor fornecedor1 = 
					Fornecedor.builder()
					.idFornecedor(1l)
					.nome("Petrobras")
					.contato("Jose")
					.telefone("47")
					.cnpj("099015")
					.ativo(true)
					.build();
			
			Fornecedor fornecedor2 = 
					Fornecedor.builder()
					.idFornecedor(2l)
					.nome("Ipiranga")
					.contato("Rui")
					.telefone("479")
					.cnpj("0990299")
					.ativo(true)
					.build();
					
				
			fornecedores.add(fornecedor1);
			fornecedores.add(fornecedor2);
			return fornecedores;
			
		}
	 
}
	 
