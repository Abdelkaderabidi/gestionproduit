package services;
import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;

public class API_SMS {
    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = "";
    public static final String AUTH_TOKEN = "";


    public static void sendSMS(String nom) {



        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber("+21696618158"),
                    new com.twilio.type.PhoneNumber("+13342127779"),
                    nom+ "veuiller consulter la liste des produit insuffisante ")

                            .create();

            System.out.println(message.getSid());
        } catch (Exception e) {
            System.err.println("Failed to send SMS: " + e.getMessage());
        }
    }


}
