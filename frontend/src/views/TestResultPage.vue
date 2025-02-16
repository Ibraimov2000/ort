<template>
  <div class="exam-results">
    <h2>Your Test Results</h2>
    <ul>
      <li v-for="result in results" :key="result.id">
        <span>{{ result.test.name }} - {{ result.score }}%</span>
      </li>
    </ul>

    <button @click="sendReminder">Send Test Reminder</button>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useStore } from 'vuex';

const results = ref([]);
const store = useStore();

onMounted(async () => {
  await store.dispatch('fetchTestResults');
  results.value = store.state.testResults;
});

const sendReminder = async () => {
  const userId = 1; // Получаем ID текущего пользователя
  await store.dispatch('sendTestReminder', userId);
};
</script>

<style scoped>
.exam-results {
  padding: 20px;
}

h2 {
  font-size: 2rem;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  margin-bottom: 10px;
}

button {
  background-color: #4CAF50;
  color: white;
  border: none;
  padding: 10px;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}
</style>
