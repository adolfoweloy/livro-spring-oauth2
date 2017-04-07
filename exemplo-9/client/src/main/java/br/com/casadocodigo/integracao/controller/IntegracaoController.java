package br.com.casadocodigo.integracao.controller;

import br.com.casadocodigo.integracao.bookserver.BookserverClientTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/integracao")
public class IntegracaoController {

    @RequestMapping(method = RequestMethod.GET, value = "/callback")
    public ModelAndView autorizar(String code) {

        System.out.println(code);

        return new ModelAndView("/minhaconta/principal");
    }

}
