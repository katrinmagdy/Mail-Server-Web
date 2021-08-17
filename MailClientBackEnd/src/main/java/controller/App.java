package controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import datastructures.DLinkedList;
import models.Contact;
import models.Filter;
import models.Folder;
import models.Mail;
import models.MailBuilder;
import models.Search;
import models.Sort;


public class App {
	
private static  App appInstance;
private String email;
private String password;
private String UserName;
private static DLinkedList list=new DLinkedList();
private static LinkedList contacts=new LinkedList();
String fol="";
int maxPages=0;	
    //the singleton consept
    //private constructor
	private  App() {
		
	}
	
  //to get a the only one instance
	public static App getInstance() {
		if(appInstance==null) {
			synchronized (App.class) {
				if(appInstance==null) {
					appInstance=new App();
				}
			}
		}
		return appInstance;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	public DLinkedList getMailsList() {
		return this.list;
	}
	//set user name
		public void setUserName(String UserName) {
			this.UserName = UserName;
		}
		////////////////////////////////////////////////////////
	public boolean signin(String email, String password) {
		this.email = email;
		this.password = password;
		return true;
		
	}
	////////////////////////////////////////////////////////////////////////////
	public boolean signup(Contact contact){
		//in order to write in the file we use mapper.writeValue

			//creating email folder in accounts folder
			 String ss = "accounts\\"+contact.getEmail();
			 File file = new File(ss);
			 file.mkdir();

			 //creating inbox folder in email folder
			 ss = "accounts\\"+contact.getEmail()+"\\Inbox";
			 File inbox = new File(ss);
			 inbox.mkdirs();

			 //creating index.json in inbox folder
		     ss = "accounts\\"+contact.getEmail()+"\\Inbox\\index.json";
			 File indexInbox = new File(ss);
			 try {
				 indexInbox.createNewFile();
			 } catch (IOException e) {
				e.printStackTrace();
			 }

			 //creating sent folder in email folder
			 ss = "accounts\\"+ contact.getEmail()+"\\sent";
			 File sent = new File(ss);
			 sent.mkdirs();

			 //creating index.json in sent folder
			 ss = "accounts\\"+ contact.getEmail()+"\\sent\\index.json";
		 	 File indexSent = new File(ss);
		 	 try {
				 indexSent.createNewFile();
		 	 } catch (IOException e) {
				e.printStackTrace();
			 }

			 //creating drafts folder in email folder
		     ss = "accounts\\"+contact.getEmail()+"\\Drafts";
			 File draft = new File(ss);
			 draft.mkdirs();

			 //creating index.json in drafts folder
			 ss = "accounts\\"+contact.getEmail()+"\\Drafts\\index.json";
			 File indexDrafts = new File(ss);
			 try {
		 	  	 indexDrafts.createNewFile();
		 	 } catch (IOException e) {
				 e.printStackTrace();
			 }

		     //creating trash folder in email folder
		     ss ="accounts\\"+ contact.getEmail()+"\\Trash";
			 File trash = new File(ss);
			 trash.mkdirs();

			 //creating index.json in trash folder
			 ss ="accounts\\"+ contact.getEmail()+"\\Trash\\index.json";
			 File indexTrash = new File(ss);
			 try {
				 indexTrash.createNewFile();
			 } catch (IOException e) {
				 e.printStackTrace();
			 }

			 //creating contacts.json in email folder
			 ss = "accounts\\"+contact.getEmail()+"\\contacts.json";
			 File cont = new File(ss);
			 try {
				cont.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		return true;
	}
	////////////////////////////////////////////////////////////////////////////////////
	public void setViewingOptions(Folder folder, Filter filter, Sort sort,Search search,boolean doSearch) {
		this.list.clear();
		
		Folder f = (Folder) folder;
		Filter fil= (Filter) filter;
	    Sort s = (Sort) sort;
	    Search srch = search;
		 
		if(folder.getCurrentFolder().equals("Trash")) {
			folder.trashmails(email,list);
			fol=folder.getCurrentFolder();
		}
		else {
			folder.foldermails(email,list);
		    fol=folder.getCurrentFolder();
		    
		}
		
		s.SortTmpListForSearching(list);
		list=fil.Filtering(list);
		if(doSearch) {
			list=srch.search(list);
		}
		/*for(int k=0;k<10;k++) {
    		System.out.println("the global list array first element from App");
    		System.out.println(((Mail)list.get(k)).toString());
    	}*/
		 
        if(list.size()%10==0) {//for example if we have 11 mails so we need (11+10)/10= 2 pages to show them 
        	this.maxPages=list.size()/10;
        }
        else {
        	this.maxPages=(list.size()+10)/10;
        }
	}
	////////////////////////////////////////////////////////////////
	
    
    public void deleteEmails(LinkedList mails) {}
   
    public void moveEmails(LinkedList mails, Folder des) {}
    /////////////////////////////////////////////////////////////////////////
  
    public boolean compose(Mail mail,String To) {

 	   //check subject
 	   if (mail.getSubject().trim().isEmpty()) {
 		   mail.setSubject("");
 	   }

 	   //set date
 	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
 	   String date = format.format( new Date());
 	   mail.setDate(date);

 	   //sending message
 	   if (To.equalsIgnoreCase("inbox")){
 	   	mail.sendMailToSentbox();
 	   	mail.sendMailToInbox();
 	   }
 	   else{
 	   	mail.sendMailToDraft();
 	   }


 	   return true;
    }
    ///////////////////////////////////////////////////////////////////////
    public DLinkedList listEmails(int page) {
		Mail[] m = new Mail[10];//the list which will be sent to the front end
    	int j=0;
    	for(int i=0;i<10;i++) {
    		m[i]=null;
    	}
    	for(int i=(0+(page-1)*10);i<(page*10);i++) {
    		//if we are in page 2 for example we will view emails from 11 to 20 (11+10-1) 10 mails per page
    		if(i!=list.size()) {
    			MailBuilder b = new MailBuilder();
    			m[j]=b.getMail();
    			m[j]=(Mail)list.get(i);
    			 
    			 /*MailBuilder b=new  MailBuilder();
    			  b.setContent(((Mail)list.get(i)).getContent());
    			  b.setRecievers(((Mail)list.get(i)).getRecievers());
    			  b.setDate(((Mail)list.get(i)).getDate());
    			  b.setAttachments(((Mail)list.get(i)).getAttachments());
    			  b.setPriority(((Mail)list.get(i)).getPriority());
    			  b.setSender(((Mail)list.get(i)).getSender());
    			  Mail mail=b.getMail();*/
    			  
    			  j++;
    		}
    		else {
    			break;
    		}
    	}
    	/*for(int k=0;k<10;k++) {
    		System.out.println("the small list array first element from App");
    		System.out.println(((Mail)m[k]).toString());
    	}*/
    	//convert it to new small list then 2D array ro be given to front end
    	int i=0;
    	DLinkedList smalllistOfMails=new DLinkedList();
    	while(m[i]!=null) {
    		smalllistOfMails.add(m[i]);
    		i++;
    		if(i>=10)
    		{
    			break;
    			}
    		}
    	
    	
    	return smalllistOfMails;//list of mails to be shown
	}
   ///////////////////////////////////////////////////////////////////////////////////////////////////// 
    
    public LinkedList showContact() {
		LinkedList result = new LinkedList();
		
		String open = "accounts//"+email+"//contacts.json";
		JSONParser jsonP=new JSONParser();
		
	
			//intialization the types of data to use 
			File file= new File(open);
			if(file.length()>0) {
			Object obj;
			try {
				
				obj = jsonP.parse(new FileReader(open));
				
				JSONObject obj2=(JSONObject)obj;
				JSONArray contacts=(JSONArray)obj2.get("Contacts");
				
				
				if(contacts!=null) {
				for (Object contact : contacts) {
		             result.add((String)contact);}     
				}
				
		}
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			return result;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////
	public void addContact(String newEmail) {
		String path = "accounts//" + email + "//contacts.json";
		File contactsFile = new File(path);
		JSONObject object = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		if (contactsFile.length() == 0) {
			jsonArray.add(newEmail);
			object.put("Contacts", jsonArray);
		} else {
			JSONParser jsonParser = new JSONParser();
			FileReader reader = null;
			try {
				reader = new FileReader(path);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			JSONObject json = null;
			try {
				json = (JSONObject) jsonParser.parse(reader);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			jsonArray.clear();
			object.clear();
			jsonArray = (JSONArray) json.get("Contacts");
			boolean found = false;
			if(jsonArray!=null) {
			for (int i = 0; i < jsonArray.size(); i++) {
				if (jsonArray.get(i).equals(newEmail)) {
					found = true;
					break;
				}
			}}
			if (found == false) {
				jsonArray.add(newEmail);
				
			}
			object.put("Contacts", jsonArray);
		}

		try {
			FileWriter fileWriter = new FileWriter(path);
			fileWriter.write(object.toString());
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	////////////////////////////////////////////////////////////////////////////////////////////////
	public String[] setViewingOptionContacts(Sort sort ,Search search,boolean doSearch) {
		this.contacts.clear();
		String[] result;
		this.contacts=this.showContact();
		result=sort.sortContact(contacts);
		if(doSearch) {
			result=search.searchContact(contacts);
		}
		return result;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	public void moveEmailsFromSent(String[][] selectedMails){
		String sender;
		String date;
		String subject;
		for (int i = 0; i < selectedMails.length; i++) {
			sender = selectedMails[i][0];
			subject = selectedMails[i][1];
			date = selectedMails[i][2];

			String drafts = "accounts//" + sender + "//Drafts";
			File file = new File("accounts//" + sender + "//sent//" + sender + subject + date);
			String sourceIndex = "accounts//" + sender + "//sent//index.json";
			String destinationIndex = "accounts//" + sender + "//Drafts//index.json";

			moveFolders(file, drafts, sender, subject, date);
			moveMailIndex(sourceIndex, destinationIndex, sender, subject, date);
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	public void deleteEmailsFrom(String[][] selectedMails, String from) {
		String sender;
		String date;
		String subject;
		for (int i = 0; i < selectedMails.length; i++) {
			sender = selectedMails[i][0];
			subject = selectedMails[i][1];
			date = selectedMails[i][2];
			String trash = "accounts//" + email + "//Trash";
			File file = new File("accounts//" + email + "//" + from + "//" + sender + subject + date);
			String sourceIndex = "accounts//" + email + "//" + from + "//index.json";
			String destinationIndex = "accounts//" + email + "//Trash//index.json";

			moveFolders(file, trash, sender, subject, date);
			moveMailIndex(sourceIndex, destinationIndex, sender, subject, date);


		}
	}

	public void deleteEmailsFromInbox(String[][] selectedMails) {
		String reciever;
		String sender;
		String date;
		String subject;
		for (int i = 0; i < selectedMails.length; i++) {
			reciever = (String) selectedMails[i][0];
			sender = (String) selectedMails[i][1];
			subject = (String) selectedMails[i][2];
			date = (String) selectedMails[i][3];
			String trash = "accounts//" + reciever + "//Trash";
			File file = new File("accounts//" + reciever + "//Inbox//" + sender + subject + date);
			String sourceIndex = "accounts//" + reciever + "//Inbox//index.json";
			String destinationIndex = "accounts//" + reciever + "//Trash//index.json";

			moveFolders(file, trash, sender, subject, date);
			moveMailIndex(sourceIndex, destinationIndex, sender, subject, date);
		}
	}

	public void sendFromDraft(Mail mail){
		String path = "accounts\\" + mail.getSender()+ "\\Drafts\\" + mail.getSender() + mail.getSubject() + mail.getDate();
		File file = new File(path);
		deleteFolder(file);
		path = "accounts\\" + mail.getSender()+ "\\Drafts\\index.json";
		editAndDeleteIndex(path,mail);
		compose(mail,"inbox");
	}

	static void deleteFolder(File file) {
		for (File subFile : file.listFiles()) {
			if (subFile.isDirectory()) {
				deleteFolder(subFile);
			} else {
				subFile.delete();
			}
		}
		file.delete();
	}

	public static void copyFolder(File source, File destination) {
		if (source.isDirectory()) {
			if (!destination.exists()) {
				destination.mkdirs();
			}

			String files[] = source.list();

			for (String file : files) {
				File srcFile = new File(source, file);
				File destFile = new File(destination, file);

				copyFolder(srcFile, destFile);
			}
		} else {
			InputStream in = null;
			OutputStream out = null;

			try {
				in = new FileInputStream(source);
				out = new FileOutputStream(destination);

				byte[] buffer = new byte[1024];

				int length;
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
				in.close();
				out.close();
			} catch (Exception e) {
				try {
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				try {
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public void moveFolders(File source, String destinationPath,String sender, String subject, String date) {
		File destination = new File(destinationPath+"\\"+sender+subject+date);
		copyFolder(source, destination);
		deleteFolder(source);
	}

	public void moveMailIndex(String sourceIndex, String destinationIndex, String sender, String subject, String date) {
		String priority = deleteFromIndex(sourceIndex, sender, subject, date);
		writeInIndex(destinationIndex, sender, subject, date, priority);
	}

	public String deleteFromIndex(String sourceIndex, String sender, String subject, String date) {
		String priority = null;
		JSONObject object = new JSONObject();
		JSONParser jsonParser = new JSONParser();
		FileReader reader = null;
		try {
			reader = new FileReader(sourceIndex);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		JSONObject json = null;
		try {
			json = (JSONObject) jsonParser.parse(reader);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONArray jsonMails = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = (JSONArray) json.get("Mails");
		for (Object jsonMail : jsonArray) {
			jsonObject = (JSONObject) jsonMail;
			if (jsonObject.get("Subject").equals(subject) && jsonObject.get("Sender").equals(sender) && jsonObject.get("Date").equals(date)) {
				priority = (String) jsonObject.get("Priority");
			}
			else {
				jsonMails.add(jsonObject);
			}
		}
		// saving new array

			object.put("Mails", jsonMails);

			try {
				FileWriter fileWriter = new FileWriter(sourceIndex);
				if(!(jsonMails.isEmpty())) {
					fileWriter.write(object.toString());
				}
				else {
					fileWriter.write("");
				}
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return priority;
	}


	public void writeInIndex(String destinationIndex,String sender,String subject,String date,String priority){
		JSONObject jsonObject = new JSONObject();
		JSONObject object = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		File index = new File(destinationIndex);
		if (index.length() == 0) {
			object.put("Mails", jsonArray);
			jsonObject.put("Subject", subject);
			jsonObject.put("Sender", sender);
			jsonObject.put("Date", date);
			jsonObject.put("Priority", priority);
			jsonArray.add(jsonObject);
		} else {
			JSONParser jsonParser = new JSONParser();
			FileReader reader = null;
			try {
				reader = new FileReader(destinationIndex);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			JSONObject json = null;
			try {
				json = (JSONObject) jsonParser.parse(reader);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			JSONArray jsonMails = new JSONArray();
			JSONObject jsonObject1 = new JSONObject();
			jsonArray = (JSONArray) json.get("Mails");
			for (Object jsonMail : jsonArray) {
				jsonObject1 = (JSONObject) jsonMail;
				jsonMails.add(jsonObject1);
			}
			// saving new array
			jsonObject.put("Subject", subject);
			jsonObject.put("Sender", sender);
			jsonObject.put("Date", date);
			jsonObject.put("Priority", priority);
			jsonMails.add(jsonObject);
			object.put("Mails", jsonMails);
		}
        try {
		FileWriter fileWriter = new FileWriter(destinationIndex);
		fileWriter.write(object.toString());
		fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Mail editAndDeleteIndex(String sourceIndex, Mail mail){
		JSONObject object = new JSONObject();
		JSONParser jsonParser = new JSONParser();
		FileReader reader = null;
		try {
			reader = new FileReader(sourceIndex);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		JSONObject json = null;
		try {
			json = (JSONObject) jsonParser.parse(reader);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONArray jsonMails = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = (JSONArray) json.get("Mails");
		for (Object jsonMail : jsonArray) {
			jsonObject = (JSONObject) jsonMail;
			if (jsonObject.get("Subject").equals(mail.getSubject()) && jsonObject.get("Sender").equals(mail.getSender()) && jsonObject.get("Date").equals(mail.getDate())&&jsonObject.get("Priority").equals(mail.getPriority())) {
				continue;
			}
			else {
				jsonMails.add(jsonObject);
			}
		}
		// saving new array

		object.put("Mails", jsonMails);

		try {
			FileWriter fileWriter = new FileWriter(sourceIndex);
			if(!(jsonMails.isEmpty())) {
				fileWriter.write(object.toString());
			}
			else {
				fileWriter.write("");
			}
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		mail.setDate(null);
		return mail;
	}
	
	/////////////////////////////////////////////////////////////////////////
	public void deleteContacts(String []contactToDelete) {
		FileReader x;JSONObject obj=new JSONObject();JSONParser jsonP = new JSONParser();
    	int j=0;
    	LinkedList<String>listOfContacts=new LinkedList<>();
		while(j<contactToDelete.length) {
			listOfContacts.clear();
		try {
			x = new FileReader("accounts//"+email+"//"+"contacts.json");
			BufferedReader br2 = new BufferedReader(x);
			File f=new File("accounts//"+email+"//"+"contacts.json");
			if(f.length()>0) {
				obj=(JSONObject) jsonP.parse(br2);//this will give us all data from this index file.json
				//JSONObject cont=(JSONObject)obj;
				JSONArray contactsArr=(JSONArray) obj.get("Contacts");
				
				if(contactsArr!=null) {
					int len=contactsArr.size();int i=0;
					while(i<contactsArr.size()) {
						if(((String)contactsArr.get(i)).contentEquals(contactToDelete[j])) {
							//don't put it in the list
							System.out.println(contactsArr.size()+"size of arr");
							System.out.println("I found it");
							//contactsArr.remove(i);
						}else {
						listOfContacts.add((String) contactsArr.get(i));
						}
						i++;
					}
				}
				File file=new File("accounts//"+email+"//"+"contacts.json");
				if(listOfContacts.size()>0) {
				JSONObject obj2=new JSONObject();
				JSONArray contactsArray=new JSONArray();
				int i=0;
				System.out.println("list size = "+listOfContacts.size());
				System.out.println(listOfContacts.get(0)+"the rem element");
				while(i<listOfContacts.size()) {
					
					contactsArray.add((String)listOfContacts.get(i));
					i++;
				}
				System.out.println(contactsArray.get(0));
				obj2.put("Contacts", contactsArray);
				DataOutputStream outstream= new DataOutputStream(new FileOutputStream(file,false));
				System.out.println(obj2.toString()+" to be written");
				outstream.write(obj2.toString().getBytes());
				outstream.close(); 
				}else {
					DataOutputStream outstream= new DataOutputStream(new FileOutputStream(file,false));
					outstream.write("".toString().getBytes());
					outstream.close(); 
				}
			
			}else {
				///show error or return false in the proxy
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		j++;
		}
		 //listOfContacts.toArray()
		
	}
	
}
