package com.Ratatouille23.Server.security.service;

import com.Ratatouille23.Server.entity.Utente;
import com.Ratatouille23.Server.repository.UtenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UtenteRepository utenteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utente> utente= utenteRepository.findByEmail(username);
        if(utente.isPresent()){
            Utente user=utente.get();
            return new User(username, user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(user.getTipoUtente().toString())));
        }
        throw new UsernameNotFoundException("Credenziali errate");
    }

}