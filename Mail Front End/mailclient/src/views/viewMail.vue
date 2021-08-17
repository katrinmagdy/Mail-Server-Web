<template>
  <div id="viewMail">
    <h1 id="title" >View Mail</h1>

    <fieldset class="field">
   <legend> From</legend>
   <label type="text" class="input" > {{sender}} </label>
    </fieldset>

    <fieldset class="multi-field">
   <legend> TO</legend>
   <select  id="r"  multiple>
       <option v-for = "(reciever) in recievers" :key="reciever"  > {{reciever}} </option>
   </select>

    </fieldset>

    <fieldset class="field">
   <legend> Subject</legend>
    <label type="text" class="input" > {{subject}} </label>
    </fieldset>

    <fieldset class="multi-field">
   <legend> Content </legend>
   <textarea type="text" placeholder="Message body" class="textarea" v-model="content" readonly> </textarea>
    </fieldset>

<fieldset class="multi-field">
   <legend> Attachments</legend>
   <select  id="a" multiple>
      <option v-for="(attachment) in attachments" :key="attachment" 
       @click="onAttachmentClicked()"> {{attachment}} </option>
   </select>

</fieldset>

<fieldset class="field">
   <legend> Priority</legend>
  <label type="text" class="input" > {{priority}} </label>
    </fieldset>
 

  </div>
</template>

<script>
// @ is an alias to /src
import  axios  from 'axios'

export default {
  name: "composeMail",
   data() {
    return {
        viewedMail:[],
        receviersString:"",
        attachmentsString:"",
        sender:"",
        recievers:[],
        subject:"",
        content:"",
        attachments:[],
        priority:"",
        valid:false,
    };
  },
  created() {
  axios.get("http://localhost:8085//getViewMail",{
        
        }).then(response=>{
          this.viewedMail=response.data; 
          this.sender=this.viewedMail[1];
          this.receviersString=this.viewedMail[0];
          this.subject=this.viewedMail[2];
          this.content=this.viewedMail[4];
          this.attachmentsString=this.viewedMail[5];
          this.priority=this.viewedMail[3];
          this.recievers =this.receviersString.split(",").map(item=>item.trim());
          this.attachments =this.attachmentsString.split(",").map(item=>item.trim());
          })
    },
 methods:{
    
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
          this.valid=response.data;
          if(!this.valid){
            alert("File doesn`t exist");
          }

        })
    },
    
},
};
</script>
<style >
#viewMail{
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
    font-size: 30px;
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

#title{
  margin: 0px;
  font-size: 40px;  
  font-family:  Cursive;
  font-style : bold ;
  color : #020749;  
}
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
</style>