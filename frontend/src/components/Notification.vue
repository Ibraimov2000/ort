<template>
  <div v-if="message" class="notification">
    <span>{{ message }}</span>
    <button @click="closeNotification">Close</button>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const message = ref("");

const showNotification = (msg) => {
  message.value = msg;
  setTimeout(() => {
    message.value = "";
  }, 5000);
};

const closeNotification = () => {
  message.value = "";
};

export { showNotification };
</script>

<style scoped>
.notification {
  position: fixed;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  background-color: #4caf50;
  color: white;
  padding: 10px;
  border-radius: 5px;
}

button {
  margin-left: 10px;
  background-color: transparent;
  border: none;
  color: white;
  cursor: pointer;
}
</style>
