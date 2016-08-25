package net.iegreen.infrastructure.mail;

import net.iegreen.ContextTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author Shengzhao Li
 */
public class MailTransmitterTest extends ContextTest {


    @Test(enabled = false)
    public void testSend() throws Exception {

        final String emailAddress = "shengzhao@andaily.com";
        MailTransmitter transmitter = new MailTransmitter("HeartBeat Testing", "I'm HeartBeat, just for Testing send mail... <br/> Please ignore...", emailAddress);
        final MailTransmitResult result = transmitter.transmit();
        assertNotNull(result);
        assertTrue(result.success());

    }

}