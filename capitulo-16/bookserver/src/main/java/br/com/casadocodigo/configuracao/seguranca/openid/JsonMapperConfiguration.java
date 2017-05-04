package br.com.casadocodigo.configuracao.seguranca.openid;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JsonMapperConfiguration {

    @Bean
    public ObjectMapper jsonMapper() {
        Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder = new Jackson2ObjectMapperBuilder();
        jackson2ObjectMapperBuilder.serializationInclusion(JsonInclude.Include.NON_NULL);

        return jackson2ObjectMapperBuilder.build();
    }

}
