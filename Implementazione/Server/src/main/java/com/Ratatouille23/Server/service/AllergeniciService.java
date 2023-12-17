package com.Ratatouille23.Server.service;

import com.Ratatouille23.Server.entity.Allergenici;
import com.Ratatouille23.Server.repository.AllergeniciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AllergeniciService {

    @Autowired
    private AllergeniciRepository repository;

    public Allergenici findOneByNomeAllergenicoIgnoreCase(String nomeAllergenico) {
        Optional<Allergenici> allergenicoOpt = repository.findOneByNomeAllergenicoIgnoreCase(nomeAllergenico);
        return allergenicoOpt.orElse(null);
    }

    public Allergenici save(Allergenici allergenici) {
        return repository.save(allergenici);
    }

    public List<Allergenici> findAll() {
        return repository.findAll();
    }
}
