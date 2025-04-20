<template>
  <div class="progress-bar">
    <div class="progress" :style="{ width: progress + '%' }"></div>
  </div>
</template>

<script>
export default {
  name: 'ProgressBar',
  props: {
    progress: {
      type: Number,
      required: true,
    },
  },
};
</script>

<style scoped>
.progress-bar {
  width: 100%;
  background-color: #f3f3f3;
  border-radius: 5px;
  height: 25px;
}

.progress {
  height: 100%;
  background-color: #4caf50;
  border-radius: 5px;
}
</style>
