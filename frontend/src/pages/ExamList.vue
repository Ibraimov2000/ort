<template>
  <div>
    <h2>Список экзаменов</h2>
    <button @click="$router.push('/exams/new')">Добавить экзамен</button>
    <ul>
      <li v-for="exam in exams" :key="exam.id">
        {{ exam.name }} - {{ exam.language }}
        <button @click="$router.push(`/exams/${exam.id}`)">Детали</button>
        <button @click="$router.push(`/exams/edit/${exam.id}`)">Редактировать</button>
        <button @click="deleteExam(exam.id)">Удалить</button>
      </li>
    </ul>
  </div>
</template>

<script>
import examService from '@/services/examService';

export default {
  data() {
    return {
      exams: []
    };
  },
  methods: {
    fetchExams() {
      examService.getAll().then(response => {
        this.exams = response.data;
      });
    },
    deleteExam(id) {
      examService.delete(id).then(() => {
        this.fetchExams();
      });
    }
  },
  created() {
    this.fetchExams();
  }
};
</script>