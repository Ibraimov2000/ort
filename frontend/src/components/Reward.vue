<template>
  <div class="reward">
    <h2>Your Rewards</h2>
    <div class="reward-list">
      <div class="reward-item" v-for="reward in rewards" :key="reward.id">
        <div class="reward-content">
          <span class="reward-name">{{ reward.name }}</span>
          <p class="reward-description">{{ reward.description }}</p>
        </div>
        <button v-if="!reward.received" class="reward-button" @click="claimReward(reward)">
          Claim Reward
        </button>
        <span v-else class="claimed-text">Claimed</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import store from "@/store/index.js";

const rewards = ref([]);

onMounted(async () => {
  await store.dispatch('fetchRewards');
  rewards.value = store.state.rewards;
});

const claimReward = (reward) => {
  store.dispatch('claimReward', reward.id);  // Assume you have an action to handle claiming rewards
};
</script>

<style scoped>
.reward {
  padding: 20px;
}

h2 {
  font-size: 2rem;
  margin-bottom: 20px;
  color: #333;
}

.reward-list {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}

.reward-item {
  background-color: #fff;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  padding: 20px;
  border-radius: 8px;
  width: 250px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.reward-name {
  font-size: 1.5rem;
  font-weight: bold;
  color: #2c3e50;
}

.reward-description {
  color: #7f8c8d;
  text-align: center;
}

.reward-button {
  background-color: #4caf50;
  color: white;
  border: none;
  padding: 10px;
  margin-top: 10px;
  border-radius: 5px;
  cursor: pointer;
}

.reward-button:hover {
  background-color: #45a049;
}

.claimed-text {
  color: #7f8c8d;
  font-style: italic;
  margin-top: 10px;
}
</style>
