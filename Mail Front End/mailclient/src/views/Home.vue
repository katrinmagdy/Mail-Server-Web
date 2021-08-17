<template>
  <div class="home">
    <firstlayout></firstlayout>
    <img alt="Vue logo" src="../assets/logo.png" />
    <HelloWorld msg="Welcome to Your Mail Client App" />
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from "@/components/HelloWorld.vue";
import Firstlayout from '../layouts/firstlayout.vue';

export default {
  name: "Home",
  components: {
    HelloWorld,
    Firstlayout
  }
};
</script>
