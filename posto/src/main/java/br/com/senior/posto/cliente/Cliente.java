package br.com.senior.posto.cliente;

import java.util.List;

import br.com.senior.posto.endereco.Endereco;
import br.com.senior.posto.registroAbastecimento.RegistroAbastecimento;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@Table(name = "cliente")
@Entity(name = "Cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cliente")
	private Long id;
	private String nome;
	private String telefone;
	private String documento;
	
	@Embedded
	private Endereco endereco;
	
	private boolean ativo;

	@OneToMany(mappedBy = "cliente")
	private List<RegistroAbastecimento> registroAbastecimento;

	public Cliente(DadosCadastroCliente dados) {
	    this.ativo = true;
        this.nome = dados.nome();
        this.telefone = dados.telefone();
        this.documento = dados.documento();
        this.endereco = new Endereco(dados.endereco());
	}

	public void atualizarInformacoes(DadosAtualizacaoCliente dados) {
		if (dados.nome() != null) {
			this.nome = dados.nome();
		}
		if (dados.telefone() != null) {
			this.telefone = dados.telefone();
		}
		if (dados.documento() != null) {
			this.documento = dados.documento();
		}
		if (dados.endereco() != null) {
			this.endereco.atualizarInformacoes(dados.endereco());
		}
	}

	public void excluir() {
		this.ativo = false;
	}

}
