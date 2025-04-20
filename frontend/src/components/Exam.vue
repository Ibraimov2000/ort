<template>
  <div class="test-page">
    <InstructionBox v-if="!started && !loading && test" @start="started = true" />
    <div v-if="loading" class="loading">Загрузка теста...</div>

    <div v-else-if="test && started">
      <header class="test-header">
        <h1>{{ test.name }}</h1>
        <p class="test-description">{{ test.description }}</p>
        <TestTimer :duration="durationInMinutes" @expired="handleTimeExpired" />
      </header>

      <form @submit.prevent="submitTest" class="test-form">
        <div v-for="section in test.sections" :key="section.id" class="section">
          <h2 class="section-title">{{ section.name }}</h2>

          <div
              v-for="(question, index) in section.questions"
              :key="question.id"
              class="question-card"
          >
            <div class="question-header">
              <p class="question-number">№{{ index + 1 }}.</p>
              <p class="question-text">{{ question.text }}</p>
              <img
                  v-if="question.imageData"
                  :src="'data:image/png;base64,' + question.imageData"
                  class="question-image"
                  alt="Вопрос изображение"
              />
            </div>

            <div class="answers">
              <label
                  v-for="answer in question.answers"
                  :key="answer.id"
                  class="answer-option"
              >
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

        <el-progress
            :percentage="progressPercentage"
            status="success"
            :show-text="true"
            class="mb-4"
        />

        <button type="submit" class="submit-btn">Завершить тест</button>
      </form>
    </div>

    <div v-else-if="!loading && (!test || !test.sections || test.sections.length === 0)" class="error">
      <p>Тест не найден или не содержит секций.</p>
    </div>
    <div v-if="notification" class="notification">{{ notification }}</div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import store from "@/store/index.js";
import { useRoute } from "vue-router";
import TestTimer from '@/components/TestTimer.vue';
import InstructionBox from "@/components/InstructionBox.vue";

const route = useRoute();
const loading = ref(true);
const notification = ref("");
const answers = ref({});
const started = ref(false);
const test = computed(() => store.state.exam);

const durationInMinutes = computed(() => {
  const duration = test.value?.duration;
  if (typeof duration === 'string') {
    const match = duration.match(/PT(?:(\d+)H)?(?:(\d+)M)?/);
    const hours = match?.[1] ? parseInt(match[1]) : 0;
    const minutes = match?.[2] ? parseInt(match[2]) : 0;
    return hours * 60 + minutes;
  }
  return typeof duration === 'number' ? duration : 0;
});

onMounted(async () => {
  const testId = route.params.id;
  try {
    await store.dispatch('fetchExamById', testId);
  } catch (error) {
    notification.value = 'Ошибка при загрузке теста.';
  } finally {
    loading.value = false;
  }
});

const submitTest = async () => {
  if (Object.keys(answers.value).length === 0) {
    notification.value = "Выберите хотя бы один ответ.";
    return;
  }

  const selectedAnswers = Object.entries(answers.value).map(([questionId, answerId]) => ({
    questionId: Number(questionId),
    answerId,
  }));

  try {
    await store.dispatch('submitExamAnswers', selectedAnswers);
    notification.value = 'Тест успешно отправлен!';
  } catch (error) {
    notification.value = 'Ошибка при отправке теста.';
  }
};

const handleTimeExpired = () => {
  submitTest();
};

const totalQuestions = computed(() => test.value?.sections?.flatMap(s => s.questions).length || 0);
const answeredCount = computed(() => Object.keys(answers.value).length);
const progressPercentage = computed(() => totalQuestions.value > 0
    ? Math.round((answeredCount.value / totalQuestions.value) * 100)
    : 0);
</script>


<style scoped>
.test-page {
  max-width: 900px;
  margin: 0 auto;
  margin-top: 80px;
  padding: 20px;
  background: #f9f9f9;
  border-radius: 12px;
}

.test-header {
  text-align: center;
  margin-bottom: 24px;
  position: sticky;
  top: 0;
  background: #f9f9f9;
  padding: 16px 0;
  z-index: 10;
  box-shadow: 0 2px 4px rgba(0,0,0,0.05);
}

h1 {
  font-size: 2.5rem;
  color: #333;
}

.test-description {
  font-size: 1.2rem;
  color: #666;
}

.section {
  margin-bottom: 30px;
  border-left: 5px solid #409eff;
  padding-left: 16px;
}

.section-title {
  font-size: 1.4rem;
  margin-bottom: 14px;
  color: #222;
}

.question-card {
  background: #fff;
  padding: 16px;
  border-radius: 10px;
  margin-bottom: 16px;
  box-shadow: 0 1px 4px rgba(0,0,0,0.1);
}

.question-header {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.question-number {
  font-size: 1.2rem;
  font-weight: 600;
  color: #333;
}

.question-text {
  font-size: 1rem;
  color: #444;
}

.question-image-wrapper {
  margin-top: 10px;
}

.question-image {
  max-width: 300px;
  border-radius: 8px;
  border: 1px solid #ddd;
}

.image-caption {
  font-size: 0.85rem;
  color: #777;
  margin-top: 4px;
}

.answers {
  display: flex;
  flex-direction: column;
  gap: 8px;
  margin-top: 12px;
}

.answer-option {
  font-size: 1rem;
  display: flex;
  align-items: center;
  gap: 8px;
}

.submit-btn {
  width: 100%;
  padding: 14px;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 1.2rem;
  cursor: pointer;
  margin-top: 30px;
  transition: background 0.3s ease;
}

.submit-btn:hover {
  background-color: #218838;
}

.notification {
  margin-top: 20px;
  padding: 12px;
  background-color: #ff9800;
  color: white;
  border-radius: 5px;
  text-align: center;
}

.loading,
.error {
  text-align: center;
  font-size: 1.3rem;
  margin-top: 60px;
  color: #409eff;
}
</style>