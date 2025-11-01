package br.com.casadocodigo.livros.api;

import br.com.casadocodigo.configuracao.seguranca.ResourceOwner;
import br.com.casadocodigo.usuarios.Usuario;
import br.com.casadocodigo.usuarios.Usuarios;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/livros")
public class LivrosApiController {

    private final Usuarios usuarios;

    LivrosApiController(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    @GetMapping
    public ResponseEntity<?> livros(@AuthenticationPrincipal ResourceOwner resourceOwner) {
        var estante = donoDosLivros(resourceOwner).getEstante();

        if (estante.temLivros()) {
            return new ResponseEntity<>(estante.todosLivros(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

    private Usuario donoDosLivros(ResourceOwner resourceOwner) {
        return usuarios.buscarPorID(resourceOwner.getId());
    }
}
