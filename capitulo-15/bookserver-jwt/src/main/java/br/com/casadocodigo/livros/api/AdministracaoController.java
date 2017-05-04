package br.com.casadocodigo.livros.api;

import br.com.casadocodigo.livros.RepositorioDeLivros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/api/v2/admin")
public class AdministracaoController {

    @Autowired
    private RepositorioDeLivros repositorioDeLivros;

    @RequestMapping(method = RequestMethod.GET, value = "/total_livros")
    public ResponseEntity<Long> getTotalDeLivros() {
        Long totalDeLivros = repositorioDeLivros.getTotalDeLivros();
        return new ResponseEntity<>(totalDeLivros, HttpStatus.OK);
    }

}