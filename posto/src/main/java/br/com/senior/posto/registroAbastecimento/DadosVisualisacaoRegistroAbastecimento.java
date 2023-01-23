package br.com.senior.posto.registroAbastecimento;

import br.com.senior.posto.enums.FormaPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosVisualisacaoRegistroAbastecimento(Long id, String nomeCliente, String nomeFuncionario, Long idBomba,
                                                     LocalDateTime dataHora, BigDecimal litrosAbastecidos,
                                                     BigDecimal valor, BigDecimal desconto,
                                                     FormaPagamento formaPagamento) {

    public DadosVisualisacaoRegistroAbastecimento(RegistroAbastecimento registroAbastecimento) {
        this(registroAbastecimento.getIdAtendimento(), registroAbastecimento.getCliente().getNome(), registroAbastecimento.getFuncionario().getNome(), registroAbastecimento.getBomba().getId(), registroAbastecimento.getDataHora(), registroAbastecimento.getLitrosAbastecidos(), registroAbastecimento.getValor(), registroAbastecimento.getDesconto(), registroAbastecimento.getFormaPagamento());
    }

}

