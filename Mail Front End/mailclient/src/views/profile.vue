<template>
  <div id = "profile">
  <div id="folders">
    <img class="icons" src="../assets/compose.png"  @click="onComposeClicked()">
    <label id ="labels">Compose Mail </label>

    <img class="icons" src="../assets/inbox.png"  @click="onInboxClicked()">
    <label id ="labels">Inbox </label>

    <img class="icons" src="../assets/sent.png"  @click="onSentClicked()">
    <label id ="labels">Sent </label>

    <img class="icons" src="../assets/trash.png"  @click="onTrashClicked()">
    <label id ="labels">Trash </label>

    <img class="icons" src="../assets/draft.png"  @click="onDraftClicked()">
    <label id ="labels">Draft </label>

    <img class="icons" src="../assets/contacts.png"  @click="onContactsClicked()">
    <label id ="labels">Contacts </label>

    <label id ="features">Features </label>

    <img class="icons" src="../assets/movetodraft.png"  @click="onMoveToDraftClicked()">
    <label id ="labels">Move to draft </label>

    <img class="icons" src="../assets/delete.png"  @click="onDeleteClicked()">
    <label id ="labels">Delete </label>

    <img class="icons" src="../assets/draftsend.png"  @click="onDraftSendClicked()">
    <label id ="labels">Send a draft </label>

    <img class="icons" src="../assets/sort.png"  @click="onSortClicked()">
    <label id ="labels">Sort </label>

    <select  id="sort-selector"  :style="{visibility: isSortClicked ? 'visible' : 'hidden'}"  v-model="sortAccordingTo" 
    @change="onSortSelectorChange()">
      <option selected disabled>According to</option>
      <option>Subject</option>
      <option>Sender</option>
      <option>Content</option>
      <option>DateDesc</option>
      <option>DateAsc</option>
      <option selected >none</option>
    </select>


    <img class="icons" src="../assets/filter.png"  @click="onFilterClicked()">
    <label id ="labels">Filter </label>

    <select  id="filter-selector"  :style="{visibility: isFilterClicked ? 'visible' : 'hidden'}"  v-model="filterAccordingTo" >
      <option selected disabled>According to</option>
      <option>Subject</option>
      <option>Sender</option>
       <option selected >none</option>
    </select>
    <input type="text" placeholder="Word To Filter"  :style="{visibility: isFilterClicked ? 'visible' : 'hidden'}"
     id="filter-input" v-model="wordToFilter" @change="onWriteFilterWord()">


  </div>
  <div id = "search">
    <input type="text" placeholder="search" id="search-input" v-model="wordToSearch" @change="onWriteSearchWord()">
   <img class="icons" src="../assets/search.png"  @click="onSearchClicked()">
  </div>

   <div  :style="{visibility: view ? 'visible' : 'hidden'}">
      <select id="show-box"   multiple>
        <option @dblclick="onMailView() " >{{list[0]}}</option>
        <option @dblclick="onMailView() " >{{list[1]}}</option>
        <option @dblclick="onMailView() " >{{list[2]}}</option>
        <option @dblclick="onMailView() " >{{list[3]}}</option>
        <option @dblclick="onMailView() " >{{list[4]}}</option>
        <option @dblclick="onMailView() " >{{list[5]}}</option>
        <option @dblclick="onMailView() " >{{list[6]}}</option>
        <option @dblclick="onMailView() " >{{list[7]}}</option>
        <option @dblclick="onMailView() " >{{list[8]}}</option>
        <option @dblclick="onMailView() " >{{list[9]}}</option>
     </select>
     <br>
  <img id="page-icons" src="../assets/previous.png"  @click="onPreviousClicked()">
  <input type="text" id="pageInput" placeholder="search"  v-model="currentPage" @change="onCurrentPageChanged()">
  <img id="page-icons" src="../assets/next.png"  @click="onNextClicked()">
  </div>

  </div>
</template>

<script>
// @ is an alias to /src
//import router from "../router"
import  axios  from 'axios'

export default {
  name: "Profile",
  data() {
    return {
        view:false,
       isInboxClicked:false,
       isSentClicked:false,
       isDraftClicked:false,
       isContactsClicked:false,
       isTrashClicked:false,
       isComposeClicked:false,
       isFilterClicked:false,
       isSortClicked:false,
       isSearchClicked :false,
       isMoveToDraftClicked :false,
       isDeleteClicked :false,
       isDraftSendClicked :false,
       sortAccordingTo:"none",
       filterAccordingTo:"none",
       wordToFilter:"",
       wordToSearch:"",
       list:[],
       fulldata:[],
       mailtoshow:[],
       selectedmails:[],
       currentPage:1,
       sender:this.$route.params.sender,
       currentFolder:"",
       doSearch:true,
       showingInboxDefault:"None",
       sentWordToSearch:"",
       extra:"",
       inboxDecodeString:"",
       othersDecodeString:"",
    };
  },
   // props: ['sender'],
 methods:{
      setUnclicked:function () {
       this.isInboxClicked=false;
       this.isSentClicked=false;
       this.isDraftClicked=false;
       this.isTrashClicked=false;
       this.isComposeClicked=false;
       this.isFilterClicked=false;
       this.isSortClicked=false;
       this.isSearchClicked =false;
       
       
      },
      setFeaturesUnClicked:function(){
       this.isMoveToDraftClicked =false;
       this.isDeleteClicked =false;
       this.isDraftSendClicked=false;
      },

       onInboxClicked:function(){
           this.setUnclicked();
           this.currentPage=1;
         this.isInboxClicked= true;
         this.view=true;
         this.currentFolder="Inbox";

         let str = prompt("Enter \"default\" to show Inbox according to date and Enter \"priority\" to show Inbox according to priority","");
          var condition=true;
          while(condition){
            if(str.trim()=="default"){
            this.showingInboxDefault="default";
            condition=false;
          }
          else if(str.trim()=="priority"){
             this.showingInboxDefault="priority";
             this.sortAccordingTo="priority";
             condition=false;
          }
          else{
           str = prompt("Wrong input \nEnter \"default\" to show Inbox according to date and Enter \"priority\" to show Inbox according to priority","");
          }
          }
         
         this.callBackEndSetView();
        /* this.fulldata.push(["katy,eman,sara","farida","Hy katrin","urgent","hyyyyyyyyyyy","attach","20/1/2020"]);
         this.fulldata.push(["katy,eman,sara","eman","Hy katrin","urgent","hyyyyyyyyyyy","attach","2/5/2020"]);
         this.fulldatatoList();*/},

       onSentClicked:function(){
         this.setUnclicked();
         this.currentPage=1;
         this.isSentClicked= true;
         this.view=true;
         this.currentFolder="sent";
         this.callBackEndSetView();
         },

       onDraftClicked:function(){
         this.setUnclicked();
         this.currentPage=1;
         this.isDraftClicked= true;
         this.view=true;
         this.currentFolder="Drafts";
         this.callBackEndSetView();
         },

       onTrashClicked:function(){
         this.setUnclicked();
         this.currentPage=1;
         this.isTrashClicked= true;
         this.view=true;
         this.currentFolder="Trash";
         this.callBackEndSetView();},

       onComposeClicked:function(){
         this.setUnclicked();
         this.isComposeClicked= true;
         console.log(this.sender);
        // router.push('/composeMail/'+this.sender)
        let route1 = this.$router.resolve({path: '/composeMail/'+this.sender});
        window.open(route1.href, '_blank');
         },
         onFilterClicked:function(){
         this.setUnclicked();
         this.isFilterClicked= true;
         },
       onSortClicked:function(){
         this.setUnclicked();
         this.isSortClicked= true;},
       onSearchClicked:function(){
         this.setUnclicked();
         this.isSearchClicked= true;},
    onSortSelectorChange:function(){
    console.log(this.sortAccordingTo);
//axios that will take that value to the back end 
    this.callBackEndSetView();
    },
    onWriteSearchWord:function(){
    console.log(this.wordToSearch);
//axios that will take that value to the back end 
   this.callBackEndSetView();
    },
    onWriteFilterWord:function(){
    console.log(this.wordToFilter);
    console.log(this.filterAccordingTo);
//axios that will take that value to the back end 
   this.callBackEndSetView();
    },
 //move from sent to draft
 onMoveToDraftClicked:function(){
     this.setFeaturesUnClicked();
   this.isMoveToDraftClicked =true;
   if(this.isSentClicked){
     //function shafra to convert selected mails
    this.inboxDecode();
     //axios 
      axios.get("http://localhost:8085//moveEmailsFromSent",{
          params : {
            selectedMails:this.inboxDecodeString,
          
          }
        }).then(response=>{
          this.extra=response.data;
          this.callBackEndSetView();
        }) 
     
   }else{
     alert("you are not in the sent mails you can't move to draft");
   }
      
 },

 onDeleteClicked:function(){
     this.setFeaturesUnClicked();
    this.isDeleteClicked =true;
    //function shafra to convert selected mails
    if(this.isTrashClicked){
      alert("you can't delete from trash");
    }else{
   this.inboxDecode();
    //axios to delete
    axios.delete("http://localhost:8085//deleteEmailsFrom",{
          params : {
            selectedMails:this.inboxDecodeString,
             from:this.currentFolder,
          
          }
        }).then(response=>{
          this.extra=response.data;
          this.callBackEndSetView()
        }) 
    }
 },
 onDraftSendClicked:function(){
 this.setFeaturesUnClicked();
 this.isDraftSendClicked=true;
 if(this.isDraftClicked){
   //function shafra to convert selected mails type inbox
 this.getselectedmails();
 if(this.selectedmails.length>1){
   alert("choose only one mail to sent")
 }else{
   axios.get("http://localhost:8085//sentFromDraft",{
   params:{
          sender:this.selectedmails[0][1] , 
          subject:this.selectedmails[0][2],
          content:this.selectedmails[0][4],
          priority:this.selectedmails[0][3],
          recievers :this.selectedmails[0][0],
          attachments :this.selectedmails[0][5],
          date:this.selectedmails[0][6],
        }
 }).then(response=>{
          this.extra=response.data;
          this.callBackEndSetView()
 })
    } //axios 
    
   }else{
     alert("you are not in the draft mails you can't send this mail only draft's mails");
   }
 },
 onCurrentPageChanged:function(){
//axios will call the backend and send to it the variable current page and take a new array of messages to show of array contains only 
//one string with is invalid message to show on the same way

this.callBackEndSetView();
 },
 onPreviousClicked:function(){
   if(this.currentPage>1){
      this.currentPage=this.currentPage-1;
      this.onCurrentPageChanged();}
 },
 onNextClicked:function(){
   this.currentPage=this.currentPage+1;
   this.onCurrentPageChanged();
   if(this.list.length==0){
 this.currentPage=this.currentPage-1;
 alert("No more mails to show");
   }
 },

 onMailView:function(){
   this.mailtoshow=[];
   var sel = document.getElementById('show-box');
   if(sel.selectedIndex<this.fulldata.length){
   for(var i=0;i<7;i++){
     this.mailtoshow.push(this.fulldata[sel.selectedIndex][i]);
   }
   }
   console.log(this.mailtoshow);
//axios will send the data to be temporarly saved in backend until we call it in the next tab page
axios.get("http://localhost:8085//setViewMail",{
   params:{
          sender:this.mailtoshow[1] , 
          subject:this.mailtoshow[2],
          content:this.mailtoshow[4],
          priority:this.mailtoshow[3],
          recievers :this.mailtoshow[0],
          attachments :this.mailtoshow[5],
        }
 })
  let route = this.$router.resolve({path: '/viewMail'});
      window.open(route.href, '_blank');
 },
 fulldatatoList(){
   this.list=[];
   for(var i=0;i<this.fulldata.length;i++){
     var str ="From: " +this.fulldata[i][1]+",Subject: "+this.fulldata[i][2]+",Date: "+this.fulldata[i][6] +"Priority: "+this.fulldata[i][3];
     this.list.push(str);
   }
 },
 getselectedmails(){
   this.selectedmails =[];
   var sel = document.getElementById('show-box');
    // loop through options in select list
    for (var i=0; i<sel.options.length; i++) {
        var opt = sel.options[i];
        // check if selected
        if ( opt.selected && i< this.fulldata.length ) {
            // add to array of option elements to return from this function
            this.selectedmails.push(this.fulldata[i]);
        }
    }
    console.log(this.selectedmails);

 },
 onContactsClicked:function () {
    this.setUnclicked();
    this.isContactsClicked= true;
    let route = this.$router.resolve({path: '/contacts'});
    window.open(route.href, '_blank');
 },
 callBackEndSetView:function(){
   //set view options
   if(this.wordToFilter==""){this.wordToFilter="none";}
   if(this.wordToSearch==""){
     this.sentWordToSearch="none";
     this.doSearch=false;
     }else{
       this.sentWordToSearch=this.wordToSearch;
       this.doSearch=true;
     }
    axios.get("http://localhost:8085//setViewingOptions",{
          params : {
          
      currentFolder:this.currentFolder,
			showingInboxDefault:this.showingInboxDefault,
			 filterAccordingTo:this.filterAccordingTo,
			 wordToFilter:this.wordToFilter,
		  sortAccordingTo:this.sortAccordingTo,
      searchAccordingTo:this.sentWordToSearch,
      doSearch:this.doSearch,
          }
        }).then(response=>{
          this.extra=response.data;
           //set the current page 
           axios.get("http://localhost:8085//getCurrentPageMails",{
          params : {
           page:this.currentPage,
          }
        }).then(response=>{
          this.fulldata=response.data;
           this.fulldatatoList();
        }) 

        })  
 },

 inboxDecode:function(){
   this.getselectedmails();
   this.inboxDecodeString="";
for(var i=0;i<this.selectedmails.length;i++){
 /* this.inboxDecodeString = this.inboxDecodeString+this.sender+"#"+this.selectedmails[i][1]+"#"
  +this.selectedmails[i][2]+"#"+this.selectedmails[i][6]+"," ;*/

  this.inboxDecodeString = this.inboxDecodeString.concat(this.inboxDecodeString,this.sender,"#",this.selectedmails[i][1],"#"
  ,this.selectedmails[i][2],"#",this.selectedmails[i][6],"," );
}
 this.inboxDecodeString=this.inboxDecodeString.slice(0,-1);
console.log(this.inboxDecodeString);

 },
 othersDecode:function(){
   this.getselectedmails();
   this.othersDecodeString="";
for(var i=0;i<this.selectedmails.length;i++){
 /* this.inboxDecodeString = this.inboxDecodeString+this.sender+"#"+this.selectedmails[i][1]+"#"
  +this.selectedmails[i][2]+"#"+this.selectedmails[i][6]+"," ;*/

  this.othersDecodeString = this.othersDecodeString.concat(this.othersDecodeString,this.selectedmails[i][1],"#"
  ,this.selectedmails[i][2],"#",this.selectedmails[i][6],"," );
}
 this.othersDecodeString=this.othersDecodeString.slice(0,-1);
console.log(this.othersDecodeString);

 },
 },
};

</script>

<style >
#folders{
padding-top:10px;
padding-left:20px;
width: 200px ;
height: 1000px;
background-color: #565f5f;
border:2px solid #000000;
display :grid;
grid-template-columns: auto auto ;
grid-template-rows:  auto auto auto auto auto auto auto auto auto auto auto auto auto auto auto auto;
float:left;
}
#labels{
  margin-top:10px;
  font-size: 30px;
  font-family:  Cursive;
  font-style : bold ;
  color : #020749;
}
#features{
  font-size: 30px;
  font-family:  Cursive;
  font-style : bold ;
  color : #dba510;
  grid-column: 1/3;
  margin-top:10px;
}
#search{
  display:flex;
  flex-direction: row;
 
}
#search-input{
  width:400px;
  max-height:50px;
  border:4px inset #a10a63;
  margin-left: 350px;
  margin-right: 30px;
  
}
#sort-selector{
  grid-column: 1/3;
   font-size: 20px;
   color : #020749;
   font-family:  Cursive;
  font-style : bold ;
  background:rgb(141, 88, 88);
  margin-bottom: 10px;
  margin-right: 10px;
}
#filter-selector{
  grid-column: 1/3;
   font-size: 20px;
   color : #020749;
   font-family:  Cursive;
  font-style : bold ;
  background:rgb(141, 88, 88);
  margin-bottom: 10px;
  margin-right: 10px;
}
#filter-input{
  grid-column: 1/3;
   font-size: 20px;
   color : #020749;
   font-family:  Cursive;
  font-style : bold ;
  background:rgb(141, 88, 88);
  max-width: 60px;}
 #show-box{
   margin: 0px;
   width:1100px;
   height: 750px;
   background: rgb(141, 88, 88);
    font-size: 25px;
    font-family:  Cursive;
  font-style : bold ;
  color:#020749 ;
 }
#pageInput{
  width:20px;

   background: rgb(141, 88, 88);
    font-size: 25px;
    font-family:  Cursive;
  font-style : bold ;
  color:#020749 ;
}
#page-icons{
  margin-right: 150px ;
  margin-left: 150px;
  
}
</style>