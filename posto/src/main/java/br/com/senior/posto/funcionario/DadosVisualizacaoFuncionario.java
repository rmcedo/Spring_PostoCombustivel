package br.com.senior.posto.funcionario;

import br.com.senior.posto.endereco.Endereco;

import java.time.LocalDate;

public record DadosVisualizacaoFuncionario(Long id, String nome, String cpf, LocalDate dataNascimento, String telefone, String cargo, Boolean ativo) {

    public DadosVisualizacaoFuncionario (Funcionario funcionario) {
        this(funcionario.getId(), funcionario.getNome(), funcionario.getCpf(), funcionario.getDataNascimento(),
                funcionario.getTelefone(), funcionario.getCargo().name(), funcionario.getAtivo());
    }

}
