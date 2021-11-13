package id.holigo.services.holigoapigatewayserver.services;

import javax.jms.JMSException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface OauthService {
    boolean isValid(String token) throws JsonMappingException, JsonProcessingException, JMSException;
}
