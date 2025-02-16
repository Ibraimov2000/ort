<template>
  <div class="user-statistics">
    <h2>User Statistics</h2>
    <div class="stat">
      <span>Completed Tests:</span>
      <span>{{ completedTests }}</span>
    </div>
    <div class="stat">
      <span>Average Score:</span>
      <span>{{ averageScore }}%</span>
    </div>
    <div class="stat">
      <span>Total Points:</span>
      <span>{{ totalPoints }}</span>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useStore } from 'vuex';

const completedTests = ref(0);
const averageScore = ref(0);
const totalPoints = ref(0);

const store = useStore();

onMounted(async () => {
  // Получаем статистику для пользователя из Vuex
  completedTests.value = store.state.userStatistics.completedTests;
  averageScore.value = store.state.userStatistics.averageScore;
  totalPoints.value = store.state.userStatistics.totalPoints;
});
</script>

<style scoped>
.user-statistics {
  padding: 20px;
}

h2 {
  font-size: 2rem;
}

.stat {
  display: flex;
  justify-content: space-between;
  margin: 10px 0;
}

.stat span {
  font-size: 1.1rem;
}
</style>
