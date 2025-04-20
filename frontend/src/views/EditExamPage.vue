<template>
  <div class="edit-test-page">
    <h1>Edit Test</h1>
    <div v-if="loading">Loading test...</div>
    <div v-else-if="test">
      <EditExamForm :initialTest="test" @saved="handleSaved" />
    </div>
    <div v-else>
      <p class="error">Test not found.</p>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, computed } from 'vue';
import { useRoute} from 'vue-router';
import store from '@/store';
import router from "@/router/index.js";
import EditExamForm from '@/components/crud/ExamForm.vue';

const route = useRoute();
const loading = ref(true);
const test = computed(() => store.state.exam);

onMounted(async () => {
  const testId = route.params.id;
  try {
    await store.dispatch('fetchExamById', testId);
  } catch (e) {
    console.error(e);
  } finally {
    loading.value = false;
  }
});

const handleSaved = () => {
  router.push(`/exams/${test.value.id}`);
};
</script>

<style scoped>
.edit-test-page {
  max-width: 800px;
  margin: 80px auto;
  padding: 20px;
  background: #fff;
  border-radius: 10px;
}
.error {
  color: red;
  font-weight: bold;
  text-align: center;
}
</style>
