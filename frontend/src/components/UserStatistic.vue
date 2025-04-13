<template>
  <div class="user-statistics">
    <h2>User Statistics</h2>

    <!-- Loading state -->
    <div v-if="loading">Loading...</div>

    <!-- User statistics -->
    <div v-else>
      <div v-if="user">
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

      <!-- If user is not authenticated -->
      <div v-else>
        <p>Please log in to see your statistics.</p>
      </div>
    </div>

    <!-- Notification -->
    <div v-if="notification" class="notification">{{ notification }}</div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import store from "@/store/index.js";

const completedTests = ref(0);
const averageScore = ref(0);
const totalPoints = ref(0);
const user = ref(null);
const loading = ref(true);
const notification = ref("");


onMounted(async () => {
  try {
    await store.dispatch('fetchUserProfile');
    user.value = store.state.user;

    if (user.value) {
      const stats = store.state.userStatistics;
      completedTests.value = stats.completedTests;
      averageScore.value = stats.averageScore;
      totalPoints.value = stats.totalPoints;
    }
  } catch (error) {
    notification.value = "Error loading user statistics.";
  } finally {
    loading.value = false;
  }
});
</script>

<style scoped>
.user-statistics {
  padding: 20px;
}

h2 {
  font-size: 2rem;
  color: #34495e;
}

.stat {
  display: flex;
  justify-content: space-between;
  margin: 10px 0;
}

.stat span {
  font-size: 1.1rem;
  color: #2c3e50;
}

button {
  padding: 10px 20px;
  margin-top: 20px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #2980b9;
}

.notification {
  margin-top: 20px;
  padding: 10px;
  background-color: #f39c12;
  color: white;
  border-radius: 5px;
}

.loading {
  font-size: 1.5rem;
  color: #34495e;
  font-weight: bold;
}
</style>
