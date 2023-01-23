package br.com.senior.posto.funcionario;

import br.com.senior.posto.endereco.DadosEndereco;
import br.com.senior.posto.enums.Cargo;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record DadosCadastroFuncionario(
        @NotNull
        String nome,

        @NotNull
        LocalDate dataNascimento,

        @NotNull
        String telefone,

        DadosEndereco endereco,

        @NotNull
        @CPF
        String cpf,

        @NotNull
        Cargo cargo

) {
}
