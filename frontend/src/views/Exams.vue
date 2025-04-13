<template>
  <div class="tests-page">
    <!-- Показываем индикатор загрузки -->
    <div v-if="loading" class="loading">
      <p>Loading tests...</p>
    </div>

    <!-- Показываем список тестов -->
    <div v-else-if="tests.length > 0" class="tests-grid">
      <ul>
        <li v-for="test in tests" :key="test.id" class="test-item">
          <router-link :to="`/test/${test.id}`" class="test-link">
            <h3>{{ test.name }}</h3>
          </router-link>
          <p><strong>Description:</strong> {{ test.description }}</p>
          <p><strong>Duration:</strong> {{ test.duration }}</p>
        </li>
      </ul>
    </div>

    <!-- Если тестов нет -->
    <div v-else>
      <p>No tests available.</p>
    </div>
  </div>
</template>

<script setup>
import { computed, ref, onMounted } from 'vue';
import store from "@/store/index.js";

const loading = ref(true);
const tests = computed(() => store.state.exams);

onMounted(async () => {
  await store.dispatch('fetchExams');
  loading.value = false;
});
</script>

<style scoped>

.tests-page {
  max-width: 800px;
  margin: 0 auto;
  margin-top: 80px;
  text-align: center;
  padding: 80px 20px 20px;
}

h1 {
  font-size: 2rem;
  color: #2c3e50;
}

.loading {
  font-size: 1.2rem;
  color: #3498db;
}

.tests-grid ul {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  padding: 0;
  list-style-type: none;
}

.test-item {
  padding: 10px;
  background: #f4f6f8;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  text-align: left;
}

.test-link {
  text-decoration: none;
  color: #3498db;
  font-weight: bold;
  transition: color 0.3s;
}

.test-link:hover {
  color: #2980b9;
}
</style>