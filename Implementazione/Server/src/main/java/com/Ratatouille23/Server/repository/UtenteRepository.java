package com.Ratatouille23.Server.repository;

import com.Ratatouille23.Server.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {

    Optional<Utente> findByEmail(String email);


    boolean existsByEmailIgnoreCase(String email);
}
