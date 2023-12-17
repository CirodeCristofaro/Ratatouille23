package com.Ratatouille23.Server.repository;

import com.Ratatouille23.Server.entity.Elementi;
import com.Ratatouille23.Server.entity.Tavolo;
import com.Ratatouille23.Server.entity.TavoloOrdinaElemento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TavoloOrdinaElementoRepository extends JpaRepository<TavoloOrdinaElemento, Long> {
    TavoloOrdinaElemento findByTavoloAndElementiOrdinati(Tavolo tavolo, Elementi elementi1);

}
