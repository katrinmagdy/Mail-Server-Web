package controller;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import models.Contact;


class testSignUp_SignIn {

    @Test
    void test() {
        proxyClass p=new proxyClass();
        Contact c1=new Contact("eman1", "1822000", "ema");//email,password,username
        Contact c2=new Contact("farida2", "2242001", "far");//email,password,username
        Contact c3=new Contact("sara3", "362007", "sar");//email,password,username
        Contact c4=new Contact("katrin4", "522003", "kat");//email,password,username
        Contact c5=new Contact("notSignedUp","123456789", "not");
        p.signup(c1);
        p.signup(c2);
        p.signup(c3);
        p.signup(c4);
        assertFalse(p.signup(c1));
        assertTrue( p.signin(c1.getEmail(), c1.getPassword()));
        assertFalse(p.signin(c5.getEmail(), c5.getPassword()));
        assertTrue(p.signup(c5));
    }

}