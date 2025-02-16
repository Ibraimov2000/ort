<template>
  <div class="test-page">
    <h1>{{ test.name }}</h1>
    <p>{{ test.description }}</p>

    <div v-if="test.questions">
      <form @submit.prevent="submitTest">
        <ul>
          <li v-for="question in test.questions" :key="question.id">
            <h3>{{ question.text }}</h3>
            <ul>
              <li v-for="answer in question.answers" :key="answer.id">
                <input
                    type="radio"
                    :name="`question-${question.id}`"
                    :value="answer.id"
                    v-model="answers[question.id]"
                />
                {{ answer.text }}
              </li>
            </ul>
          </li>
        </ul>
        <button type="submit">Submit Test</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useStore } from 'vuex';

const test = ref({});
const answers = ref({}); // Объект для хранения выбранных ответов
const store = useStore();
const route = useRoute();

onMounted(async () => {
  const testId = route.params.id;
  await store.dispatch('fetchTestById', testId);
  test.value = store.state.test;
});

const submitTest = () => {
  const selectedAnswers = Object.entries(answers.value).map(([questionId, answerId]) => ({
    questionId,
    answerId,
  }));

  // Отправка выбранных ответов на сервер
  store.dispatch('submitTestAnswers', selectedAnswers);
};
</script>

<style scoped>
.test-page {
  padding: 20px;
}

h1 {
  font-size: 2rem;
}
</style>
