<template>
  <div class="statistics-chart">
    <h2>Test Progress Chart</h2>
    <canvas ref="chart" width="400" height="200"></canvas>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import store from "@/store/index.js";
import { Chart } from 'chart.js';

const chartRef = ref(null);

onMounted(async () => {
  // Получаем данные о тестах и прогрессе пользователя
  await store.dispatch('fetchTests');
  const tests = store.state.exams;

  // Извлекаем данные для графика (например, прогресс по каждому тесту)
  const labels = tests.map(test => test.name);
  const data = tests.map(test => test.progress); // Прогресс пользователя по каждому тесту

  const ctx = chartRef.value.getContext('2d');

  // Инициализируем график
  new Chart(ctx, {
    type: 'bar',
    data: {
      labels, // Имена тестов
      datasets: [{
        label: 'Progress',
        data, // Прогресс по каждому тесту
        backgroundColor: 'rgba(75, 192, 192, 0.2)',
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: 1
      }]
    },
    options: {
      scales: {
        y: {
          beginAtZero: true,
          max: 100, // Максимальное значение прогресса
        }
      }
    }
  });
});
</script>

<style scoped>
.statistics-chart {
  padding: 20px;
}

h2 {
  font-size: 2rem;
}

canvas {
  max-width: 100%;
  margin-top: 20px;
}
</style>
