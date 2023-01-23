package br.com.senior.posto.registroAbastecimento;

import br.com.senior.posto.enums.FormaPagamento;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosCriarRegistroAbastecimento(@NotNull Long idFuncionario, @NotNull LocalDateTime dataHora,

                                              @NotNull BigDecimal litrosAbastecidos, Long idCliente,
                                              @NotNull FormaPagamento formaPagamento, @NotNull Long idBomba) {
}
