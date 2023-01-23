package br.com.senior.posto.bomba;

import java.util.List;

import br.com.senior.posto.enums.TipoCombustivel;
import br.com.senior.posto.registroAbastecimento.RegistroAbastecimento;
import br.com.senior.posto.reservatorio.Reservatorio;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@Table(name = "bomba")
@Entity(name = "Bomba")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Bomba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bomba")
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoCombustivel tipoCombustivel;

    private Boolean ativo = true;

    @ManyToOne
    @JoinColumn(name = "fk_Reservatorio_id_reservatorio", referencedColumnName = "id_reservatorio")
    private Reservatorio idReservatorio;

    @OneToMany(mappedBy = "bomba")
    private List<RegistroAbastecimento> registroAbastecimento;

    public Bomba(DadosCadastroBomba dados) {
        this.ativo = true;
    }

    public void excluir() {
        this.ativo = false;
    }

    public void ativa() {
        this.ativo = true;
    }
}
