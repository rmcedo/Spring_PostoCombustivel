package br.com.senior.posto.fornecedor;

import java.util.ArrayList;
import java.util.List;

import br.com.senior.posto.endereco.Endereco;
import br.com.senior.posto.reservatorio.Reservatorio;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Table(name = "fornecedor")
@Entity(name = "Fornecedor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idFornecedor")

public class Fornecedor {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_fornecedor")
	private Long idFornecedor;
	private String nome;
	private String contato;
	private String telefone;
	private String cnpj;
	
	@Embedded
	private Endereco endereco;
	
	private Boolean ativo;
	
	 @OneToMany(mappedBy = "idFornecedor", cascade = CascadeType.ALL)
	 private List<Reservatorio> fo = new ArrayList<>();
	
	  public Fornecedor( String nome, String contato, String telefone, String cnpj) {
			this.nome = nome;
			this.contato = contato;
			this.telefone = telefone;
			this.cnpj = cnpj;
		}
	    
	    public Fornecedor(DadosCadastroFornecedor dados) {
	        this.ativo = true;
	        this.nome = dados.nome();
	        this.contato = dados.contato();
	        this.telefone = dados.telefone();
	        this.cnpj = dados.cnpj();
	        this.endereco = new Endereco(dados.endereco());
	    }
	    
	    public void atualizarInformacoes(DadosAtualizacaoFornecedor dados) {
	        if(dados.nome() != null) {
	            this.nome = dados.nome();
	        }
	        if(dados.telefone() != null) {
	            this.telefone = dados.telefone();
	        }
	        if(dados.cnpj() != null) {
	            this.cnpj = dados.cnpj();
	        }
	        if(dados.endereco() != null) {
	            this.endereco.atualizarInformacoes(dados.endereco());
	        }
	    }

	    public void excluir() {
	        this.ativo = false;
	    }

}
