<template>
  <div class="user-profile">
    <h1>User Profile</h1>
    <div v-if="user">
      <!-- Основная информация о пользователе -->
      <p><strong>Name:</strong> {{ user.firstName }} {{ user.lastName }}</p>
      <p><strong>Email:</strong> {{ user.email }}</p>
      <p><strong>Role:</strong> {{ user.role }}</p>
      <p><strong>Score:</strong> {{ user.score }}%</p>

      <!-- Статистика пользователя -->
      <section class="user-statistics-section">
        <h2>Your Statistics</h2>
        <UserStatistic :statistic="userStatistics" />
      </section>

      <!-- Прогресс -->
      <section class="progress-section">
        <h2>Your Progress</h2>
        <ProgressBar :progress="progress" />
      </section>

      <!-- Достижения -->
      <section class="achievements-section">
        <h2>Your Achievements</h2>
        <ul>
          <li v-for="achievement in achievements" :key="achievement.id">
            <span>{{ achievement.name }}</span>
            <span>{{ achievement.description }}</span>
          </li>
        </ul>
      </section>

      <!-- Награды -->
      <section class="rewards-section">
        <h2>Your Rewards</h2>
        <ul>
          <li v-for="reward in rewards" :key="reward.id">
            <span>{{ reward.name }}</span>
            <span>{{ reward.description }}</span>
          </li>
        </ul>
      </section>

      <button @click="navigateToEditProfile">Edit Profile</button>
      <button @click="logout">Logout</button>
    </div>
    <div v-else>
      <p>Loading...</p>
    </div>

    <!-- Уведомления -->
    <div v-if="notification" class="notification">
      <span>{{ notification }}</span>
      <button @click="closeNotification">Close</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import ProgressBar from '../components/ProgressBar.vue';
import UserStatistic from "@/components/UserStatistic.vue";
import store from "@/store/index.js";
import router from "@/router/index.js";
const user = ref(null);
const userStatistics = ref({});
const progress = ref(0);
const achievements = ref([]);
const rewards = ref([]);
const notification = ref('');

onMounted(async () => {
  await store.dispatch('fetchUserProfile');
  user.value = store.state.user;

  const userStats = store.state.userStatistics;
  if (userStats && userStats.completedTests) {
    progress.value = (userStats.completedTests / 10) * 100; // Примерный расчет прогресса
  }

  // Получаем достижения и награды
  await store.dispatch('fetchAchievements');
  achievements.value = store.state.achievements;

  await store.dispatch('fetchRewards');
  rewards.value = store.state.rewards;
});

// Навигация на страницу редактирования профиля
const navigateToEditProfile = () => {
  router.push('/profile/edit');
};

// Логирование пользователя
const logout = () => {
  store.dispatch('logout');
  router.push('/login');
};

// Закрытие уведомлений
const closeNotification = () => {
  notification.value = '';
};
</script>

<style scoped>
.user-profile {
  padding: 120px 20px 20px;
  font-family: 'Arial', sans-serif;
}

h1 {
  font-size: 2rem;
  color: #34495e;
}

p {
  font-size: 1.2rem;
  margin-bottom: 10px;
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

section {
  margin-top: 20px;
}

h2 {
  margin-bottom: 15px;
  color: #2c3e50;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  margin-bottom: 10px;
  font-size: 1.1rem;
}

.notification {
  position: fixed;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  background-color: #4caf50;
  color: white;
  padding: 10px;
  border-radius: 5px;
  display: flex;
  align-items: center;
}

.notification button {
  margin-left: 10px;
  background-color: transparent;
  border: none;
  color: white;
  cursor: pointer;
}

.notification button:hover {
  opacity: 0.7;
}

@media (min-width: 768px) {
  .user-profile {
    max-width: 900px;
    margin: 0 auto;
  }
}
</style>
