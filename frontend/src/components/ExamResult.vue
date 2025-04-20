<template>
  <div class="exam-results">
    <h2>Your Test Results</h2>

    <div v-if="loading">Loading...</div>

    <ul v-else>
      <li v-for="result in results" :key="result.id">
        <span>{{ result.test.name }} - {{ result.score }}%</span>
      </li>
    </ul>

    <button v-if="user" @click="sendReminder">Send Test Reminder</button>
    <div v-else>
      <p>Please log in to send reminders.</p>
    </div>

    <div v-if="notification" class="notification">{{ notification }}</div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import store from "@/store/index.js";

const results = ref([]);
const loading = ref(true);
const notification = ref("");

const user = ref(null);

onMounted(async () => {
  try {
    await store.dispatch('fetchUserProfile');
    user.value = store.state.user;

    await store.dispatch('fetchTestResults');
    results.value = store.state.examResults;
  } catch (error) {
    notification.value = 'Error loading data.';
  } finally {
    loading.value = false;
  }
});

const sendReminder = async () => {
  if (user.value) {
    const userId = user.value.id;
    try {
      await store.dispatch('sendTestReminder', userId);
      notification.value = 'Test reminder sent successfully!';
    } catch (error) {
      notification.value = 'Failed to send reminder.';
    }
  } else {
    notification.value = 'User is not authenticated.';
  }
};
</script>

<style scoped>
.exam-results {
  padding: 20px;
}

h2 {
  font-size: 2rem;
  color: #34495e;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  margin-bottom: 10px;
  background-color: #f8f8f8;
  padding: 10px;
  border-radius: 4px;
}

button {
  padding: 10px 20px;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 20px;
}

button:hover {
  background-color: #45a049;
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
