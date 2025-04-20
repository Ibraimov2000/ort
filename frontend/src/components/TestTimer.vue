<template>
  <div class="timer">
    <el-icon><Clock /></el-icon>
    <span>{{ formattedTime }}</span>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue';
import { Clock } from '@element-plus/icons-vue';

const props = defineProps({
  duration: {
    type: Number, // в минутах
    required: true
  }
});

const timeLeft = ref(props.duration * 60); // в секундах
let interval = null;

const formattedTime = computed(() => {
  const minutes = Math.floor(timeLeft.value / 60);
  const seconds = timeLeft.value % 60;
  return `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
});

onMounted(() => {
  interval = setInterval(() => {
    if (timeLeft.value > 0) {
      timeLeft.value--;
    } else {
      clearInterval(interval);
      // emit('timeout') — при желании можно обработать
    }
  }, 1000);
});

onUnmounted(() => {
  clearInterval(interval);
});
</script>

<style scoped>
.timer {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 1.1rem;
  color: #333;
  font-weight: 500;
  background: #f2f6fc;
  padding: 6px 12px;
  border-radius: 8px;
  margin-bottom: 20px;
}
</style>
