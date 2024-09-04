package br.com.alura.dojoplaces.domain.local.repository;

import br.com.alura.dojoplaces.domain.local.Local;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalRepository extends JpaRepository<Local, Long> {

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, Long id);

}

