package br.com.senior.posto.bomba;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface BombaRepository extends JpaRepository<Bomba, Long>{
	
	Page<Bomba> findAllByAtivoTrue(Pageable paginacao);
	
	@Query(value = "select * from bomba where id = :id", nativeQuery = true)
	Optional<Bomba> findById(Long id);

}
