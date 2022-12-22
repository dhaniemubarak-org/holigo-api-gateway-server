package id.holigo.services.holigoapigatewayserver.services;

import javax.jms.JMSException;
import javax.jms.Message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import id.holigo.services.common.model.OauthDto;
import id.holigo.services.holigoapigatewayserver.config.JmsConfig;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OauthServiceImpl implements OauthService {

    private final JmsTemplate jmsTemplate;

    private final ObjectMapper objectMapper;

    @Override
    public OauthDto getOauth(String token) throws JsonProcessingException, JMSException {
        OauthDto oauthDto = new OauthDto();
        oauthDto.setToken(token);
        Message received = jmsTemplate.sendAndReceive(JmsConfig.OAUTH_QUEUE, session -> {
            Message oauthMessage ;
            try {
                oauthMessage = session.createTextMessage(objectMapper.writeValueAsString(oauthDto));
                oauthMessage.setStringProperty("_type", "id.holigo.services.common.model.OauthDto");
            } catch (JsonProcessingException e) {
                throw new JMSException(e.getMessage());
            }
            return oauthMessage;
        });
        assert received != null;
        return objectMapper.readValue(received.getBody(String.class), OauthDto.class);
    }

}
