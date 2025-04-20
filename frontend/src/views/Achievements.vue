<template>
  <div class="achievements">
    <h2>Your Achievements</h2>
    <div class="achievement-list">
      <div class="achievement-item" v-for="achievement in achievements" :key="achievement.id">
        <div class="achievement-content">
          <span class="achievement-name">{{ achievement.name }}</span>
          <p class="achievement-description">{{ achievement.description }}</p>
        </div>
        <span v-if="achievement.unlocked" class="unlocked-text">Unlocked</span>
        <span v-else class="locked-text">Locked</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import store from "@/store/index.js";

const achievements = ref([]);

onMounted(async () => {
  await store.dispatch('fetchAchievements');
  achievements.value = store.state.achievements;
});
</script>

<style scoped>
.achievements {
  padding: 20px;
}

h2 {
  font-size: 2rem;
  margin-bottom: 20px;
  color: #2c3e50;
}

.achievement-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 20px;
}

.achievement-item {
  background-color: #fff;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  padding: 20px;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.achievement-name {
  font-size: 1.5rem;
  font-weight: bold;
  color: #2c3e50;
  margin-bottom: 10px;
}

.achievement-description {
  color: #7f8c8d;
  text-align: center;
}

.unlocked-text {
  color: #4caf50;
  font-style: italic;
  margin-top: 10px;
}

.locked-text {
  color: #f44336;
  font-style: italic;
  margin-top: 10px;
}
</style>
