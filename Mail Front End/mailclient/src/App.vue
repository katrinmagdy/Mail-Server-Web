<template>
  <div id="app">
     <router-view />
  </div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

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
