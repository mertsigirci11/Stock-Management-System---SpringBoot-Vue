<template>
  <div>
      <navigation-bar></navigation-bar>
      <br>
  </div>
  <main>
    <router-view ></router-view>
  </main>
</template>

<script>
import "bootstrap/dist/css/bootstrap.min.css";
import NavigationBar from './components/NavBar/NavigationBar.vue';
export default{
  components:{
    NavigationBar,
  },
  
}
</script>

<style>
</style>