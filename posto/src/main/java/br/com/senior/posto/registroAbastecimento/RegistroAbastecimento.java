package br.com.senior.posto.registroAbastecimento;

import br.com.senior.posto.bomba.Bomba;
import br.com.senior.posto.cliente.Cliente;
import br.com.senior.posto.enums.FormaPagamento;
import br.com.senior.posto.enums.TipoCombustivel;
import br.com.senior.posto.funcionario.Funcionario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "registroAbastecimento")
@Table(name = "registroAbastecimento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroAbastecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAtendimento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_funcionarios_id_funcionario", referencedColumnName = "id_funcionario")
    private Funcionario funcionario;

    private LocalDateTime dataHora;
    private BigDecimal litrosAbastecidos;
    private BigDecimal valor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_bomba", referencedColumnName = "id_bomba")
    private Bomba bomba;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_cliente_id_cliente", referencedColumnName = "id_cliente")
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;
    private BigDecimal desconto;

    public RegistroAbastecimento(DadosCriarRegistroAbastecimento dto, @RequestBody @Valid Cliente cliente, @RequestBody @Valid Funcionario funcionario, @RequestBody @Valid Bomba bomba) {
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.bomba = bomba;
        this.dataHora = dto.dataHora();
        this.litrosAbastecidos = dto.litrosAbastecidos();
        this.formaPagamento = dto.formaPagamento();
        this.valor = calcularValor();
    }

    public BigDecimal calcularValor() {
        if (bomba.getTipoCombustivel() == TipoCombustivel.GASOLINA) {
            valor = this.litrosAbastecidos.multiply(BigDecimal.valueOf(5));
        }
        if (bomba.getTipoCombustivel() == TipoCombustivel.GASOLINA_ADITIVADA) {
            valor = this.litrosAbastecidos.multiply(BigDecimal.valueOf(5.15));
        }
        if (bomba.getTipoCombustivel() == TipoCombustivel.DIESEL) {
            valor = this.litrosAbastecidos.multiply(BigDecimal.valueOf(6.50));
        }
        if (bomba.getTipoCombustivel() == TipoCombustivel.ETANOL) {
            valor = this.litrosAbastecidos.multiply(BigDecimal.valueOf(4.80));
        }
        if (this.cliente != null) {
            BigDecimal desconto = this.valor.multiply(BigDecimal.valueOf(0.10));
            this.desconto = desconto;
            return this.valor = this.valor.subtract(desconto);
        } else return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RegistroAbastecimento that = (RegistroAbastecimento) o;
        return idAtendimento != null && Objects.equals(idAtendimento, that.idAtendimento);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
