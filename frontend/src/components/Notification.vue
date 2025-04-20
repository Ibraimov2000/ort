<template>
  <div v-if="message" :class="['notification', notificationType]">
    <span>{{ message }}</span>
    <button @click="closeNotification">Close</button>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const message = ref("");
const notificationType = ref('success'); // Можно выбрать "success", "error" или другие типы сообщений

const showNotification = (msg, type = 'success') => {
  message.value = msg;
  notificationType.value = type;  // Применяем тип уведомления (например, "success" или "error")
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
  padding: 10px;
  border-radius: 5px;
  color: white;
  display: flex;
  align-items: center;
}

.success {
  background-color: #4caf50;
}

.error {
  background-color: #f44336;
}

button {
  margin-left: 10px;
  background-color: transparent;
  border: none;
  color: white;
  cursor: pointer;
}

button:hover {
  opacity: 0.7;
}
</style>
