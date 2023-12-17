package com.Ratatouille23.Server.service;

import com.Ratatouille23.Server.entity.Menu;
import com.Ratatouille23.Server.entity.Tavolo;
import com.Ratatouille23.Server.entity.TipoUtente;
import com.Ratatouille23.Server.entity.Utente;
import com.Ratatouille23.Server.handler.exception.InternalServerException;
import com.Ratatouille23.Server.handler.exception.NotFoundException;
import com.Ratatouille23.Server.repository.UtenteRepository;
import com.Ratatouille23.Server.security.response.UtenteResponse;
import com.Ratatouille23.Server.security.service.JwtTokenService;
import com.Ratatouille23.Server.security.service.JwtUserDetailsService;
import com.Ratatouille23.Server.utils.ApiSuccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.Ratatouille23.Server.utils.Utils.patternEmail;
import static com.Ratatouille23.Server.utils.Utils.patternPassword;

@Service
@Slf4j
public class UtenteService {

    @Autowired
    private UtenteRepository service;
    @Autowired
    private TavoloService repositoryTavolo;

    @Autowired
    private MenuService menuService;


    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    private static String nomeMenu = "Ratatouille23";

    public List<Utente> findAll() {
        return service.findAll();
    }

    public Utente findByEmail(String email) throws NotFoundException {
        Optional<Utente> utente = service.findByEmail(email);
        if (utente.isPresent()) {
            return utente.get();
        }
        throw new NotFoundException( "Utente non trovato");
    }


    public boolean checkIfEmailExits(String email) {
        return service.existsByEmailIgnoreCase(email);
    }

    public Utente saveUtente(Utente utente) {
        return service.save(utente);
    }


    public List<Utente> findAllAddettiAllaSala() {
        return service.findAll().stream().filter(tipoutente ->
                        tipoutente.getTipoUtente().equals(TipoUtente.AddettoAllaSala))
                .collect(Collectors.toList());
    }

    public ResponseEntity<Utente> reImpostaPassword(String email, String nuovaPassword) throws NotFoundException, InternalServerException {
        Utente utente = findByEmail(email);
        if (utente.getPrimoAccesso() != null) {
            throw new InternalServerException( "Errore: primo accesso già effettuato in precedenza");
        }
        if (!patternPassword(nuovaPassword)) {
            throw new InternalServerException( "Formato password non valido");
        }
        if (passwordEncoder.matches(nuovaPassword,utente.getPassword())) {
            throw new InternalServerException( "La password deve essere diversa da quella inserita dall'Amministratore");
        }
        utente.setPassword(passwordEncoder.encode(nuovaPassword));
        log.info("data primo Accesso  : {}", new Date());
        utente.setPrimoAccesso(new Date());
        return ResponseEntity.ok(saveUtente(utente));
    }

    public UtenteResponse login(String email, String password) throws NotFoundException {
        Utente utente=findByEmail(email);
        if(passwordEncoder.matches(password,utente.getPassword())){
            final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(email);
            return new UtenteResponse(jwtTokenService.generateToken(userDetails));
        }

        throw  new NotFoundException("Credenziali errate");

    }



    public ResponseEntity<ApiSuccess> creaUtente(Utente utente) throws InternalServerException, NotFoundException {
        if (!patternEmail(utente.getEmail())) {
            throw new InternalServerException( "Formato email non valida");
        }
        if (!patternPassword(utente.getPassword())) {
            throw new InternalServerException( "Formato password non valida");
        }
        if (checkIfEmailExits(utente.getEmail())) {
            throw new InternalServerException( "Email già in uso");
        }
        if (checkCampiUtente(utente)) {
            throw new InternalServerException( "Uno o piu campi non sono stati definiti");
        }
        utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        utente = saveUtente(utente);
        if (tipoUtenteAmministratoreOSupervisore(utente)) {
            aggiungiUtenteAlMenu(utente);
        } else if (tipoUtenteAddettoAllaSala(utente)) {
            aggiungiUtenteAlMenu(utente);
            List<Tavolo> TuttiITavoli = repositoryTavolo.findAll();
            for (Tavolo tavolo : TuttiITavoli) {
                tavolo.getUtenteGestisceTavolo().add(utente);
                repositoryTavolo.save(tavolo);
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiSuccess(201, "Utenza creata con successo"));
    }


    private void aggiungiUtenteAlMenu(Utente utente) throws  NotFoundException {
        Menu menu = menuService.findByNomeMenu(nomeMenu);
        utente.setMenuGestito(menu);
        saveUtente(utente);

    }

    private boolean tipoUtenteAmministratoreOSupervisore(Utente utente) {
        return utente.getTipoUtente().equals(TipoUtente.Amministratore)
                || utente.getTipoUtente().equals(TipoUtente.Supervisore);
    }

    private boolean tipoUtenteAddettoAllaSala(Utente utente) {
        return utente.getTipoUtente().equals(TipoUtente.AddettoAllaSala);
    }

    private boolean checkCampiUtente(Utente utente) {
        return utente == null || (utente.getEmail() == null) || (utente.getPassword() == null) || (utente.getTipoUtente() == null);
    }

    public ResponseEntity<Utente> informazioniUtente(String email) throws NotFoundException {
        Utente utente = findByEmail(email);
       return ResponseEntity.ok(utente);
    }
}
