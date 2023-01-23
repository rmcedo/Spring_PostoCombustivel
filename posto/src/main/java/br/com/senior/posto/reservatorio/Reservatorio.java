package br.com.senior.posto.reservatorio;

import java.math.BigDecimal;
import br.com.senior.posto.mensagem.Mensagem;
import br.com.senior.posto.enums.TipoCombustivel;
import br.com.senior.posto.fornecedor.Fornecedor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity(name = "Reservatorio")
@Table(name = "reservatorio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Reservatorio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_reservatorio")
	private Long id;

	@Column(name = "quantidade_combustivel")
	private BigDecimal qtdCombustivel;

	@Column(name = "tipo_combustivel")
	@Enumerated(EnumType.STRING)
	private TipoCombustivel tipoCombustivel;

	private BigDecimal capacidade;
	private Boolean ativo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_fornecedor", referencedColumnName = "id_fornecedor")
	private Fornecedor idFornecedor;
	
	public Reservatorio(DadosCadastroReservatorio dados) {

		this.capacidade = dados.capacidade();
		this.qtdCombustivel = dados.qtdCombustivel();
		this.tipoCombustivel = dados.tipoCombustivel();
		this.ativo = true;

	}

	public void atualizarInformacoes(DadosAtualizacaoReservatorio dados) {

		if (dados.capacidade() != null) {
			this.capacidade = dados.capacidade();
		}
		if (dados.qtdCombustivel() != null) {
			this.qtdCombustivel = dados.qtdCombustivel();
		}
		if (dados.tipoCombustivel() != null) {
			this.tipoCombustivel = dados.tipoCombustivel();
		}
		

	}

	public void excluir() {
		this.ativo = false;
	}
	public void reativar() {
		this.ativo = true;
	}

	public Mensagem verificarQuantidadeCombustivel() {
		BigDecimal limite = this.capacidade.multiply(new BigDecimal("0.2"));
		if (this.qtdCombustivel.compareTo(limite) <= 0) {
			return new Mensagem("Atenção, o reservatório está com menos de 20% de sua capacidade de combustível");
		}
		return new Mensagem("Capacidade acima de 20%");
	}

}
