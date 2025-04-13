<template>
  <div class="test-page">
    <div v-if="loading" class="loading">Loading...</div>

    <div v-if="!loading && test">
      <header class="test-header">
        <h1>{{ test.name }}</h1>
        <p class="test-description">{{ test.description }}</p>
      </header>

      <form @submit.prevent="submitTest" class="test-form">
        <div v-for="section in test.sections" :key="section.id" class="section">
          <h2 class="section-title">{{ section.name }}</h2>
          <div v-for="question in section.questions" :key="question.id" class="question">
            <p class="question-text">{{ question.text }}</p>
            <div class="answers">
              <label v-for="answer in question.answers" :key="answer.id" class="answer-option">
                <input
                    type="radio"
                    :name="`question-${question.id}`"
                    :value="answer.id"
                    v-model="answers[question.id]"
                />
                {{ answer.text }}
              </label>
            </div>
          </div>
        </div>
        <button type="submit" class="submit-btn">Submit Test</button>
      </form>
    </div>

    <div v-if="!loading && (!test || !test.sections || test.sections.length === 0)" class="error">
      <p>Test not found or has no sections.</p>
    </div>

    <div v-if="notification" class="notification">{{ notification }}</div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import store from "@/store/index.js";
import { useRoute } from "vue-router";

const route = useRoute();
const loading = ref(true);
const notification = ref("");
const answers = ref({});
const test = computed(() => store.state.exam);

onMounted(async () => {
  const testId = route.params.id;
  try {
    await store.dispatch('fetchExamById', testId);
  } catch (error) {
    notification.value = 'Failed to load test';
  } finally {
    loading.value = false;
  }
});

const submitTest = async () => {
  if (Object.keys(answers.value).length === 0) {
    notification.value = "Please select at least one answer.";
    return;
  }

  const selectedAnswers = Object.entries(answers.value).map(([questionId, answerId]) => ({
    questionId: Number(questionId),
    answerId,
  }));

  try {
    await store.dispatch('submitExamAnswers', selectedAnswers);
    notification.value = 'Test submitted successfully!';
  } catch (error) {
    notification.value = 'Error submitting test. Please try again.';
  }
};
</script>

<style scoped>
.test-page {
  max-width: 900px;
  margin: 0 auto;
  margin-top: 80px;
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.test-header {
  text-align: center;
  margin-bottom: 20px;
}

h1 {
  font-size: 2.5rem;
  color: #333;
}

.test-description {
  font-size: 1.2rem;
  color: #555;
}

.loading {
  text-align: center;
  font-size: 1.5rem;
  font-weight: bold;
  color: #007bff;
}

.section {
  background: #ffffff;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 15px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.section-title {
  font-size: 1.5rem;
  color: #444;
  margin-bottom: 10px;
}

.question {
  margin-bottom: 15px;
}

.question-text {
  font-size: 1.2rem;
  font-weight: bold;
}

.answers {
  display: grid;
  grid-template-columns: repeat(2, 1fr); /* Две колонки */
  gap: 15px;
}

.answer-option {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 1rem;
  cursor: pointer;
}


.answers {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.answer-option {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 1rem;
  cursor: pointer;
}

.submit-btn {
  width: 100%;
  padding: 12px;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 1.2rem;
  cursor: pointer;
  margin-top: 20px;
}

.submit-btn:hover {
  background-color: #218838;
}

.notification {
  margin-top: 20px;
  padding: 10px;
  background-color: #ff9800;
  color: white;
  border-radius: 5px;
  text-align: center;
}

.error {
  color: red;
  font-size: 1.2rem;
  text-align: center;
  margin-top: 20px;
}
</style>