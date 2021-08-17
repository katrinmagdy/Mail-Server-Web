package com.example.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
 //import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import controller.AppController;
import controller.proxyClass;
import datastructures.DLinkedList;
import datastructures.DoubleLinkedList;
import datastructures.Priority_Queue;
import datastructures.Queue;
import models.Contact;
import models.Filter;
import models.Folder;
import models.Mail;
import models.MailBuilder;
import models.Search;
import models.Sort;




@SpringBootApplication
@ComponentScan(basePackageClasses= AppController.class)
public class MailClientBackEndApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(MailClientBackEndApplication.class, args);
		
		LinkedList attachments = new LinkedList();
		attachments.add("//lgjkg");
		
		
		Mail m1 = new Mail("mail1","farida","12/12/2012","urgent","hi all",attachments,null);
		Mail m2 = new Mail("mail2","katrin","12/12/2012","urgent","hi all",attachments,null);
		Mail m3 = new Mail("mail3","eman"  ,"12/12/2012","urgent","hi all",attachments,null);
		Mail m4 = new Mail("mail4","sara"  ,"12/12/2012","unimportant","hi all",attachments,null);
		
		DLinkedList l = new DLinkedList ();
		l.add(m1);
		l.add(m4);
		l.add(m3);
		l.add(m2);
		
		Contact c1 = new Contact("farida","e");
		Contact c2 = new Contact("katrin","d");
		Contact c3 = new Contact("eman","c");
		Contact c4 = new Contact("sara","b");
		Contact c5 = new Contact("zyad","a");
		
		LinkedList contacts= new LinkedList();
		contacts.add("farida");
		contacts.add("katrin");
		contacts.add("n@");
		
	
		
		/*Sort s = new Sort("e-mail");
		contacts= s.sortContact(contacts);*/
		Search s = new Search("n@");
		//contacts=s.searchContact(contacts);
		/*for(int i=0; i<contacts.size();i++) {
			System.out.println(((Contact)contacts.get(i)).toString());
				}*/
		/*Filter f =new Filter("subject","mail3");
		DLinkedList k = f.Filtering(l);
		for(int i=0; i<k.size();i++) {
		System.out.println(((Mail)k.get(i)).toString());
			}*/
		/*Search s = new Search("sara");
		DLinkedList k =s.search(l);
		for(int i=0; i<k.size();i++) {
			System.out.println(((Mail)k.get(i)).toString());
				}*/
		/*DLinkedList trylist = new DLinkedList();
		Folder f = new Folder("sent","");
		f.foldermails("n@", trylist);
		System.out.println(trylist.size());
		for(int i=0; i<trylist.size();i++) {
			System.out.println(((Mail)trylist.get(i)).toString());
				}*/
		
		/*proxyClass p = new proxyClass();
		LinkedList contacts1 =p.showContact();
		for(int i=0; i<contacts1.size();i++) {
			System.out.println((contacts1.get(i)).toString());
				}*/
		
}
	
}
