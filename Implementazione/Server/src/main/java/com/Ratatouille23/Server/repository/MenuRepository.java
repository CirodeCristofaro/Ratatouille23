package com.Ratatouille23.Server.repository;

import com.Ratatouille23.Server.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    Optional<Menu> findByNomeMenu(String nomeMenu);

}
