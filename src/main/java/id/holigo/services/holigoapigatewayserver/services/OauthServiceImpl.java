package id.holigo.services.holigoapigatewayserver.services;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import id.holigo.services.common.model.OauthDto;
import id.holigo.services.holigoapigatewayserver.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OauthServiceImpl implements OauthService {

    private final JmsTemplate jmsTemplate;

    private final ObjectMapper objectMapper;

    @Override
    public boolean isValid(String token) throws JsonMappingException, JsonProcessingException, JMSException {
        OauthDto oauthDto = new OauthDto();
        oauthDto.setToken(token);
        log.info("Init");
        log.info("Token in service -> {}", token);
        Message received = jmsTemplate.sendAndReceive(JmsConfig.OAUTH_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                log.info("try to create message isValid ....");
                Message oauthMessage = null;
                try {
                    log.info("try to create Text message ....");
                    oauthMessage = session.createTextMessage(objectMapper.writeValueAsString(oauthDto));
                    oauthMessage.setStringProperty("_type", "id.holigo.services.common.model.OauthDto");
                } catch (JsonProcessingException e) {
                    log.info("Error !");
                    throw new JMSException(e.getMessage());
                }
                return oauthMessage;
            }
        });
        log.info("Message receive -> {}", received.getBody(String.class));
        OauthDto result = objectMapper.readValue(received.getBody(String.class), OauthDto.class);
        log.info("Result -> {}", result);
        return result.isValid();
    }

}
