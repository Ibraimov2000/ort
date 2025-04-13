<template>
  <div class="leaderboard-chart">
    <h2>Leaderboard Progress</h2>
    <!-- Loading indicator -->
    <div v-if="loading" class="loading">Loading...</div>
    <canvas v-else id="leaderboardChart" width="400" height="200"></canvas>
    <!-- Error message -->
    <div v-if="error" class="error">{{ error }}</div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { Chart } from 'chart.js';
import store from "@/store/index.js";

const chartRef = ref(null);
const leaderboardData = ref([]);
const loading = ref(false);
const error = ref('');


onMounted(async () => {
  loading.value = true;
  error.value = '';
  try {
    await store.dispatch('fetchLeaderboard');
    leaderboardData.value = store.state.leaderboard;

    const ctx = chartRef.value.getContext('2d');
    new Chart(ctx, {
      type: 'bar',
      data: {
        labels: leaderboardData.value.map(user => `${user.user.firstName} ${user.user.lastName}`),
        datasets: [{
          label: 'Top Users',
          data: leaderboardData.value.map(user => user.score),
          backgroundColor: 'rgba(75, 192, 192, 0.2)',
          borderColor: 'rgba(75, 192, 192, 1)',
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  } catch (err) {
    error.value = 'Error loading leaderboard data';
    console.error(err);
  } finally {
    loading.value = false;
  }
});
</script>

<style scoped>
.leaderboard-chart {
  padding: 20px;
  text-align: center;
}

canvas {
  max-width: 100%;
  margin-top: 20px;
}

.loading {
  font-size: 1.2rem;
  color: #3498db;
}

.error {
  color: red;
  font-size: 1.2rem;
}
</style>
