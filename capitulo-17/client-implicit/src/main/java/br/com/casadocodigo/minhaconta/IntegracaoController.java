package br.com.casadocodigo.minhaconta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.integracao.bookserver.ImplicitTokenService;

@Controller
@RequestMapping("/integracao")
public class IntegracaoController {

    @Autowired
    private ImplicitTokenService implicitTokenService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView integracao() {
        String endpointDeAutorizacao = implicitTokenService.getAuthorizationEndpoint();
        return new ModelAndView("redirect:" + endpointDeAutorizacao);
    }

    @RequestMapping(value = "implicit", method = RequestMethod.GET)
    public ModelAndView implicit() {
        return new ModelAndView("minhaconta/bookserver");
    }

}
