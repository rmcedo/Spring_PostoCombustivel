package br.com.senior.posto.registroAbastecimento;

import br.com.senior.posto.enums.FormaPagamento;
import br.com.senior.posto.enums.TipoCombustivel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface RegistroAbastecimentoRepository extends JpaRepository<RegistroAbastecimento, Long> {


    Page<RegistroAbastecimento> findRegistroAbastecimentoByDataHora(LocalDateTime dataHora, Pageable paginacao);

    @Query(value = "select r.* from registro_abastecimento r where r.id_bomba = :id --#pageable\n", nativeQuery = true)
    Page<RegistroAbastecimento> findRegistroAbastecimentoByBomba(Long id, Pageable paginacao);

    Page<RegistroAbastecimento> findRegistroAbastecimentoByFormaPagamento(FormaPagamento formaPagamento, Pageable paginacao);

    Page<RegistroAbastecimento> findRegistroAbastecimentoByFuncionarioNome(String nome, Pageable paginacao);

    Page<RegistroAbastecimento> findRegistroAbastecimentoByFuncionarioCpf(String cpf, Pageable paginacao);

    Page<RegistroAbastecimento> findRegistroAbastecimentoByClienteNome(String nome, Pageable paginacao);

    Page<RegistroAbastecimento> findRegistroAbastecimentoByClienteDocumento(String documento, Pageable paginacao);


    @Query(value = "SELECT r.* FROM registro_abastecimento r where r.data_hora >= :dataHora --#pageable\n", nativeQuery = true)
    Page<RegistroAbastecimento> findRegistroAbastecimentoAposEssaData(LocalDateTime dataHora, Pageable paginacao);

    @Query(value = "SELECT r.* FROM registro_abastecimento r where r.data_hora <= :dataHora --#pageable\n", nativeQuery = true)
    Page<RegistroAbastecimento> findRegistroAbastecimentoAntesDestaData(LocalDateTime dataHora, Pageable paginacao);

    @Query(value = "SELECT r.* FROM registro_abastecimento r where r.data_hora  >= :data1 and r.data_hora <= :data2 --#pageable\n", nativeQuery = true)
    Page<RegistroAbastecimento> findRegistroAbastecimentoEntreEstasDatas(LocalDateTime data1, LocalDateTime data2, Pageable paginacao);

    Page<RegistroAbastecimento> findRegistroAbastecimentoByBombaTipoCombustivel(TipoCombustivel tipoCombustivel, Pageable paginacao);

    @Query("select r from registroAbastecimento r order by r.valor DESC limit 1")
    Page<RegistroAbastecimento> findRegistroAbastecimentoByOrderByValorDesc(Pageable paginacao);

    @Query("select r from registroAbastecimento r order by r.litrosAbastecidos DESC limit 1")
    Page<RegistroAbastecimento> findRegistroAbastecimentoByOrderByLitrosAbastecidosDesc(Pageable paginacao);

    @Query(value = "SELECT count(r.id_atendimento) FROM registro_abastecimento r --#pageable\n", nativeQuery = true)
    Integer countRegistroAbastecimentos();

    @Query(value = "SELECT sum(r.valor) FROM registro_abastecimento r --#pageable\n", nativeQuery = true)
    Double countValorTotalDosAbastecimentos();

}
