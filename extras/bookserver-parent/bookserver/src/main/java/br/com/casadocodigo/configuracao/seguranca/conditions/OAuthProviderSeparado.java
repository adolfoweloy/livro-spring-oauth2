package br.com.casadocodigo.configuracao.seguranca.conditions;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class OAuthProviderSeparado implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String oauthCondition = context.getEnvironment().getProperty("oauth.all-in-one");
        if (oauthCondition == null) return false;
        return oauthCondition.equalsIgnoreCase("false");
    }

}
