package br.com.casadocodigo.integracao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/integracao")
public class IntegracaoController {

    @RequestMapping(method = RequestMethod.GET, value = "/callback")
    public ModelAndView autorizar() {
        return new ModelAndView("forward:/minhaconta/principal");
    }

}
