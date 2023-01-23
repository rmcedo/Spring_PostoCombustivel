package br.com.senior.posto.controller;

import br.com.senior.posto.enums.FormaPagamento;
import br.com.senior.posto.enums.TipoCombustivel;
import br.com.senior.posto.mensagem.Mensagem;
import br.com.senior.posto.registroAbastecimento.DadosCriarRegistroAbastecimento;
import br.com.senior.posto.registroAbastecimento.DadosVisualisacaoRegistroAbastecimento;
import br.com.senior.posto.registroAbastecimento.RegistroAbastecimentoRepository;
import br.com.senior.posto.registroAbastecimento.RegistroAbastecimentoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("registro_abastecimento")
public class RegistroAbastecimentoController {

    @Autowired
    private RegistroAbastecimentoService service;

    @Autowired
    private RegistroAbastecimentoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity criarRegistro(@RequestBody @Valid DadosCriarRegistroAbastecimento dto) {
        Mensagem mensagem = service.criarRegistroAbastecimento(dto);

        return ResponseEntity.ok(mensagem);
    }

    @GetMapping
    Page<DadosVisualisacaoRegistroAbastecimento> visualizar(Pageable paginacao) {
        return service.visualizar(paginacao);
    }

    @GetMapping("/funcionario_nome/{nome}")
    Page<DadosVisualisacaoRegistroAbastecimento> encontrarRegistroPeloNomeDoFuncionario(@PathVariable("nome") String nome, Pageable paginacao) {
        return service.encontrarRegistroPeloNomeDoFuncionario(nome, paginacao);
    }

    @GetMapping("/funcionario_cpf/{cpf}")
    Page<DadosVisualisacaoRegistroAbastecimento> encontrarRegistroPeloCpfDoFuncionario(@PathVariable("cpf") String cpf, Pageable paginacao) {
        return service.encontrarRegistroPeloCpfDoFuncionario(cpf, paginacao);
    }

    @GetMapping("/cliente_nome/{nome}")
    Page<DadosVisualisacaoRegistroAbastecimento> encontrarRegistroPeloNomeDoCliente(@PathVariable("nome") String nome, Pageable paginacao) {
        return service.encontrarRegistroPeloNomeDoCliente(nome, paginacao);
    }

    @GetMapping("/cliente_documento/{documento}")
    Page<DadosVisualisacaoRegistroAbastecimento> encontrarRegistroPeloDocumentoDoCliente(@PathVariable("documento") String documento, Pageable paginacao) {
        return service.encontrarRegistroPeloDocumentoDoCliente(documento, paginacao);
    }

    @GetMapping("/data_hora/{dataHora}")
    Page<DadosVisualisacaoRegistroAbastecimento> encontrarRegistroPelaDataEHora(@PathVariable("dataHora") LocalDateTime dataHora, Pageable paginacao) {
        return service.encontrarRegistroPelaDataEHora(dataHora, paginacao);
    }

    @GetMapping("/apos_data_hora/{dataHora}")
    Page<DadosVisualisacaoRegistroAbastecimento> encontrarRegistroPelaAposEstaDataEHora(@PathVariable("dataHora") LocalDateTime dataHora, Pageable paginacao) {
        return service.encontrarRegistroPelaAposEstaDataEHora(dataHora, paginacao);
    }

    @GetMapping("/antes_data_hora/{dataHora}")
    Page<DadosVisualisacaoRegistroAbastecimento> encontrarRegistroPelaAntesDestaDataEHora(@PathVariable("dataHora") LocalDateTime dataHora, Pageable paginacao) {
        return service.encontrarRegistroPelaAntesDestaDataEHora(dataHora, paginacao);
    }

    @GetMapping("/entre_data_hora/{data1}/{data2}")
    Page<DadosVisualisacaoRegistroAbastecimento> encontrarRegistroPelaEntreEssasDatas(@PathVariable("data1") LocalDateTime data1, @PathVariable("data2") LocalDateTime data2, Pageable paginacao) {
        return service.encontrarRegistroPelaEntreEssasDatas(data1, data2, paginacao);
    }

    @GetMapping("/bomba/{id}")
    Page<DadosVisualisacaoRegistroAbastecimento> encontrarRegistroPelaBomba(@PathVariable("id") Long id, Pageable paginacao) {
        return service.encontrarRegistroPelaBomba(id, paginacao);
    }

    @GetMapping("/bomba_tipo_combustivel/{tipo_combustivel}")
    Page<DadosVisualisacaoRegistroAbastecimento> encontrarRegistroPeloTipoDeCombustivelNaBomba(@PathVariable("tipo_combustivel") TipoCombustivel tipoCombustivel, Pageable paginacao) {
        return service.encontrarRegistroPeloTipoDeCombustivelNaBomba(tipoCombustivel, paginacao);
    }

    @GetMapping("/abastecimento_mais_caro")
    Page<DadosVisualisacaoRegistroAbastecimento> encontrarRegistroMaisCaro(@PageableDefault(size = 1) Pageable paginacao) {
        return service.encontrarRegistroMaisCaro(paginacao);
    }

    @GetMapping("/mais_litros_abastecidos")
    Page<DadosVisualisacaoRegistroAbastecimento> encontrarRegistroComMaisLitrosAbastecidos(@PageableDefault(size = 1) Pageable paginacao) {
        return service.encontrarRegistroComMaisLitrosAbastecidos(paginacao);
    }

    @GetMapping("/forma_de_pagamento/{forma_de_pagamento}")
    Page<DadosVisualisacaoRegistroAbastecimento> encontrarRegistroPelaFormaDePagamento(@PathVariable("forma_de_pagamento") FormaPagamento formaPagamento, Pageable paginacao) {
        return service.encontrarRegistroPelaFormaDePagamento(formaPagamento, paginacao);
    }

    @GetMapping("/count_registros")
    Integer contarTodosOsRegistros() {
        return service.contarTodosOsRegistros();
    }


    @GetMapping("/count_valor_total_abastecimentos")
    Double contarValorTotalDosAbastecimentos() {
        return service.contarValorTotalDosAbastecimentos();
    }
}