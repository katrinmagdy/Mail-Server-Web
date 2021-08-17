<template>
<div>
<firstlayout></firstlayout>
  <div id="signin">
   <fieldset class="field">
   <legend> E-mail</legend>
   <input type="text" placeholder="type your E-mail" class="input" v-model="email">
    </fieldset>

    <fieldset class="field">
   <legend> Password</legend>
   <input type="password" placeholder="type your password" class="input" v-model="password">
   </fieldset>

   <button class="submit" @click="signIn()" >SignIn</button>
   
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
  name: "signin",
   data() {
    return {
      email:"",
    password:"",
    valid:false
    };
  },
 methods:{
      signIn:function () {
        this.email=this.email.trim();
        this.password = this.password.trim();

        if(this.email&&this.password){
          //axios will send email and password and recieve boolean will be saved in valid
         axios.get("http://localhost:8085//signin",{
          params : {
           email:this.email,
           password:this.password,
           
          }
        }).then(response=>{
          this.valid=response.data;
           if(this.valid){
              //router.push({ name: "profile" , params: { sender: this.email } });
              router.push('/profile/'+ this.email);
          }else{
              alert("Email or password are wrong");
          }
        }) 
         
        }
        else{
          alert("all fields must be filled");
        }
            
      }
 },
}
</script>
<style>
  #signin{
      margin: auto;
      min-height: 80vh;
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
    width:300px;
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
