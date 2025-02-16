<template>
  <div class="leaderboard-chart">
    <h2>Leaderboard Progress</h2>
    <canvas id="leaderboardChart" width="400" height="200"></canvas>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useStore } from 'vuex';
import { Chart } from 'chart.js';

const chartRef = ref(null);
const leaderboardData = ref([]);

onMounted(async () => {
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
.leaderboard-chart {
  padding: 20px;
}

canvas {
  max-width: 100%;
  margin-top: 20px;
}
</style>
