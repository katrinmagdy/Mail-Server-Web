<template>
<div>
  <firstlayout></firstlayout>
  <div id="signup">
  <fieldset class="field">
   <legend>Name</legend>
   <input type="text" placeholder="type your Name" class="input" v-model="name">

    </fieldset>
   <fieldset class="field">
   <legend> E-mail</legend>
   <input type="text" placeholder="type your E-mail" class="input" v-model="email">
    </fieldset>

    <fieldset class="field">
   <legend> Password</legend>
   <input type="password" placeholder="type your password" class="input" v-model="password">
   </fieldset>

   <button class="submit" @click="signUp()">SignUp</button>
  </div>
  </div>
</template>

<script>
import firstlayout from '../layouts/firstlayout.vue';
// @ is an alias to /src

import router from "../router"
import  axios  from 'axios'

export default {
  components: { firstlayout },
  name: "signup",
   data() {
    return {
         name:"",
      email:"",
    password:"",
    valid:false,
    };
  },
 methods:{
      signUp:function () {
        this.name=this.name.trim();
        this.email=this.email.trim();
        this.password = this.password.trim();

        if(this.name&&this.email&&this.password){
          if(this.email.includes("\\")||this.email.includes("/")||this.email.includes(":")||this.email.includes("*")||this.email.includes("?")||this.email.includes("\"")||this.email.includes("<")||this.email.includes(">")||this.email.includes("|")){
            alert("Email can`t contain \\:*?\"<>|");
          }
          else{
             //axios will send email and password and recieve boolean will be saved in valid
         axios.get("http://localhost:8085//signup",{
          params : {
           email:this.email,
           password:this.password,
           username:this.name,
          }
        }).then(response=>{
          this.valid=response.data;
           if(this.valid){
              //router.push({ name: "profile" , params: { sender: this.email } });
              router.push('/profile/'+ this.email);
          }else{
              alert("Email already exist enter another name");
          }
        }) 

          }
         
         
        }
        else{
          alert("all fields must be filled");
        }
            
      }
 
 },
};
</script>
<style>
  #signup{
      margin: auto;
      min-height: 100vh;
      display:flex;
    flex-direction: column;
    width: 600px;
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

.field{
    width: 300px;
    border: 5px double;
    margin-bottom: 50px;
    font-size: 40px;
  font-family:  Cursive;
  font-style : bold ;
  color : #020749;
}
.submit{
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
