package com.Ratatouille23.Server.repository;

import com.Ratatouille23.Server.entity.Allergenici;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AllergeniciRepository extends JpaRepository<Allergenici, Long> {


    Optional<Allergenici> findOneByNomeAllergenicoIgnoreCase(String nomeAllergenico);

}
