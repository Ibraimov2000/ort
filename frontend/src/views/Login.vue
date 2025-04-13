<script setup>
import { ref } from "vue";
import store from "@/store";
import router from "@/router";
import authService from "@/services/authService.js";

const loginRequest = ref({
  email: "",
  password: "",
});
const errorMessage = ref("");
const sessionExpired = ref(false);

async function login() {
  try {
    clearMessages();
    const token = await authService.login(loginRequest.value);
    const userRole = extractUserRoleFromToken(token);
    await store.dispatch("login", {userRole});
    await router.push("/tests");
  } catch (error) {
    if (error.response) {
      showErrorMessage(error.response.data.message);
    } else if (error.request) {
      showErrorMessage("Unable to connect to the server. Please try again later.");
    } else {
      showErrorMessage("An error occurred while processing your request.");
    }
  }
}

function clearMessages() {
  errorMessage.value = "";
  sessionExpired.value = false;
}

function showErrorMessage(message) {
  errorMessage.value = message;
}

function extractUserRoleFromToken(token) {
  try {
    return JSON.parse(atob(token.split(".")[1])).role;
  } catch (e) {
    console.error("Error decoding token:", e);
    return null;
  }
}

if (router.currentRoute.value.query.sessionExpired) {
  sessionExpired.value = true;
}
</script>

<template>
  <section class="py-4 py-md-5 my-5">
    <div class="container py-md-5">
      <div class="row">
        <div class="col-md-6 text-center">
          <img
              class="img-fluid w-100"
              src="../assets/img/illustrations/login.svg"
              alt="login-img"
          />
        </div>
        <div class="col-md-5 col-xl-4 text-center text-md-start">
          <h2 class="display-6 fw-bold mb-5">
            <span class="underline pb-1">
              <strong>Login</strong>
            </span>
          </h2>
          <form @submit.prevent="login">
            <div class="mb-3">
              <input
                  class="shadow form-control"
                  v-model="loginRequest.email"
                  required
                  type="email"
                  name="email"
                  placeholder="Email"
              />
            </div>
            <div class="mb-3">
              <input
                  class="shadow form-control"
                  v-model="loginRequest.password"
                  type="password"
                  name="password"
                  placeholder="Password"
              />
            </div>
            <div class="mb-5">
              <button class="btn btn-primary shadow" type="submit">Log in</button>
            </div>
            <div v-if="errorMessage" class="alert alert-danger">
              {{ errorMessage }}
            </div>
            <div v-if="sessionExpired" class="alert alert-warning">
              Your session has expired.
              <br />
              Please log in again.
            </div>
            <p class="text-muted">
              Don't have an account?
              <router-link to="/signup">
                Sign up
                <img src="../assets/img/arrow-right.svg" alt="Arrow Right Icon" />
              </router-link>
            </p>
            <p class="text-muted">
              Forgot your password?
              <router-link to="/forgotten-password">
                Yes
                <img src="../assets/img/arrow-right.svg" alt="Arrow Right Icon" />
              </router-link>
            </p>
          </form>
        </div>
      </div>
    </div>
  </section>
</template>
