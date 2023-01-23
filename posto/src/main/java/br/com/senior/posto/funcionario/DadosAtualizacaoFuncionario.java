package br.com.senior.posto.funcionario;

import java.time.LocalDate;

import br.com.senior.posto.endereco.DadosEndereco;
import br.com.senior.posto.enums.Cargo;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoFuncionario(@NotNull Long id, String nome, LocalDate dataNascimento, String telefone,
                                         DadosEndereco endereco, String cpf, Cargo cargo) {
}
