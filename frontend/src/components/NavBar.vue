<script setup>
import axiosInstance from "@/api/axiosInstance";
import router from "@/router";
import store from "@/store/index.js";
import { computed, onMounted, onUnmounted } from "vue";


const isAuthenticated = computed(() => store.state.isAuthenticated);
const isAdmin = computed(() => store.getters.isAdmin);
const isUser = computed(() => store.getters.isUser);

function checkAuthenticationStateAndUpdateStore() {
  const accessToken = localStorage.getItem("access_token");
  const refreshToken = localStorage.getItem("refresh_token");

  if (!accessToken && !refreshToken) {
    store.dispatch("logout");
    router.push("/login");
  } else if (!store.state.isAuthenticated) {
    try {
      const decodedToken = JSON.parse(atob(accessToken.split(".")[1]));
      const userRole = decodedToken.role;
      store.dispatch("login", userRole);

      axiosInstance.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
    } catch (error) {
      console.error("Error decoding token:", error);
      store.dispatch("logout");
      router.push("/login");
    }
  }
}

onMounted(() => {
  window.addEventListener("storage", checkAuthenticationStateAndUpdateStore);
});

onUnmounted(() => {
  window.removeEventListener("storage", checkAuthenticationStateAndUpdateStore);
});

const logout = async () => {
  try {
    await axiosInstance.post("auth/logout");

    localStorage.removeItem("access_token");
    localStorage.removeItem("refresh_token");

    await store.dispatch("logout");

    window.dispatchEvent(new Event("localStorage"));

    await router.push("/login");
  } catch (error) {
    console.error("Logout error:", error);
  }
};
</script>

<template>
  <nav class="navbar navbar-light navbar-expand-md fixed-top navbar-shrink py-3" id="mainNav">
    <div class="container">
      <router-link class="navbar-brand d-flex align-items-center" to="/"> <span>ORT online</span> </router-link>
      <button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-1">
        <span class="visually-hidden">Toggle navigation</span>
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navcol-1">
        <ul class="navbar-nav mx-auto">
          <li class="nav-item">
            <router-link v-if="isAuthenticated" class="nav-link" to="/tests">Тесты</router-link>
          </li>
<!--          <li v-if="isAuthenticated" class="nav-item">-->
<!--            <router-link class="nav-link" to="/authenticated">Authenticated</router-link>-->
<!--          </li>-->
          <li v-if="isAdmin" class="nav-item">
            <router-link class="nav-link" to="/admin">ADMIN</router-link>
          </li>
          <li v-if="isUser" class="nav-item">
            <router-link class="nav-link" to="/user">USER</router-link>
          </li>
          <li v-if="isUser" class="nav-item">
            <router-link class="nav-link" to="/profile">Профиль</router-link>
          </li>
        </ul>
        <router-link v-if="!isAuthenticated" class="btn btn-outline-primary" to="/login">Войти</router-link>
        <router-link v-if="!isAuthenticated" class="btn btn-outline-secondary" to="/signup">Зарегистрироваться</router-link>
        <button v-if="isAuthenticated" class="btn btn-outline-primary" @click="logout">Выйти</button>
      </div>
    </div>
  </nav>
</template>

<style scoped></style>
