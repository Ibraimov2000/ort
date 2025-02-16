<template>
  <div class="statistics-chart">
    <h2>Test Progress Chart</h2>
    <canvas id="progressChart" width="400" height="200"></canvas>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useStore } from 'vuex';
import { Chart } from 'chart.js';

const chartRef = ref(null);

onMounted(() => {
  const ctx = chartRef.value.getContext('2d');
  new Chart(ctx, {
    type: 'bar',
    data: {
      labels: ['Test 1', 'Test 2', 'Test 3'], // Пример
      datasets: [{
        label: 'Progress',
        data: [85, 70, 95], // Пример данных
        backgroundColor: 'rgba(75, 192, 192, 0.2)',
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: 1
      }]
    },
    options: {
      scales: {
        y: {
          beginAtZero: true
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
