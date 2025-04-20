<template>
  <div class="flex items-center gap-3 pl-6 my-2 border rounded px-3 py-2">
    <el-input v-model="answer.text" placeholder="Ответ" class="flex-1" />
    <el-radio
        :model-value="answer.isCorrect"
        :label="true"
        @change="$emit('set-correct')"
    >
      <el-icon class="text-green-600"><Check /></el-icon>
    </el-radio>
    <el-button
        type="danger"
        circle
        size="small"
        @click="$emit('remove')"
    >
      <el-icon><Delete /></el-icon>
    </el-button>
  </div>
</template>

<script setup>
import { Delete, Check } from '@element-plus/icons-vue';

const props = defineProps({
  answer: Object
});
</script>
