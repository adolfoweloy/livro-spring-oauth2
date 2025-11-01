package br.com.casadocodigo.configuracao.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model,
                            @RequestParam(value = "continue", required = false) String continueParam) {
        
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            
            if (statusCode == 403) {
                return "redirect:/login?error=access_denied";
            } else if (statusCode == 404) {
                return "redirect:/home";
            } else if (statusCode == 999) {
                // This is likely an authentication error, redirect to login
                return "redirect:/login?error=auth_failed";
            }
        }
        
        // For any other error, redirect to login with general error
        return "redirect:/login?error=true";
    }
}
