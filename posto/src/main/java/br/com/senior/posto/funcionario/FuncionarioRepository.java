package br.com.senior.posto.funcionario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {


    Page<Funcionario> findAllByAtivoTrue(Pageable paginacao);

    Page<Funcionario> findByDataContratacao(LocalDate dataContratacao, Pageable paginacao);

    @Query(value = "select * from funcionarios f " +
                   "where f.nome ilike %:nome%", nativeQuery = true)
    Page<Funcionario> findByNome(String nome, Pageable paginacao);

    @Query(value = "select * from funcionarios f " +
                   "where f.data_contratacao >= :data " +
                   "order by data_contratacao desc", nativeQuery = true)
    Page<Funcionario> findByDataContratacaoEmDiante(LocalDate data, Pageable paginacao);

    Funcionario findByCpf(String cpf);

    @Query(value = "select * from funcionarios f " +
                   "where f.cargo = :cargo", nativeQuery = true)
    Page<Funcionario> findByCargo(String cargo, Pageable paginacao);

    Boolean existsByCpf(String cpf);
    
    @Query(value = "select *, count(id_funcionario) from funcionarios f " +
            "inner join registro_abastecimento r on r.fk_funcionarios_id_funcionario = f.id_funcionario " +
            "group by id_funcionario, id_atendimento " +
            "order by 1 desc " +
            "limit 1;", nativeQuery = true)
    Funcionario findFuncionarioComMaiorNumeroDeRegistros();

}
