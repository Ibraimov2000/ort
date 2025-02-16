<template>
  <div class="user-profile">
    <h1>User Profile</h1>
    <div v-if="user">
      <p><strong>Name:</strong> {{ user.firstName }} {{ user.lastName }}</p>
      <p><strong>Email:</strong> {{ user.email }}</p>
      <p><strong>Role:</strong> {{ user.role }}</p>
      <p><strong>Score:</strong> {{ user.score }}%</p>
    </div>
    <div v-else>
      <p>Loading...</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useStore } from 'vuex';

const user = ref(null);
const store = useStore();

onMounted(async () => {
  await store.dispatch('fetchUserProfile');
  user.value = store.state.user;
});
</script>

<style scoped>
.user-profile {
  padding: 20px;
}

h1 {
  font-size: 2rem;
}

p {
  font-size: 1.2rem;
  margin-bottom: 10px;
}
</style>
