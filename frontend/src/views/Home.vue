<template>
  <div>
    <h2>Welcome to the Online Exam Platform</h2>
    <TestList />
    <Leaderboard />
    <ProgressBar :progress="progress" />
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useStore } from 'vuex';
import TestList from '../components/TestList.vue';
import Leaderboard from '../components/Leaderboard.vue';
import ProgressBar from '../components/ProgressBar.vue';

export default {
  name: 'HomePage',
  components: {
    TestList,
    Leaderboard,
    ProgressBar,
  },
  setup() {
    const store = useStore();
    const progress = ref(50);

    onMounted(async () => {
      await store.dispatch('fetchTests');
      await store.dispatch('fetchTopUsers');
      await store.dispatch('fetchUserProfile');
    });

    return { progress };
  },
};
</script>

<style scoped>
h1 {
  text-align: center;
}
</style>
