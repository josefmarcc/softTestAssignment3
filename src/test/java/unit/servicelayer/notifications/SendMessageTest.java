package unit.servicelayer.notifications;

import dto.SmsMessage;
import org.junit.jupiter.api.Test;
import servicelayer.notifications.SmsService;
import servicelayer.notifications.SmsServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

public class SendMessageTest {
    private SmsService smsService = new SmsServiceImpl();;


    @Test
    public void mustReturnTrueWhenSmsNotEmpty() {
        boolean sms = smsService.sendSms(new SmsMessage("Customer", "Hello"));
        assertTrue(sms);
    }

    @Test
    public void mustReturnFalseWhenRecipientIsEmpty() {
        boolean sms = smsService.sendSms(new SmsMessage("", "Hello"));
        assertFalse(sms);
    }

    @Test
    public void mustReturnFalseWhenMessageIsEmpty() {
        boolean sms = smsService.sendSms(new SmsMessage("Customer", ""));
        assertFalse(sms);
    }
}
