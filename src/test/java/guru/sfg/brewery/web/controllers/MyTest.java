package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.util.DigestUtils;

@ExtendWith(MockitoExtension.class)
public class MyTest {

    private static final String PASSWORD = "password";

    @Test
    void getSimpleHash() {
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes()));
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes()));

        String salted = PASSWORD + "ThisIsMySalt";
        System.out.println(DigestUtils.md5DigestAsHex(salted.getBytes()));
    }

    @Test
    void noOp() {
        PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();
        System.out.println(passwordEncoder.encode(PASSWORD));
    }

    @Test
    void ldapEncode() {
        PasswordEncoder passwordEncoder = new LdapShaPasswordEncoder();
        System.out.println(passwordEncoder.encode(PASSWORD));
        System.out.println(passwordEncoder.encode(PASSWORD));

        String a = passwordEncoder.encode(PASSWORD);

        System.out.println(a);

    }

    @Test
    void sha256Enc() {

        PasswordEncoder sha256 = new StandardPasswordEncoder();

        System.out.println(sha256.encode("12345"));
        System.out.println(sha256.encode(PASSWORD));

    }

    @Test
    void BCryptEncode() {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(16);
        System.out.println(passwordEncoder.encode(PASSWORD));
        System.out.println(passwordEncoder.encode(PASSWORD));

    }
}
