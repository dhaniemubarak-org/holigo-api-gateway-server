package id.holigo.services.holigoapigatewayserver.services;

import javax.jms.JMSException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import id.holigo.services.common.model.OauthDto;

public interface OauthService {
    OauthDto getOauth(String token) throws JsonMappingException, JsonProcessingException, JMSException;
}
