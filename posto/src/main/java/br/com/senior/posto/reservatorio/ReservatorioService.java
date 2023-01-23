package br.com.senior.posto.reservatorio;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.senior.posto.enums.TipoCombustivel;
import br.com.senior.posto.fornecedor.FornecedorRepository;

@Service
public class ReservatorioService {

	@Autowired
	private ReservatorioRepository repository;
	
	@Autowired
	private FornecedorRepository fornecedorRepository;

	

	public Page<DadosListagemReservatorio> listar(Pageable paginacao) {

		return repository.findAll(paginacao).map(DadosListagemReservatorio::new);

	}
	public Reservatorio getById(Long id) {
		return repository.getReferenceById(id);
	}
	public void deletar(Long id) {
		repository.getReferenceById(id).excluir();
	}
	public void reativar(Long id) {
		repository.getReferenceById(id).reativar();
	}

	public DadosListagemReservatorio cadastrarReservatorio(DadosCadastroReservatorio dados) {
		
		Reservatorio reservatorio = new Reservatorio(dados);
		
		reservatorio.setIdFornecedor(fornecedorRepository.findById(dados.idFornecedor()).get());
		if(reservatorio.getQtdCombustivel().compareTo(reservatorio.getCapacidade()) > 0) {
			throw new IllegalArgumentException("A quantidade de combustível não pode ser maior que a capacidade do reservatório");
		}
		repository.save(reservatorio);
		return new DadosListagemReservatorio(reservatorio);
		

	}

	public Reservatorio atualizar(DadosAtualizacaoReservatorio dados) {
		var reservatorio = repository.getReferenceById(dados.id());
		if(dados.qtdCombustivel() != null) {
			if(dados.qtdCombustivel().compareTo(reservatorio.getCapacidade()) > 0) {
				throw new IllegalArgumentException("A quantidade de combustível não pode ser maior que a capacidade do reservatório");
			}
		}
		reservatorio.atualizarInformacoes(dados);
		return reservatorio;
	}
	
	public List<Reservatorio> findByCapacidadeGreaterThan(BigDecimal capacidade){
		return repository.findByCapacidadeGreaterThan(capacidade);
		
	}
	
	public List<Reservatorio> findByTipoCombustivelEquals(TipoCombustivel tipoCombustivel){
		return repository.findByTipoCombustivelEquals(tipoCombustivel);
	}
	
	

}
