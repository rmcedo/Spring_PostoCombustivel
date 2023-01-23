package br.com.senior.posto.fornecedor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.senior.posto.cliente.Cliente;
import br.com.senior.posto.funcionario.Funcionario;


public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

	Page<Fornecedor> findAllByAtivoTrue(Pageable paginacao);
	
	Page<Fornecedor> findAll (Pageable paginacao);

	
	@Query(value = "select * from fornecedor f " +
            "where f.nome ilike %:nome%", nativeQuery = true)
Page<Fornecedor> findByNome(String nome, Pageable paginacao);
	
	Fornecedor findByCnpj(String cnpj);
	
	Boolean existsByCnpj(String cnpj);

}
