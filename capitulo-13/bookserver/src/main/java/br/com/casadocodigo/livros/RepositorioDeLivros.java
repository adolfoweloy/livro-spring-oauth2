package br.com.casadocodigo.livros;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class RepositorioDeLivros {

    @PersistenceContext
    private EntityManager em;

    public Long getTotalDeLivros() {
        TypedQuery<Long> query = em.createQuery(
            "select count(l) from Livro l", Long.class);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }
}
