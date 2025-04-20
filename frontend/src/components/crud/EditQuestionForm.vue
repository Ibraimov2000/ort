<template>
  <div class="border-l-2 pl-4 my-4">
    <el-form :inline="true" class="items-center mb-2">
      <el-form-item label="Номер">
        <el-input v-model="question.number" placeholder="Номер вопроса" />
      </el-form-item>
      <el-form-item label="Вопрос">
        <el-input v-model="question.text" placeholder="Текст вопроса" />
      </el-form-item>
      <el-form-item label="Тип вопроса">
        <el-select v-model="question.questionType" placeholder="Выберите тип">
          <el-option label="Фото" value="PHOTO" />
          <el-option label="Текст" value="TEXT" />
        </el-select>
      </el-form-item>

      <el-form-item label="Баллы за вопрос">
        <el-input-number v-model.number="question.score" :min="0" class="w-28" />
      </el-form-item>

      <el-form-item>
        <el-button
            type="danger"
            circle
            size="small"
            @click="$emit('remove')"
        >
          <el-icon><Delete /></el-icon>
        </el-button>
      </el-form-item>
    </el-form>

    <!-- upload for photo question -->
    <el-form-item v-if="question.questionType === 'PHOTO'" label="Изображение">
      <el-upload
          :show-file-list="false"
          :before-upload="handleUpload"
      >
        <el-button>Загрузить изображение</el-button>
      </el-upload>

      <div v-if="question.imageDataPreview" class="mt-2">
        <img :src="question.imageDataPreview" alt="preview" class="max-w-xs rounded border" />
      </div>
    </el-form-item>

    <EditAnswerForm
        v-for="(a, idx) in question.answers"
        :key="a.id || idx"
        :answer="a"
        @set-correct="() => setCorrectAnswer(idx)"
        @remove="question.answers.splice(idx, 1)"
    />

    <div v-if="!hasCorrectAnswer" class="text-red-500 text-sm mt-1">
      ⚠️ Не выбран правильный ответ
    </div>

    <el-button
        class="mt-2"
        @click="tryAddAnswer"
    >
      Добавить ответ
    </el-button>
  </div>
</template>

<script setup>
import { computed } from 'vue';
import EditAnswerForm from "@/components/crud/EditAnswerForm.vue";
import { Delete } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';

const props = defineProps({ question: Object });

function addAnswer() {
  props.question.answers.push({ id: null, text: '', isCorrect: false });
}

function tryAddAnswer() {
  if (!hasCorrectAnswer.value && props.question.answers.length > 0) {
    ElMessage.warning('Перед добавлением нового ответа выберите правильный для текущих');
    return;
  }
  addAnswer();
}

function setCorrectAnswer(index) {
  props.question.answers.forEach((a, i) => {
    a.isCorrect = i === index;
  });
}

const hasCorrectAnswer = computed(() => {
  return props.question.answers.some(a => a.isCorrect);
});

function handleUpload(file) {
  const reader = new FileReader();
  reader.onload = (e) => {
    const base64 = e.target.result;
    props.question.imageDataPreview = base64;

    const byteString = atob(base64.split(',')[1]);
    const byteArray = new Uint8Array(byteString.length);
    for (let i = 0; i < byteString.length; i++) {
      byteArray[i] = byteString.charCodeAt(i);
    }
    props.question.imageData = Array.from(byteArray);
  };
  reader.readAsDataURL(file);
  return false;
}
</script>
