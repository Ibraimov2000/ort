<template>
  <div>
    <h2>Детали экзамена</h2>
    <p><strong>Название:</strong> {{ exam.name }}</p>
    <p><strong>Язык:</strong> {{ exam.language }}</p>
    <p><strong>Тип:</strong> {{ exam.examType }}</p>
    <p><strong>Описание:</strong> {{ exam.description }}</p>
    <button @click="$router.push('/exams')">Назад</button>
  </div>
</template>

<script>
import examService from '@/services/examService';

export default {
  data() {
    return {
      exam: {}
    };
  },
  methods: {
    fetchExam() {
      examService.getById(this.$route.params.id).then(response => {
        this.exam = response.data;
      });
    }
  },
  created() {
    this.fetchExam();
  }
};
</script>