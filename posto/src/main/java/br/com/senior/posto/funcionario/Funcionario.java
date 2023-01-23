package br.com.senior.posto.funcionario;

import br.com.senior.posto.endereco.Endereco;
import br.com.senior.posto.enums.*;
import br.com.senior.posto.registroAbastecimento.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Table(name = "funcionarios")
@Entity(name = "Funcionario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcionario")
    private Long id;
    private String nome;
    private LocalDate dataNascimento;
    private String telefone;
    private Endereco endereco;
    private String cpf;
    private Boolean ativo;

    @Enumerated(EnumType.STRING)
    private Cargo cargo;

    private LocalDate dataContratacao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionario")
    @JsonIgnore
    private List<RegistroAbastecimento> registroAbastecimento;

    public Funcionario (DadosCadastroFuncionario dados) {
        this.nome = dados.nome();
        this.dataNascimento = dados.dataNascimento();
        this.telefone = dados.telefone();
        this.cargo = dados.cargo();

        if (dados.endereco() != null) {
            this.endereco = new Endereco(dados.endereco());
        }
        
        this.cpf = dados.cpf();
        this.ativo = true;
        this.dataContratacao = LocalDate.now();
    }

    public void atualizarInformacoes (DadosAtualizacaoFuncionario dados) {
        if (dados.cpf() != null) {
            this.cpf = dados.cpf();
        }
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.dataNascimento() != null) {
            this.dataNascimento = dados.dataNascimento();
        }
        if (dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
        if (dados.endereco() != null) {
            endereco.atualizarInformacoes(dados.endereco());
        }
        if (dados.cargo() != null) {
            this.cargo = dados.cargo();
        }
    }

    public void excluir() {
        this.ativo = false;
    }


}
