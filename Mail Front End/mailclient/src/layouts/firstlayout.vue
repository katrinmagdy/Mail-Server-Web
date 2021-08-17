<template>
  <div>
      <div id ="header">
   <img class="home" src="../assets/home.png" @click="$router.push('home')">
   <label id ="title"><span class="first-letter">M</span>ail Client </label>
   <label id ="navigation-label" @click="$router.push('signup')">SignUp</label>
   <label id ="navigation-label" @click="$router.push('signin')">SignIn</label>
   </div>
     <slot/>
  </div>
</template>

<script>
export default {

}
</script>

<style>
#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #197e50;
}

#header {
display:flex;
flex-direction: row;
background-color: #020749;

}
.first-letter{
  font-size: 100px;
  line-height: 70px;
  font-family:  serif;
  font-style : bold ;
  color : #0af5aed7;
}
#title{
  font-size: 40px;
  font-family: serif;
  margin-right: 1000px;
  color : #f5edede5;
  
}
#navigation-label{
  font-size: 30px;
  font-family: serif;
  margin-right: 50px;
  color : #f5edede5;
  margin-top: 45px;
  
}
.home{
 margin-left: 10px;
 margin-right: 10px;
}

</style>