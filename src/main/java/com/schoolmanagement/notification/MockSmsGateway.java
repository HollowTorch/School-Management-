package com.schoolmanagement.notification;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty; import org.springframework.stereotype.Component; import java.util.UUID;
@Component @ConditionalOnProperty(name="app.sms.provider",havingValue="mock",matchIfMissing=true) public class MockSmsGateway implements SmsGateway {public String send(String phone,String message){System.out.println("[MOCK SMS] to="+phone+" message="+message);return "MOCK-"+UUID.randomUUID();}}
