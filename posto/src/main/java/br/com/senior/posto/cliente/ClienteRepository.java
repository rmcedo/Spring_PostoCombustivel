package br.com.senior.posto.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	Page<Cliente> findAllByAtivoTrue(Pageable paginacao);
	
	Page<Cliente> findAll (Pageable paginacao);
	
	Cliente findByDocumento(String documento);
	
	Boolean existsByDocumento(String documento);
	
	@Query(value = "select * from cliente c " +
            "where c.nome ilike %:nome%", nativeQuery = true)
    Page<Cliente> findByNome(String nome, Pageable paginacao);
	
    @Query(value = "select *, count(id_cliente) from cliente c " +
            "inner join registro_abastecimento r on r.fk_cliente_id_cliente = c.id_cliente " +
            "group by id_cliente, id_atendimento " +
            "order by 1 desc " +
            "limit 1;", nativeQuery = true)
    Cliente findClienteComMaiorNumeroDeRegistros();
    
  


}
