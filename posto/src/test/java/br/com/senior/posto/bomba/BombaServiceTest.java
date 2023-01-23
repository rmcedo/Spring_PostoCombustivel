package br.com.senior.posto.bomba;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import br.com.senior.posto.endereco.Endereco;
import br.com.senior.posto.enums.TipoCombustivel;
import br.com.senior.posto.fornecedor.Fornecedor;
import br.com.senior.posto.reservatorio.Reservatorio;
import br.com.senior.posto.reservatorio.ReservatorioRepository;

@ExtendWith(MockitoExtension.class)
class BombaServiceTest {

	@InjectMocks
	private BombaService bombaService;
	
	@Mock
	private BombaRepository bombaRepository;
	
	@Mock
	private ReservatorioRepository reservatoriRepository;
	
	Reservatorio reservatorio = criarReservatorio();
	
	
	@Test
	public void deveriaAtualizarUmaBomba() {
		DadosAtualizacaoBomba bombaAlt = bombaAtualizada();
		
		Bomba bomba = Bomba.builder()
				.id(1L)
				.idReservatorio(reservatorio)
				.tipoCombustivel(reservatorio.getTipoCombustivel())
				.ativo(true)
				.build();
		
		Mockito.when(bombaRepository.getReferenceById(1L)).thenReturn(bomba);
		
		Bomba bombaAtualizada = bombaService.atualizar(bombaAlt);
		
		Assertions.assertEquals(bombaAtualizada.getIdReservatorio(), bomba.getIdReservatorio());
				
	}
	
	@Test
	public void deveriaInativarUmaBomba() {
			
		Bomba bomba = Bomba.builder()
				.id(1L)
				.idReservatorio(reservatorio)
				.tipoCombustivel(reservatorio.getTipoCombustivel())
				.ativo(true)
				.build();
		
		Mockito.when(bombaRepository.getReferenceById(1L)).thenReturn(bomba);
		
		bombaService.inativar(bomba.getId());
		
		Assertions.assertFalse(bomba.getAtivo());
				
	}
	
	@Test
	public void deveriaTrazerUmaBomba() {
			
		Bomba bomba = Bomba.builder()
				.id(1L)
				.idReservatorio(reservatorio)
				.tipoCombustivel(reservatorio.getTipoCombustivel())
				.ativo(true)
				.build();
		
		Mockito.when(bombaRepository.getReferenceById(1L)).thenReturn(bomba);
		
		bombaService.inativar(bomba.getId());
		
		Assertions.assertFalse(bomba.getAtivo());
				
	}
	
	@Test
	public void deveriaAtivarUmaBomba() {
			
		Bomba bomba = Bomba.builder()
				.id(1L)
				.idReservatorio(reservatorio)
				.tipoCombustivel(reservatorio.getTipoCombustivel())
				.ativo(false)
				.build();
		
		Mockito.when(bombaRepository.getReferenceById(1L)).thenReturn(bomba);
		
		bombaService.ativar(bomba.getId());
		
		Assertions.assertTrue(bomba.getAtivo());
				
	}
	
	@Test
	public void deveriaListarAsBombas() {
			
		Pageable paginacao = PageRequest.of(0,10);
		
		List<Bomba> bombas = criarListaBombas();
		
		Mockito.when(bombaRepository.findAll(paginacao)).thenReturn(new PageImpl<>(bombas));
		
		Page<DadosListagemBomba> result = bombaService.listar(paginacao);
		
		Assertions.assertEquals(4, result.getTotalElements());
							
	}

	@Test
	public void deveriaCadastrarBomba() {
		DadosCadastroBomba dadosCadastroBomba = this.bombaCadastro();
		
		Mockito.when(reservatoriRepository.findById(any())).thenReturn(Optional.of(reservatorio));
		
		bombaService.cadastrar(dadosCadastroBomba);
		
		verify(bombaRepository).save(Bomba.builder()
				.id(null)
				.idReservatorio(reservatorio)
				.tipoCombustivel(reservatorio.getTipoCombustivel())
				.ativo(true)
				.build());
	}
	
	@Test
	public void deveriaRetornarUmaExcecaoAoCadastrarBomba() {
		DadosCadastroBomba dadosCadastroBomba = this.bombaCadastro();
		
		Mockito.when(reservatoriRepository.findById(any())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		Assertions.assertThrows(ResponseStatusException.class, () -> bombaService.cadastrar(dadosCadastroBomba));
		
	}

	private DadosCadastroBomba bombaCadastro() {
		
		DadosCadastroBomba bombaCadastro = new DadosCadastroBomba(1L);
		
		return bombaCadastro;
	}
	
	
	private DadosAtualizacaoBomba bombaAtualizada() {
		return new DadosAtualizacaoBomba(1L, 2L);
	}
	
	private Reservatorio criarReservatorio(){
		Endereco endereco = new Endereco("Rua abc", "bairro", "cep", "uf", "cidade", "num", "complemento");
		Fornecedor fornecedor = Fornecedor.builder()
	            .idFornecedor(1L)
	            .nome("Petrobras")
	            .contato("Josias")
	            .telefone("47999884415")
				.cnpj("00900800715")
				.endereco(endereco)
	            .build();		 
	 
		
		Reservatorio reservatorio = 
				Reservatorio.builder()
				.id(1l)
				.ativo(true)
				.capacidade(new BigDecimal("1000"))
				.qtdCombustivel(new BigDecimal("500"))
				.idFornecedor(fornecedor)
				.tipoCombustivel(TipoCombustivel.GASOLINA)
				.build();
		
		
		return reservatorio;
	}
	
				
	private List<Bomba> criarListaBombas() {
	
		List<Bomba> bombas = new ArrayList<>();
		
		Reservatorio reservatorio = criarReservatorio();
	
		Bomba bomba1 = Bomba.builder()
			.id(1L)
			.idReservatorio(reservatorio)
			.tipoCombustivel(reservatorio.getTipoCombustivel())
			.ativo(true)
			.build();
	
		Bomba bomba2 = Bomba.builder()
			.id(2L)
			.idReservatorio(reservatorio)
			.tipoCombustivel(reservatorio.getTipoCombustivel())
			.ativo(true)
			.build();
		
		Bomba bomba3 = Bomba.builder()
			.id(3L)
			.idReservatorio(reservatorio)
			.tipoCombustivel(reservatorio.getTipoCombustivel())
			.ativo(true)
			.build();

		Bomba bomba4 = Bomba.builder()
			.id(14L)
			.idReservatorio(reservatorio)
			.tipoCombustivel(reservatorio.getTipoCombustivel())
			.ativo(true)
			.build();
	
		bombas.add(bomba1);
		bombas.add(bomba2);
		bombas.add(bomba3);
		bombas.add(bomba4);
		
		return bombas;
		
	}
}
