<template>
  <div class="leaderboard">
    <h2>Top Users</h2>
    <ul>
      <li v-for="(leader, index) in leaderboard" :key="index">
        {{ index + 1 }}. {{ leader.user.firstName }} {{ leader.user.lastName }} - {{ leader.score }} points
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useStore } from 'vuex';

const leaderboard = ref([]);
const store = useStore();

onMounted(async () => {
  await store.dispatch('fetchLeaderboard');
  leaderboard.value = store.state.leaderboard;
});
</script>

<style scoped>
.leaderboard {
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
  background-color: #f8f8f8;
  padding: 10px;
  border-radius: 4px;
}
</style>
