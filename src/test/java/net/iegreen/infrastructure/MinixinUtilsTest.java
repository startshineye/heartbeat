package net.iegreen.infrastructure;

import net.iegreen.ContextTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/*
  * @author Shengzhao Li
  */
public class MinixinUtilsTest extends ContextTest {


    @Test(enabled = false)
    public void sendMsg() {

        String content = "Unable to connect [qc8]\n" +
                "\n" +
                "Time: 2016-05-20 22:26:00" +
                "\n" +
                "Monitor URL: http://cd.qc8.me/trends/" +
                "\n" +
                "Remark:" +
                "\n" +
                "More monitoring instance see: http://andaily.com/hb/monitoring/0549c80c7b084edca4cbf21f71ba0cc1.hb\n" +
                "\n" +
                "\n" +
                "来自 HeartBeat: http://andaily.com/hb/";

        final boolean result = MinixinUtils.sendMsg("o6E7SwADQVjJfrwsnGM-6AJvKtSI", content);
        assertTrue(result);

    }


}