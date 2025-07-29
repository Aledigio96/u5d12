package digiovannialessandro.u5d12.security;

import digiovannialessandro.u5d12.configurations.JWTConfig;
import digiovannialessandro.u5d12.ecxeptions.UnauthorizedException;
import digiovannialessandro.u5d12.entities.Dipendente;
import digiovannialessandro.u5d12.services.DipendentiService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component

public class JWTCheckerFilter extends OncePerRequestFilter {


    @Autowired
    private JWTConfig jwtTools;
    @Autowired
    private DipendentiService dipendentiService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Questo è il metodo che verrà richiamato ad ogni richiesta che dovrà verificare il token (quindi a parte /login e /register)
        // Questo filtro sarà responsabile di recuperare il token, verificare che questo sia valido, se tutto è ok mandare avanti la richiesta
        // al prossimo filtro, in caso invece di problemi, segnalare un errore
        // Una delle caratteristiche interessanti dei filtri, è quella di avere l'accesso a tutte le parti della richiesta e quindi anche agli headers
        // Il token sarà posizionato negli headers (Authorization header)

        // *********************************************** AUTENTICAZIONE ***************************************************

        // Piano di battaglia
        // 1. Verifichiamo se nella richiesta è presente l'Authorization Header e se esso è ben formato ("Bearer 34j1k2lkjxcljxkjclkj..."), se
        // non c'è oppure se non ha il formato giusto --> 401
        String authHeader = request.getHeader("Authorization"); // "Bearer k1lm2m34lkmxc0898u213lk21nm390.213489us09c.123u91283"
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Inserire il token nell'Authorization Header nel formato corretto!");

        // 2. Estraiamo il token dall'header
        String accessToken = authHeader.replace("Bearer ", "");

        // 3. Verifichiamo se il token è ok, cioè: controlliamo se è stato manipolato (tramite signature), o se è scaduto (tramite Expiration Date)
        jwtTools.verifyToken(accessToken);

        // ****************************************** AUTORIZZAZIONE *******************************************************

        // 1. Cerco l'utente nel db tramite id (l'id sta nel token)
        String userId = jwtTools.extractIdFromToken(accessToken);
        Dipendente currentUser = this.dipendentiService.findById(Integer.parseInt(userId));

        // 2. Trovato l'utente devo associarlo al Security Context, questa è la maniera per Spring Security di sapere qual è l'utente
        // che sta effettuando la richiesta, ciò ci è fondamentale perché quando arriviamo al controller dobbiamo sapere qual è il ruolo
        // di chi sta effettuando la richiesta o in alcuni casi ci interessa proprio sapere chi sia l'utente per controllare ad es se sia
        // lui il proprietario della risorsa che sta andando a leggere/modificare/cancellare
        Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
        // Il terzo parametro serve per poter utilizzare i vari @PreAuthorize sugli endpoint, perché così il SecurityContext saprà quali sono
        // i ruoli dell'utente che sta effettuando la richiesta
        SecurityContextHolder.getContext().setAuthentication(authentication); // Aggiorniamo il Security Context associandogli l'utente autenticato (con i suoi ruoli)

        // 3. Se tutto è OK, passiamo la richiesta al prossimo (che può essere o un filtro o il controller direttamente)
        filterChain.doFilter(request, response); // Tramite .doFilter(req,res) richiamo il prossimo membro della catena (o un filtro o il controller)

    }

    // Disabilitiamo questo filtro per determinati endpoints tipo /auth/login e /auth/register
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
        // Ignoriamo il filtro per tutte le richieste su http://localhost:3001/auth/.....
    }
}
