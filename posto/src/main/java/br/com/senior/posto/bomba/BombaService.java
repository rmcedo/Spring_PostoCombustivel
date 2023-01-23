package br.com.senior.posto.bomba;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import br.com.senior.posto.reservatorio.Reservatorio;
import br.com.senior.posto.reservatorio.ReservatorioRepository;
import jakarta.validation.Valid;

@Service
public class BombaService {

	@Autowired
	private BombaRepository bombaRepository;

	@Autowired
	private ReservatorioRepository reservatorioRepository;

	public DadosListagemBomba cadastrar(DadosCadastroBomba dados) {

		Bomba bomba = new Bomba();
		
		Reservatorio reservatorio = reservatorioRepository.findById(dados.idReservatorio()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Este reservatório não existe"));

		bomba.setIdReservatorio(reservatorio);
		bomba.setTipoCombustivel(reservatorio.getTipoCombustivel());

		bombaRepository.save(bomba);

		return new DadosListagemBomba(bomba);
	}

	public Page<DadosListagemBomba> listar(@PageableDefault Pageable paginacao) {
		return bombaRepository.findAll(paginacao).map(DadosListagemBomba::new);
	}

	public Bomba atualizar(@RequestBody @Valid DadosAtualizacaoBomba dados) {
		var bomba = bombaRepository.getReferenceById(dados.id());
		bomba.setIdReservatorio(reservatorioRepository.getReferenceById(dados.idReservatorio()));

		return bomba;

	}

	public Bomba ativar(Long id) {
		Bomba bomba = bombaRepository.getReferenceById(id);
		if(!bomba.getAtivo()) {
			bomba.ativa();
		}
		return bomba;
	}
	
	public void inativar(@PathVariable Long id) {
		var bomba = bombaRepository.getReferenceById(id);
		bomba.excluir();
	}

	
}
