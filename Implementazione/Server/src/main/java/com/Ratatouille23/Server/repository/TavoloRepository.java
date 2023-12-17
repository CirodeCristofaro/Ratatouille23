package com.Ratatouille23.Server.repository;

import com.Ratatouille23.Server.entity.Tavolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TavoloRepository extends JpaRepository<Tavolo, Long> {

    boolean existsByNumeroTavolo(int numeroTavolo);
}
