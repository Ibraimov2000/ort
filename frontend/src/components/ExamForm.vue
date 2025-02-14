<template>
  <div>
    <h2>{{ isEdit ? 'Редактировать экзамен' : 'Создать экзамен' }}</h2>
    <form @submit.prevent="saveExam">
      <label>Название: <input v-model="exam.name" required /></label>
      <label>Язык:
        <select v-model="exam.language">
          <option value="RU">Русский</option>
          <option value="KG">Кыргызча</option>
        </select>
      </label>
      <label>Тип:
        <select v-model="exam.examType">
          <option value="MAIN">Основной</option>
          <option value="ADDITIONAL">Дополнительный</option>
        </select>
      </label>
      <label>Описание: <textarea v-model="exam.description"></textarea></label>
      <button type="submit">Сохранить</button>
    </form>
  </div>
</template>

<script>
import examService from '@/services/examService';

export default {
  data() {
    return {
      exam: { name: '', language: 'RU', examType: 'MAIN', description: '' }
    };
  },
  computed: {
    isEdit() {
      return !!this.$route.params.id;
    }
  },
  methods: {
    saveExam() {
      if (this.isEdit) {
        examService.update(this.$route.params.id, this.exam).then(() => {
          this.$router.push('/exams');
        });
      } else {
        examService.create(this.exam).then(() => {
          this.$router.push('/exams');
        });
      }
    },
    fetchExam() {
      if (this.isEdit) {
        examService.getById(this.$route.params.id).then(response => {
          this.exam = response.data;
        });
      }
    }
  },
  created() {
    this.fetchExam();
  }
};
</script>