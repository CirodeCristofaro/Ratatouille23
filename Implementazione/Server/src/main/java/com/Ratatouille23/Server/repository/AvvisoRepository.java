package com.Ratatouille23.Server.repository;

import com.Ratatouille23.Server.entity.Avviso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvvisoRepository extends JpaRepository<Avviso, Long> {

}
