package br.com.casadocodigo.configuracao.seguranca.conditions;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class OAuthHabilitado implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String enabled = context.getEnvironment().getProperty("oauth.enabled");
        if (enabled == null) return false;
        return enabled.equalsIgnoreCase("true");
    }

}
