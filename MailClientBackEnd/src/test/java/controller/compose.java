package controller;
import static org.junit.jupiter.api.Assertions.*;



import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import datastructures.Queue;
import models.MailBuilder;


class testCompose {

    @Test
    void test() throws IOException {
        proxyClass p=new proxyClass();
        p.signin("eman1", "1822000");
        MailBuilder m1=new MailBuilder();
        //create a test attachment in the desktop
        File f1=new File("accounts//testAttachment1.txt");
        f1.createNewFile();
        File f2=new File("accounts//testAttachment2.txt");
        f2.createNewFile();
        File f3=new File("accounts//testAttachment3.txt");
        f3.createNewFile();
        Queue receivers=new Queue();
        LinkedList attachments=new LinkedList();
        attachments.add(f1.getAbsolutePath());//we add all names of attachements
        attachments.add(f2.getAbsolutePath());
        receivers.enqueue("farida2");
        m1.setAttachments(attachments);
        m1.setContent("mail1Content");
        m1.setPriority("");
        m1.setRecievers(receivers);
        m1.setSender("eman1");
        m1.setSubject("mail1Subject");
        p.compose(m1.getMail(), "inbox");//compose the mail to the inbox of the recievers
        attachments.add(f2.getAbsolutePath());//we add all names of attachements
        m1.setAttachments(attachments);
        m1.setContent("contentOfMail2");
        m1.setSubject("subjectOfMail2");
        receivers.enqueue("farida2");
        receivers.enqueue("katrin4");//now we have more than one reciever with same sender
        m1.setRecievers(receivers);
        assertTrue(p.compose(m1.getMail(), "inbox"));
        attachments.add(f1.getAbsolutePath());
        attachments.add(f2.getAbsolutePath());
        attachments.add(f3.getAbsolutePath());
        System.out.println("size of attachments = "+attachments.size());
        m1.setAttachments(attachments);
        receivers.enqueue("farida2");
        receivers.enqueue("katrin4");
        receivers.enqueue("sara3");
        m1.setContent("mail1Content");
        m1.setPriority("");
        m1.setRecievers(receivers);
        m1.setSender("eman1");
        m1.setSubject("mail1Subject");
        assertTrue(p.compose(m1.getMail(), "Drafts"));//it will be sent to draft
        receivers.enqueue("emy");//it is wrong email so the compose must return false
        m1.setRecievers(receivers);
        assertFalse(p.compose(m1.getMail(),"inbox"));//it will not be composed

    }

}