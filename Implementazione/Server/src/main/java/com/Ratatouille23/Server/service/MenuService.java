package com.Ratatouille23.Server.service;

import com.Ratatouille23.Server.entity.Menu;
import com.Ratatouille23.Server.handler.exception.NotFoundException;
import com.Ratatouille23.Server.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public Menu findByNomeMenu(String nomeMenu) throws  NotFoundException {
        Optional<Menu> menu = menuRepository.findByNomeMenu(nomeMenu);
        if (menu.isPresent()) {
            return menu.get();
        }
        throw new NotFoundException("Menu non trovato");

    }

    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }

    public boolean menuExists(String nome){
        Optional<Menu> menu = menuRepository.findByNomeMenu(nome);
        return menu.isPresent();
    }

}
