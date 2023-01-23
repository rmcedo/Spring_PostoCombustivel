package br.com.senior.posto.registroAbastecimento;

import br.com.senior.posto.bomba.Bomba;
import br.com.senior.posto.bomba.BombaRepository;
import br.com.senior.posto.cliente.ClienteRepository;
import br.com.senior.posto.enums.FormaPagamento;
import br.com.senior.posto.enums.TipoCombustivel;
import br.com.senior.posto.funcionario.FuncionarioRepository;
import br.com.senior.posto.mensagem.Mensagem;
import br.com.senior.posto.reservatorio.Reservatorio;
import br.com.senior.posto.reservatorio.ReservatorioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class RegistroAbastecimentoService {

    @Autowired
    ReservatorioRepository reservatorioRepository;

    @Autowired
    RegistroAbastecimentoRepository registroAbastecimentoRepository;
    @Autowired
    BombaRepository bombaRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    FuncionarioRepository funcionarioRepository;

    public Mensagem criarRegistroAbastecimento(@RequestBody @Valid DadosCriarRegistroAbastecimento dto) {
        if (!clienteRepository.existsById(dto.idCliente())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!bombaRepository.existsById(dto.idBomba())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!funcionarioRepository.existsById(dto.idFuncionario())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        RegistroAbastecimento registro = new RegistroAbastecimento(dto, clienteRepository.getReferenceById(dto.idCliente()), funcionarioRepository.getReferenceById(dto.idFuncionario()), bombaRepository.getReferenceById(dto.idBomba()));
        registroAbastecimentoRepository.save(registro);

        Bomba bomba = bombaRepository.getReferenceById(dto.idBomba());
        Reservatorio reservatorio = reservatorioRepository.getReferenceById(bomba.getIdReservatorio().getId());

        reservatorio.setQtdCombustivel(reservatorio.getQtdCombustivel().subtract(registro.getLitrosAbastecidos()));
        return reservatorio.verificarQuantidadeCombustivel();
    }

    public Page<DadosVisualisacaoRegistroAbastecimento> visualizar(Pageable paginacao) {
        return registroAbastecimentoRepository.findAll(paginacao).map(DadosVisualisacaoRegistroAbastecimento::new);
    }

    public Page<DadosVisualisacaoRegistroAbastecimento> encontrarRegistroPeloNomeDoFuncionario(String nome, Pageable paginacao) {
        return registroAbastecimentoRepository.findRegistroAbastecimentoByFuncionarioNome(nome, paginacao).map(DadosVisualisacaoRegistroAbastecimento::new);
    }

    public Page<DadosVisualisacaoRegistroAbastecimento> encontrarRegistroPeloCpfDoFuncionario(String cpf, Pageable paginacao) {
        return registroAbastecimentoRepository.findRegistroAbastecimentoByFuncionarioCpf(cpf, paginacao).map(DadosVisualisacaoRegistroAbastecimento::new);
    }

    public Page<DadosVisualisacaoRegistroAbastecimento> encontrarRegistroPeloNomeDoCliente(String nome, Pageable paginacao) {
        return registroAbastecimentoRepository.findRegistroAbastecimentoByClienteNome(nome, paginacao).map(DadosVisualisacaoRegistroAbastecimento::new);
    }

    public Page<DadosVisualisacaoRegistroAbastecimento> encontrarRegistroPeloDocumentoDoCliente(String documento, Pageable paginacao) {
        return registroAbastecimentoRepository.findRegistroAbastecimentoByClienteDocumento(documento, paginacao).map(DadosVisualisacaoRegistroAbastecimento::new);
    }

    public Page<DadosVisualisacaoRegistroAbastecimento> encontrarRegistroPelaDataEHora(LocalDateTime dataHora, Pageable paginacao) {
        return registroAbastecimentoRepository.findRegistroAbastecimentoByDataHora(dataHora, paginacao).map(DadosVisualisacaoRegistroAbastecimento::new);
    }

    public Page<DadosVisualisacaoRegistroAbastecimento> encontrarRegistroPelaAposEstaDataEHora(LocalDateTime dataHora, Pageable paginacao) {
        return registroAbastecimentoRepository.findRegistroAbastecimentoAposEssaData(dataHora, paginacao).map(DadosVisualisacaoRegistroAbastecimento::new);
    }

    public Page<DadosVisualisacaoRegistroAbastecimento> encontrarRegistroPelaAntesDestaDataEHora(LocalDateTime dataHora, Pageable paginacao) {
        return registroAbastecimentoRepository.findRegistroAbastecimentoAntesDestaData(dataHora, paginacao).map(DadosVisualisacaoRegistroAbastecimento::new);
    }

    public Page<DadosVisualisacaoRegistroAbastecimento> encontrarRegistroPelaEntreEssasDatas(LocalDateTime data1, LocalDateTime data2, Pageable paginacao) {
        return registroAbastecimentoRepository.findRegistroAbastecimentoEntreEstasDatas(data1, data2, paginacao).map(DadosVisualisacaoRegistroAbastecimento::new);
    }

    public Page<DadosVisualisacaoRegistroAbastecimento> encontrarRegistroPelaBomba(Long id, Pageable paginacao) {
        return registroAbastecimentoRepository.findRegistroAbastecimentoByBomba(id, paginacao).map(DadosVisualisacaoRegistroAbastecimento::new);
    }

    public Page<DadosVisualisacaoRegistroAbastecimento> encontrarRegistroPeloTipoDeCombustivelNaBomba(TipoCombustivel tipoCombustivel, Pageable paginacao) {
        return registroAbastecimentoRepository.findRegistroAbastecimentoByBombaTipoCombustivel(tipoCombustivel, paginacao).map(DadosVisualisacaoRegistroAbastecimento::new);
    }

    public Page<DadosVisualisacaoRegistroAbastecimento> encontrarRegistroMaisCaro(Pageable paginacao) {
        return registroAbastecimentoRepository.findRegistroAbastecimentoByOrderByValorDesc(paginacao).map(DadosVisualisacaoRegistroAbastecimento::new);
    }

    public Page<DadosVisualisacaoRegistroAbastecimento> encontrarRegistroComMaisLitrosAbastecidos(Pageable paginacao) {
        return registroAbastecimentoRepository.findRegistroAbastecimentoByOrderByLitrosAbastecidosDesc(paginacao).map(DadosVisualisacaoRegistroAbastecimento::new);
    }

    public Page<DadosVisualisacaoRegistroAbastecimento> encontrarRegistroPelaFormaDePagamento(FormaPagamento formaPagamento, Pageable paginacao) {
        return registroAbastecimentoRepository.findRegistroAbastecimentoByFormaPagamento(formaPagamento, paginacao).map(DadosVisualisacaoRegistroAbastecimento::new);
    }

    public Integer contarTodosOsRegistros() {
        return registroAbastecimentoRepository.countRegistroAbastecimentos();
    }


    public Double contarValorTotalDosAbastecimentos() {
        return registroAbastecimentoRepository.countValorTotalDosAbastecimentos();
    }
}