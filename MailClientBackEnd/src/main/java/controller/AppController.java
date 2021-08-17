package controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import datastructures.DLinkedList;
import datastructures.Priority_Queue;
import datastructures.Queue;

import models.Contact;
import models.Filter;
import models.Folder;
import models.Mail;
import models.MailBuilder;
import models.Search;
import models.Sort;
 @RestController
 @CrossOrigin
public class AppController {
	 
	 private proxyClass proxy = new proxyClass();
	 String[] viewMail = new String[7] ;
	 MailBuilder mailBuilder = new MailBuilder();
	 
    @GetMapping("/signin")
	public boolean signin(@RequestParam(value = "email")String email,@RequestParam(value = "password") String password) {
  
    	return proxy.signin(email,password);	
	}
    /////////////////////////////////////////////////////////////////////////
    @GetMapping("/signup")
	public boolean signup(@RequestParam(value = "email")String email,@RequestParam(value = "password") String password,
			@RequestParam(value = "username") String username) {
    	Contact contact = new Contact (email,password,username);
		return proxy.signup(contact);
	}
	/////////////////////////////////////////////////////////////////////////
 
    
    @DeleteMapping("/deleteMail")
    public void deleteEmails(@RequestParam(value = "selectedMails")String[][] selectedMails) {
   //fuction to transfer the array mails of strings in linkedlist 
    	//app.deleteEmails(mails);
    }
    @PutMapping("/moveEmails")
    public void moveEmails(@RequestParam(value = "selectedMails")String[][] selectedMails,
    		@RequestParam(value = "currentFolder")String currentFolder,@RequestParam(value = "showingInboxDefault")String showingInboxDefault) {
    	//use the same function to transfer array to linked list 
    }
    /////////////////////////////////////////////////////////////////
    @GetMapping("/composeNewMail")
	public boolean compose(@RequestParam(value = "sender") String sender,
			@RequestParam(value = "subject") String subject, @RequestParam(value = "content") String content,
			@RequestParam(value = "priority") String priority, @RequestParam(value = "recievers") String recievers,
			@RequestParam(value = "attachments") String attachments, @RequestParam(value = "to") String to) {

		if (attachments.equalsIgnoreCase("None") ) {
			attachments = "";
		}
		mailBuilder.setSender(sender);
		mailBuilder.setSubject(subject);
		mailBuilder.setContent(content);
		mailBuilder.setPriority(priority);
		mailBuilder.setRecievers(stringToQueue(recievers));
		mailBuilder.setAttachments(stringToList(attachments));
		// return app.compose(email);
		return proxy.compose(mailBuilder.getMail(), to);
	}
    /////////////////////////////////////////////////////////
    @GetMapping("/setViewingOptions")
	public void setViewingOptions(@RequestParam(value = "currentFolder") String currentFolder,
			@RequestParam(value = "showingInboxDefault") String showingInboxDefault,
			@RequestParam(value = "filterAccordingTo") String filterAccordingTo,
			@RequestParam(value = "wordToFilter") String wordToFilter,
			@RequestParam(value = "sortAccordingTo") String sortAccordingTo,
			@RequestParam(value = "searchAccordingTo") String searchAccordingTo,
			@RequestParam(value = "doSearch") boolean doSearch) {
		Folder folder = new Folder(currentFolder, showingInboxDefault);
		Filter filter = new Filter(filterAccordingTo, wordToFilter);
		Sort sort = new Sort(sortAccordingTo);
		Search search = new Search(searchAccordingTo);
		proxy.setViewingOptions(folder, filter, sort, search,doSearch);
		//this will only load the mails to list //so we will call it when ever we clicked on inbox oe sent
	}

	@GetMapping("/getCurrentPageMails")
	public String[][] listEmails(@RequestParam int page){
		DLinkedList smallList= proxy.listEmails(page);
		//we will give it page number then it will get the mails for this page
		//System.out.println("the small list first element");
		//System.out.println(((Mail)smallList.get(0)).toString());
		/*for(int i=0; i<smallList.size();i++) {
			System.out.println(((Mail)smallList.get(i)).toString());
				}*/
		return convertTheListOfMailsTo2DArrOfMails(smallList);
	}
	
	
	@GetMapping("/setViewMail")
	public void setViewMail(@RequestParam(value = "sender") String sender,
			@RequestParam(value = "subject") String subject, @RequestParam(value = "content") String content,
			@RequestParam(value = "priority") String priority, @RequestParam(value = "recievers") String recievers,
			@RequestParam(value = "attachments") String attachments) {
		this.viewMail[0] = recievers;
		this.viewMail[1] = sender;
		this.viewMail[2] = subject;
		this.viewMail[3] = priority;
		this.viewMail[4] = content;
		this.viewMail[5] = attachments;

		// System.out.println(viewMail.toString());
	}

	@GetMapping("/getViewMail")
	public String[] setViewMail() {
		return this.viewMail;
	}
//////////////////////////////////////////////////////////////////////////////
    
    public LinkedList stringToList(String attach){
		String[] elements = attach.split(",");
		LinkedList attachments = new LinkedList();
		for (int i = 0 ; i<elements.length;i++){
			attachments.add(elements[i]);
		}
		return attachments;
	}

    public Queue stringToQueue(String receiver) {
		String[] elements = receiver.split(",");
		Queue receivers = new Queue();
		for (int i = 0 ; i<elements.length;i++){
			receivers.enqueue(elements[i]);
		}
		return receivers;
	}
	
	public  String[][]convertTheListOfMailsTo2DArrOfMails(DLinkedList mails){//we will move it to the front end 
    	int i=0;String arrOfMails[][]=new String[mails.size()][7];
    	while(i<mails.size()) {
    		Mail m=(Mail)mails.get(i);
    		arrOfMails[i][0]=convertListOfRecieversToStringSepByComma(m.getRecievers());
    		arrOfMails[i][1]=m.getSender();
    		arrOfMails[i][2]=m.getSubject();
    		arrOfMails[i][3]=m.getPriority();
    		arrOfMails[i][4]=m.getContent();
    		arrOfMails[i][5]=convertAttachmentsToCommaSepString(m.getAttachments());
    		arrOfMails[i][6]=m.getDate();
    		i++;
    	}
    	return arrOfMails;
    }
    
    public String convertAttachmentsToCommaSepString(LinkedList attach) {
    	Queue tmp=new Queue();
    	String[]arrOfAttachments=new String[attach.size()];
    	int i=0;
    	while(i<attach.size()) {
    		arrOfAttachments[i]=(String)attach.get(i);
    		i++;
    	}
    	
    	i=0;
    	String strSebByComma="";
    	if(arrOfAttachments.length>0) {
    		StringBuilder strBuilder=new StringBuilder();
    	while(i<arrOfAttachments.length) {
    		strBuilder.append(arrOfAttachments[i]+",");
    		i++;
    	}
    	strBuilder.deleteCharAt(strBuilder.length()-1);
    	strSebByComma=strBuilder.toString();
    	}
    	
    	return strSebByComma;
	}
 ///////////////////////////////////////////////////////////////////////////////////////////////   
    public String convertListOfRecieversToStringSepByComma(Queue recievers) {
    	Queue tmp = new Queue();
    	String[]arrOfRecievers=new String[recievers.size()];
    	int i=0;
    	while(recievers.isEmpty()==false) {
    		String s=(String)recievers.dequeue();
    		arrOfRecievers[i]=s;
    		i++;
    		tmp.enqueue(s);
    	}
    	while(tmp.isEmpty()==false) {
    		recievers.enqueue(tmp.dequeue());
    	}
    	
    	i=0;
    	String strSebByComma="";
    	if(arrOfRecievers.length>0) {
    		StringBuilder strBuilder=new StringBuilder();
    	while(i<arrOfRecievers.length) {
    		strBuilder.append(arrOfRecievers[i]+",");
    		i++;
    	}
    	strBuilder.deleteCharAt(strBuilder.length()-1);
    	strSebByComma=strBuilder.toString();
    	}
    	
    	return strSebByComma;
    }
    ////////////////////////////////////////////////////
   
    @GetMapping("/addContact")
	    public void addContact(@RequestParam(value = "newEmail") String  newEmail) {
	    	 proxy.addContact(newEmail);
	    }
    
    @GetMapping("/showContacts")
    public String[] setViewingOptionContacts(@RequestParam(value = "sortAccordingTo") String sortAccordingTo,
			@RequestParam(value = "searchAccordingTo") String searchAccordingTo,
			@RequestParam(value = "doSearch") boolean doSearch) {
    	Sort sort = new Sort(sortAccordingTo);
		Search search = new Search(searchAccordingTo);
		return proxy.setViewingOptionContacts(sort, search, doSearch);
    }
  /////////////////////////////////////////////////////////////////////////////////////////////////

	@DeleteMapping("/deleteEmailsFrom")
	public void deleteEmailsFrom(@RequestParam(value = "selectedMails") String selectedMails,
								 @RequestParam(value = "from") String from){
		String[][] mails = null;
		if(from.equals("Inbox")) {
			mails = stringTo2DArrayInbox(selectedMails);
			proxy.deleteEmailsFromInbox(mails);
		}
		else {
			mails = stringTo2DArray(selectedMails);
			proxy.deleteEmailsFrom(mails,from);

		}
	}
	
//////////////////////////////////////////////////////////////////////////////////////////
	//move from sent to draft
	@GetMapping("/moveEmailsFromSent")
	public void moveEmailsFromSent(@RequestParam(value = "selectedMails") String selectedMails) {
		// use the same function to transfer array to linked list
		String[][] mails = stringTo2DArray(selectedMails);
		proxy.moveEmailsFromSent(mails);
	}
   ///////////////////////////////////////////////////////
	@GetMapping("/sentFromDraft")
	public void sendFromDraft(@RequestParam(value = "sender") String sender,
								 @RequestParam(value = "subject") String subject, @RequestParam(value = "content") String content,
								 @RequestParam(value = "priority") String priority, @RequestParam(value = "recievers") String receivers,
								 @RequestParam(value = "attachments") String attachments,
							     @RequestParam(value = "date") String date) {
		if (attachments.equals("None")) {
			attachments = "";
		}
		mailBuilder.setSender(sender);
		mailBuilder.setSubject(subject);
		mailBuilder.setContent(content);
		mailBuilder.setPriority(priority);
		mailBuilder.setRecievers(stringToQueue(receivers));
		mailBuilder.setAttachments(stringToList(attachments));
		mailBuilder.setDate(date);

		proxy.sendFromDraft(mailBuilder.getMail());
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	@GetMapping("/viewAttach")
	public boolean viewAttach(@RequestParam(value = "path") String path) {
		System.out.println(path);
		System.setProperty("java.awt.headless", "false");
		File f = new File(path.replaceAll("\\\\", "\\\\\\\\"));
		Desktop desktop = Desktop.getDesktop();

		
			if(f.exists()) {
				try {
				desktop.open(f);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return true;
			}
			else {
				return false;
			}
			
		

	}
	//////////////////////////////////////////////////////////////////////////////////
	public String[][] stringTo2DArray (String selectedMails){
		String[] temp = new String[4];
		String[] elements = selectedMails.split(",");
		String[][] mails = new String[elements.length][3];
		for(int i = 0 ; i<elements.length ; i++){
			temp = elements[i].split("#");
			mails[i][0] = temp[1];
			mails[i][1] = temp[2];
			mails[i][2] = temp[3];
		}
		return mails;
	}

	public String[][] stringTo2DArrayInbox (String selectedMails){
		String[] temp = new String[4];
		String[] elements = selectedMails.split(",");
		String[][] mails = new String[elements.length][4];
		for(int i = 0 ; i<elements.length ; i++){
			temp = elements[i].split("#");
			mails[i][0] = temp[0];
			mails[i][1] = temp[1];
			mails[i][2] = temp[2];
			mails[i][3] = temp[3];
		}
		return mails;}
		///////////////////////////////////////////////////////////
	@DeleteMapping("deleteFromContacts")
	public void deleteContacts(@RequestParam(value = "deleteContact") String contactsToDelete) {
		String[] tocall=new String[1];
		tocall[0]=contactsToDelete;
		 proxy.deleteFromContacts(tocall);}
}
