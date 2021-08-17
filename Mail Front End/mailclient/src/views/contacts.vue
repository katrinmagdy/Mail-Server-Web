<template>
  <div class="contacts">
    <div id="features">

   <img class="icons" src="../assets/showcontacts.png"  @click="onShowContactsClicked()">
    <label id ="labels">Show Contacts </label>

    <img class="icons" src="../assets/addcontact.png"  @click="onAddContactClicked()">
    <label id ="labels">Add Contact </label>

    <img class="icons" src="../assets/deletecontact.png"  @click="onDeleteContactClicked()">
    <label id ="labels">Delete Contact </label>

    <img class="icons" src="../assets/sort.png"  @click="onSortClicked()">
    <label id ="labels">Sort </label>

    <select  id="sort-selector"  :style="{visibility: isSortClicked ? 'visible' : 'hidden'}"  v-model="sortAccordingTo" 
    @change="onSortSelectorChange()">
      <option selected disabled>According to</option>
      
      <option>E-mail</option>
      <option selected >none</option>
    </select>
    </div>

 

   <div id = "search">
    <input type="text" placeholder="search" id="search-input" v-model="wordToSearch" @change="onWriteSearchWord()">
   <img class="icons" src="../assets/search.png"  @click="onSearchClicked()">
  </div>

   <div id='show' :style="{visibility: view ? 'visible' : 'hidden'}">
      <select id="show-box"  multiple>
        <option v-for = "(contact) in contacts" :key="contact"  > {{contact}} </option>
        
     </select>
    
  
  </div>

<div id="addnewcontact" :style="{visibility: isAddContactClicked ? 'visible' : 'hidden'}">

   

    <fieldset class="field">
   <legend> E-mail</legend>
   <input type="text" placeholder="type Contact's E-mail" class="input" v-model="contactEmail">
    </fieldset>

   <button class="Add" @click="addnewcontact()" >Add</button>
   
  </div>
   </div>
</template>

<script>
// @ is an alias to /src

import  axios  from 'axios'
export default {
  name: "contacts",
data() {
    return {
       view:false,
       isShowContactsClicked:false,
       isAddContactClicked:false,
       isDeleteContactClicked:false,
       isSortClicked:false,
       isSearchClicked :false,
       sortAccordingTo:"",
       wordToSearch:"",
      doSearch:true,
       selectedcontactss:[],
        contacts:[],
       extra:"",
       contactEmail:"",
       extra1:"",
    };
  },
 methods:{
      setUnclicked:function () {
     
       this.isAddContactClicked=false;
       this.isDeleteContactClicked=false;
       this.isSortClicked=false;
       this.isSearchClicked =false;
       
      },
       onSortClicked:function(){
         
         this.isSortClicked= true;},

       onSearchClicked:function(){
         this.setUnclicked();
         this.isSearchClicked= true;},

    onSortSelectorChange:function(){
    this.callBackEndSetView();
//axios that will take that value to the back end 
    },
    onWriteSearchWord:function(){
    this.callBackEndSetView();
//axios that will take that value to the back end 
    },
    
 
 onShowContactsClicked:function(){
     this.setUnclicked();
   this.isShowContactsClicked =true;
   this.view=true;
     this.callBackEndSetView();
      
 },
 onAddContactClicked:function(){
    this.setUnclicked();
    this.isAddContactClicked =true;
    
 },
 onDeleteContactClicked:function(){
     this.setUnclicked();
    this.isDeleteContactClicked =true;
    let sel = document.getElementById('show-box');

    axios.delete("http://localhost:8085//deleteFromContacts",{
      params:{
        deleteContact:this.contacts[sel.selectedIndex],
      }
    }).then(response=>{
      this.extra1=response.data;
      this.callBackEndSetView();
    })
 },
 
addnewcontact:function () {
     axios.get("http://localhost:8085//addContact",{
          params : {
        
	newEmail:this.contactEmail,
          }
        }).then(response=>{
          this.extra=response.data; 
       this.callBackEndSetView();
        })
},
 callBackEndSetView:function(){
   //set view options
  
   if(this.wordToSearch==""){
     this.sentWordToSearch="none";
     this.doSearch=false;
     }else{
       this.sentWordToSearch=this.wordToSearch;
       this.doSearch=true;
     }
    axios.get("http://localhost:8085//showContacts",{
          params : {
          
     
	 sortAccordingTo:this.sortAccordingTo,
      searchAccordingTo:this.sentWordToSearch,
      doSearch:this.doSearch,
          }
        }).then(response=>{
          this.contacts=response.data;
          

        }) //getSelectedContacts
 /*getselectedmails(){
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

 },*/
 },
 },
};
</script>
<style>
#features{
padding-top:10px;
padding-left:20px;
width: 200px ;
height: 750px;
background-color: #565f5f;
border:2px solid #000000;
display :grid;
grid-template-columns: auto auto ;
grid-template-rows:  auto auto auto auto auto auto auto ;
float:left;
}
#labels{
  margin-top:10px;
  font-size: 30px;
  font-family:  Cursive;
  font-style : bold ;
  color : #020749;
}
#search{
  display:flex;
  flex-direction: row;
 
}
#search-input{
  width:400px;
  max-height:50px;
  border:4px inset #a10a63;
  margin-left: 150px;
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

#show-box{
   margin: 0px;
   width:800px;
   height: 650px;
   background: rgb(141, 88, 88);
    font-size: 25px;
    font-family:  Cursive;
  font-style : bold ;
  color:#020749 ;
 
 }
 #show{
 width:800px;
 float: left;
 margin-left: 20px;
 margin-right: 20px;
 }

#addnewcontact{
      margin-left:20px ;
      min-height: 80vh;
      display:flex;
    flex-direction: column;
    width: 400px;
   justify-content: center;
   align-items: center;
  background-color:rgb(141, 88, 88);
  margin-top: 20px;
  border:5px double #020749;
 
}
.input{
    border: 2px dashed #020749;
    width : 250px;
    height: 30px;
    
}

.field{
    width:300px;
    border: 5px double;
    margin-bottom: 50px;
    font-size: 40px;
  font-family:  Cursive;
  font-style : bold ;
  color : #020749;
}
.Add{
    
  border: 3px double;
  margin-bottom: 5px;
  font-size: 20px;
  font-family:  Cursive;
  font-style : bold ;
  color : #f1f2f5;
  background: #020749;
  height: 50px;
}
</style>
