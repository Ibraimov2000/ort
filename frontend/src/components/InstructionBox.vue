<template>
  <div class="instruction-box">
    <h2>Инструкция перед началом теста</h2>
    <ul class="instruction-list">
      <li>⏱ У вас ограниченное время на прохождение теста.</li>
      <li>📸 Некоторые вопросы могут содержать изображение.</li>
      <li>✅ Выберите один правильный ответ на каждый вопрос.</li>
      <li>📌 После завершения нажмите "Завершить тест".</li>
    </ul>
    <el-button type="primary" class="mt-4" @click="$emit('start')">
      Начать тест
    </el-button>
  </div>
</template>

<script setup>
// emits only "start"
</script>

<style scoped>
.instruction-box {
  background: #ffffff;
  padding: 24px;
  border-radius: 10px;
  margin-bottom: 24px;
  box-shadow: 0 1px 6px rgba(0, 0, 0, 0.1);
  text-align: left;
}

.instruction-list {
  margin-top: 12px;
  list-style: none;
  padding-left: 0;
}

.instruction-list li {
  margin-bottom: 8px;
  font-size: 1rem;
  line-height: 1.4;
  color: #444;
}
</style>
