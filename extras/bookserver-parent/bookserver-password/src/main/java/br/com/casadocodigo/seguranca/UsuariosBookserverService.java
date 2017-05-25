package br.com.casadocodigo.seguranca;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class UsuariosBookserverService implements UserDetailsService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TypedQuery<UsuarioBookserver> query = em.createQuery(
            "select u from UsuarioBookserver u where u.email = :username", UsuarioBookserver.class);
        query.setParameter("username", username);

        UsuarioBookserver usuarioBookserver = query.getSingleResult();

        return usuarioBookserver;
    }

}
