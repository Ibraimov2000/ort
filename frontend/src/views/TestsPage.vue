<template>
  <div class="tests-page">
    <h1>Available Tests</h1>
    <div v-if="tests.length > 0">
      <ul>
        <li v-for="test in tests" :key="test.id">
          <router-link :to="`/test/${test.id}`">{{ test.name }}</router-link>
        </li>
      </ul>
    </div>
    <div v-else>
      <p>No tests available.</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useStore } from 'vuex';

const tests = ref([]);
const store = useStore();

onMounted(async () => {
  await store.dispatch('fetchTests');
  tests.value = store.state.tests;
});
</script>

<style scoped>
.tests-page {
  padding: 20px;
}

h1 {
  font-size: 2rem;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  margin-bottom: 10px;
}
</style>
