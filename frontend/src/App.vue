<template>
  <div id="app">
    <NavBar />
    <router-view />
  </div>
</template>

<script setup>
import NavBar from "@/components/NavBar.vue";
</script>

<style>
body {
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 0;
  background-color: #f4f6f8;
}
</style>
