<template>
  <div class="register">
    <h2>Register</h2>
    <form @submit.prevent="registerUser">
      <div>
        <label for="email">Email:</label>
        <input type="email" v-model="email" required />
      </div>
      <div>
        <label for="password">Password:</label>
        <input type="password" v-model="password" required />
      </div>
      <button type="submit">Register</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

const email = ref('');
const password = ref('');
const store = useStore();
const router = useRouter();

const registerUser = async () => {
  try {
    await store.dispatch('register', { email: email.value, password: password.value });
    router.push('/login');
  } catch (error) {
    console.error("Registration failed:", error);
  }
};
</script>

<style scoped>
.register {
  padding: 20px;
}

h2 {
  font-size: 2rem;
}

form {
  display: flex;
  flex-direction: column;
}

div {
  margin-bottom: 1rem;
}

button {
  background-color: #4CAF50;
  color: white;
  border: none;
  padding: 10px;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}
</style>
