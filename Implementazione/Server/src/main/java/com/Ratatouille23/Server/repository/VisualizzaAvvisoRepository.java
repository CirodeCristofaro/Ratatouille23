package com.Ratatouille23.Server.repository;

import com.Ratatouille23.Server.entity.VisualizzaAvviso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisualizzaAvvisoRepository extends JpaRepository<VisualizzaAvviso, Long> {

}
