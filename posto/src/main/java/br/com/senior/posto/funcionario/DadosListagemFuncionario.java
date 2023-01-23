package br.com.senior.posto.funcionario;

public record DadosListagemFuncionario (String nome, String cpf, String telefone, String cargo){

    public DadosListagemFuncionario (Funcionario funcionario) {
        this(funcionario.getNome(), funcionario.getCpf(), funcionario.getTelefone(), funcionario.getCargo().name());
    }

}
