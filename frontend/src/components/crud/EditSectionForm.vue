<template>
  <el-card class="mb-4 relative">
    <el-button
        type="danger"
        circle
        size="small"
        class="absolute top-2 right-2"
        @click="$emit('remove')"
    >
      <el-icon><Delete /></el-icon>
    </el-button>

    <el-form-item label="Название раздела">
      <el-input v-model="section.name" placeholder="Введите название" />
    </el-form-item>

    <EditQuestionForm
        v-for="(q, idx) in section.questions"
        :key="q.id || idx"
        :question="q"
        @remove="section.questions.splice(idx, 1)"
    />

    <el-button class="mt-2" @click="addQuestion" :disabled="section.questions.length >= section.questionsCount">
      Добавить вопрос
    </el-button>
    <p v-if="section.questions.length >= section.questionsCount" class="text-xs text-gray-500 mt-1">
      Достигнуто максимальное количество вопросов для этого раздела
    </p>
  </el-card>
</template>

<script setup>
import EditQuestionForm from "@/components/crud/EditQuestionForm.vue";
import { Delete } from '@element-plus/icons-vue';

const props = defineProps({ section: Object });

function addQuestion() {
  if (props.section.questions.length < props.section.questionsCount) {
    props.section.questions.push({
      id: null,
      text: '',
      score: 0,
      questionType: 'TEXT',
      answers: []
    });
  }
}
</script>

<style scoped>
.relative {
  position: relative;
}
.absolute {
  position: absolute;
}
.top-2 {
  top: 0.5rem;
}
.right-2 {
  right: 0.5rem;
}
</style>
