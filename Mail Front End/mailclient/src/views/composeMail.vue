<template>
  <div id="composeMail">
    <h1 id="title" >Compose Mail</h1>

    <fieldset class="field">
   <legend> From</legend>
   <label type="text" class="input" > {{sender}} </label>
    </fieldset>

    <fieldset class="multi-field">
   <legend> TO</legend>
   <input type="text" placeholder="Recievers" class="input" v-model="currentReciever" @change="onEnterReciever()">
   <br>
   <select id="r" multiple>
       <option v-for = "(reciever) in recievers" :key="reciever" @dblclick="OnDoubleClick()" > {{reciever}} </option>
   </select>

    </fieldset>

   

    <fieldset class="field">
   <legend> Subject</legend>
   <input type="text" placeholder="Subject" class="input" v-model="subject">
    </fieldset>

    <fieldset class="multi-field">
   <legend> Content </legend>
   <textarea type="text" placeholder="Message body" class="textarea" v-model="content"> </textarea>
    </fieldset>

<fieldset class="multi-field">
   <legend> Attachments</legend>
    <input type="text" placeholder="Enter the path of your attachment" class="input" v-model="currentAttachment"
     @change="onEnterAttachment()">
    <br>
   <select id="a" multiple>
      <option v-for="(attachment) in attachments" :key="attachment"
       @dblclick="OnDoubleClickAttach()" > {{attachment}} </option>
   </select>
<button id ="openattach" @click="onAttachmentClicked()">open</button>
    </fieldset>

<fieldset class="multi-field">
   <legend> Priority</legend>
  <select id="priority-box" @change="onChangePriority($event)" >
         <option disabled selected>None</option>
        <option >urgent/important</option>
        <option >not urgent/important</option>
        <option >urgent/not important</option>
        <option >not urgent/not important</option> 
     </select>
    </fieldset>
 
<div id="optionstosave">
<button class="send" @click="onSendClick()">Send</button>
<button class="send" @click="onDraftClick()">Draft</button>
</div>
  </div>
</template>

<script>
// @ is an alias to /src
import  axios  from 'axios'

export default {
  name: "composeMail",
   data() {
    return {
        sender:this.$route.params.sender,
        recieversstring:"",
        Attachstring:"",
        recievers:[],
        subject:"",
        content:"",
        attachments:[],
        currentReciever:"",
        currentAttachment:"",
        priority:"None",
        valid:false,
        attachvalid:false,
    };
  },
 methods:{

     onSendClick:function(){
      this.subject=this.subject.trim();
    //axios will send the data as it is to the back end 
    if (this.subject==""||this.content.trim()==""||this.recievers.length== 0||this.priority =="None"){
      alert("content or recivers or subject are empty please fill them first and priority can`t be None");
    }else{
       if(this.subject.includes("\\")||this.subject.includes("/")||this.subject.includes(":")||this.subject.includes("*")||this.subject.includes("?")||this.subject.includes("\"")||this.subject.includes("<")||this.subject.includes(">")||this.subject.includes("|")){
            alert("Subject can`t contain \\:*?\"<>|");
          }
          else{
      this.recieversToString();
      this.AttachToString();
       axios.get("http://localhost:8085//composeNewMail",{
          params:{
          sender:this.sender , 
          subject:this.subject,
          content:this.content,
          priority:this.priority,
          recievers :this.recieversstring,
          attachments :this.Attachstring,
          to : "inbox",
         }
        
        }).then(response=>{
          this.valid=response.data;
          if(this.valid){
       alert("Mail sent");
          }else{
      alert("some of your recievers are fake");
          }
          })

          }
      
    }
    },
    onEnterReciever:function() {
      this.recievers.push(this.currentReciever);  
    },
    onEnterAttachment:function() {
      this.attachments.push(this.currentAttachment);  
    },
    onAttachmentClicked:function () {
      var sel = document.getElementById('a');
      var str = this.attachments[sel.selectedIndex] ;
      str=str.trim();
        //axios that calls the back end and give it that path to open it 
        axios.get("http://localhost:8085//viewAttach",{
          params:{
          path:str,
         }
        }).then(response=>{
          this.attachvalid=response.data;
          if(!this.attachvalid){
            alert("File doesn`t exist");
          }
        })
    },
    onDraftClick:function () {
       this.subject=this.subject.trim();
      //axios put the mail to the draft
      if (this.subject==""||this.content.trim()==""||this.recievers.length== 0||this.priority =="None"){
      alert("content or recivers or subject are empty please fill them first and priority can`t be None");
    }else{
      if(this.subject.includes("\\")||this.subject.includes("/")||this.subject.includes(":")||this.subject.includes("*")||this.subject.includes("?")||this.subject.includes("\"")||this.subject.includes("<")||this.subject.includes(">")||this.subject.includes("|")){
            alert("Subject can`t contain \\:*?\"<>|");
          }
          else{
            this.recieversToString();
      this.AttachToString();
       axios.get("http://localhost:8085//composeNewMail",{
          params:{
          sender:this.sender , 
          subject:this.subject,
          content:this.content,
          priority:this.priority,
          recievers :this.recieversstring,
          attachments :this.Attachstring,
          to : "draft",
         }
        
        }).then(response=>{
          this.valid=response.data;
          if(this.valid){
       alert("Mail saved to draft");
          }else{
      alert("some of your recievers are fake");
          }
          })

          }
       
    }
    },
    onChangePriority:function (event) {
      this.priority=event.target.value;
      },

      OnDoubleClick(){
      var sel = document.getElementById('r');
      if(sel.selectedIndex==0){
        this.recievers.shift();
      }
      else{
        this.recievers.splice(sel.selectedIndex,sel.selectedIndex);
      }

    },
    OnDoubleClickAttach(){
      var sel = document.getElementById('a');
      if(sel.selectedIndex==0){
        this.attachments.shift();
      }
      else{
        this.attachments.splice(sel.selectedIndex,sel.selectedIndex);
      }

    },

    recieversToString(){
      var str=this.recievers[0].trim();
      for(var i=1;i<this.recievers.length;i++){
        str=str+","+this.recievers[i].trim();
      }
      this.recieversstring=str;
    },

    AttachToString(){
      var str="";
      if(this.attachments.length==0){
        this.Attachstring="None";
      }
      else{
        str=this.attachments[0].trim();
      for(var i=1;i<this.attachments.length;i++){
        str=str+","+this.attachments[i].trim();
      }
      this.Attachstring=str;
      }
    },
},
};
</script>
<style >
#composeMail{
    margin: auto;
    min-height: 100vh;
    display:flex;
    flex-direction: column;
    width: 800px;
   justify-content: center;
   align-items: center;
  background-color: #13be8bd7;
  margin-top: 20px;
  border:5px double #020749;
    
}
.input{
    border: 2px dashed #020749;
    width : 250px;
    height: 30px; 
}
.textarea{
     border: 2px dashed #020749;
    width : 300px;
    height: 150px; 
}

.field{
    width: 400px;
    height: 100px;
    border: 5px double;
    margin-bottom: 30px;
    font-size: 40px;
    font-family:  Cursive;
    font-style : bold ;
    color : #020749;
}
.multi-field{
    width: 400px;
    height: 200px;
    border: 5px double;
    margin-bottom: 30px;
    font-size: 20px;
    font-family:  Cursive;
    font-style : bold ;
    color : #020749;}
.send{
    border: 3px double;
    margin-bottom: 5px;
    font-size: 20px;
    font-family:  Cursive;
    font-style : bold ;
    color : #f1f2f5;
    background: #020749;
    height: 50px;
    margin-right: 30px;
}
#title{
  margin: 0px;
  font-size: 40px;  
  font-family:  Cursive;
  font-style : bold ;
  color : #020749;  
}
#priority-box{
   font-size: 20px;  
  font-family:  Cursive;
  font-style : bold ;
  color : #eff0f5; 
  background:#020749; 
  margin-top: 10px;
  margin-bottom: 10px;
   border: 2px dashed #020749;
}
#a{
  font-size: 20px;  
  font-family:  Cursive;
  font-style : bold ;
  color : #eff0f5; 
  background:#020749; 
  margin-top: 10px;
  margin-bottom: 10px;
   border: 2px dashed #020749;
   min-width:150px;
   max-width: 300px;}

   #r{
  font-size: 20px;  
  font-family:  Cursive;
  font-style : bold ;
  color : #eff0f5; 
  background:#020749; 
  margin-top: 10px;
  margin-bottom: 10px;
   border: 2px dashed #020749;
   min-width:150px;
   max-width: 300px;}
#optionstosave{
 display:flex;
  flex-direction: row;
}
</style>