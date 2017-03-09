package br.com.casadocodigo.livros.api;

import br.com.casadocodigo.configuracao.seguranca.UsuarioLogado;
import br.com.casadocodigo.livros.Estante;
import br.com.casadocodigo.livros.Livro;
import br.com.casadocodigo.usuarios.Usuario;
import br.com.casadocodigo.usuarios.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/livros", "/api/v2/livros"})
public class LivrosApiController {

    @Autowired
    private Usuarios usuarios;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> livros() {

        Estante estante = usuarioLogado().getEstante();

        if (estante.temLivros()) {
            return new ResponseEntity<>(estante.todosLivros(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> adicionarLivro(@RequestBody Livro livro) {
        Usuario usuario = usuarioLogado();

        usuario.getEstante().adicionar(livro);

        usuarios.atualizar(usuario);

        return new ResponseEntity<>(livro, HttpStatus.CREATED);
    }

    private Usuario usuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsuarioLogado usuarioLogado = (UsuarioLogado) authentication.getPrincipal();

        return usuarios.buscarPorID(usuarioLogado.getId());
    }
}
