<template>
  <div class="achievements">
    <h2>Your Achievements</h2>
    <ul>
      <li v-for="achievement in achievements" :key="achievement.id">
        <span>{{ achievement.name }}</span>
        <span>{{ achievement.description }}</span>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useStore } from 'vuex';

const achievements = ref([]);
const store = useStore();

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
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  margin-bottom: 10px;
}
</style>
