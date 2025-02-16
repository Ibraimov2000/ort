<template>
  <div class="reward">
    <h2>Your Rewards</h2>
    <ul>
      <li v-for="reward in rewards" :key="reward.id">
        <span>{{ reward.name }}</span>
        <span>{{ reward.description }}</span>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useStore } from 'vuex';

const rewards = ref([]);
const store = useStore();

onMounted(async () => {
  await store.dispatch('fetchRewards');
  rewards.value = store.state.rewards;
});
</script>

<style scoped>
.reward {
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
