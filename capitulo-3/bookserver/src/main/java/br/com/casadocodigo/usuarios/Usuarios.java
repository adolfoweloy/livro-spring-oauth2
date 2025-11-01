package br.com.casadocodigo.usuarios;

import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class Usuarios {

	@PersistenceContext
	private EntityManager em;

	public Usuario registrar(Usuario novoUsuario) {
		em.persist(novoUsuario);

		return novoUsuario;
	}

	public Usuario buscarPorID(int id) {
		return em.find(Usuario.class, id);
	}

	public Optional<Usuario> buscarPorEmail(String email) {
		TypedQuery<Usuario> query = em.createQuery(
			"select u from Usuario u where u.credenciais.email = :email",
			Usuario.class);

		query.setParameter("email", email);

		try {
			Usuario usuario = query.getSingleResult();
			return Optional.of(usuario);
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}

	public void atualizar(Usuario usuario) {
		em.persist(usuario);
	}

}
