<template>
  <div v-if="timeRemaining > 0">
    <p>Оставшееся время: {{ formatTime(timeRemaining) }}</p>
  </div>
  <div v-else>
    <p>Время истекло! Тест завершен.</p>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue';
import axios from 'axios';

const timeRemaining = ref(0);  // Оставшееся время в секундах
const examEndTime = ref(null);  // Время окончания теста, получаемое с сервера

// Форматирование времени (часы:минуты:секунды)
const formatTime = (seconds) => {
  const hours = Math.floor(seconds / 3600);
  const minutes = Math.floor((seconds % 3600) / 60);
  const sec = seconds % 60;
  return `${hours}:${minutes < 10 ? '0' : ''}${minutes}:${sec < 10 ? '0' : ''}${sec}`;
};

// Запуск таймера с данными от сервера
const startTimer = (endTime) => {
  examEndTime.value = new Date(endTime);
  const interval = setInterval(() => {
    const now = new Date();
    const timeLeft = examEndTime.value - now;

    if (timeLeft > 0) {
      timeRemaining.value = Math.floor(timeLeft / 1000);  // Преобразуем в секунды
    } else {
      timeRemaining.value = 0;
      clearInterval(interval);
      // Здесь можно вызвать завершение теста
    }
  }, 1000);
};

// Пример запроса на сервер для получения времени окончания теста
onMounted(() => {
  axios.get('/api/v1/exams/1/endTime')  // Пример запроса для получения времени окончания
      .then(response => {
        const endTime = response.data.endTime; // Пример: { endTime: "2025-02-15T15:00:00" }
        startTimer(endTime);
      })
      .catch(error => {
        console.error('Ошибка при получении времени окончания теста:', error);
      });
});

onBeforeUnmount(() => {
  // Очистка таймера, если компонент удален
  clearInterval();
});
</script>

<style scoped>
/* Стили для таймера */
</style>
