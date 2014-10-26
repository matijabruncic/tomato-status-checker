package com.example.FreeStuffChecker.broadcastreceiver;

import com.example.FreeStuffChecker.broadcast.receiver.sms.SMSReceiver;
import com.example.FreeStuffChecker.model.ReceivedSMS;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by mbruncic on 20.10.2014
 */
//@RunWith(RobolectricTestRunner.class) TODO fix tests
public class SMSReceiverTest extends SMSReceiver{

    private static SMSReceiver smsReceiver = new SMSReceiver();

    public static void main(String[] args) {
        smsReceiver.setPattern("Stanje preostalih besplatnih minuta u Brutalnoj tarifi je {minute} min i {second} sec, besplatnih SMS poruka {smsCount} i besplatnih MB {internet}. Tarifa vrijedi do 16.11.2014.");
        ReceivedSMS receivedSMS = smsReceiver.extractStuff("Stanje preostalih besplatnih minuta u Brutalnoj tarifi je 991 min i 0 sec, besplatnih SMS poruka 986 i besplatnih MB 1474. Tarifa vrijedi do 16.11.2014.");
        assertNotNull(receivedSMS);
        assertEquals(991, receivedSMS.getMinute());
        assertEquals(0, receivedSMS.getSecond());
        assertEquals(986, receivedSMS.getSmsCount());
        assertEquals(147, receivedSMS.getInternetTraffic());
    }
}
