package net.iegreen.infrastructure;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.testng.annotations.Test;

/**
 * @author Shengzhao Li
 */
public class MD5Test {


    @Test
    public void encode() {
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        String encode = encoder.encodePassword("heart_beat", null);
        System.out.println(encode);
    }

}