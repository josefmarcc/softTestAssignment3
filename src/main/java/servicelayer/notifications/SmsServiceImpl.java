package servicelayer.notifications;

import dto.SmsMessage;

public class SmsServiceImpl implements SmsService{
    @Override
    public boolean sendSms(SmsMessage message) {
        if( message.getRecipient().equals("") || message.getMessage().equals("")) {
        return false;
        }
        return true;
    }
}
