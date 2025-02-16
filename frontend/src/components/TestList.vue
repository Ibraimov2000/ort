<template>
  <div>
    <h2>Available Tests</h2>
    <ul>
      <li v-for="test in tests" :key="test.id">
        <h3>{{ test.name }}</h3>
        <p>{{ test.description }}</p>
      </li>
    </ul>
  </div>
</template>

<script>
import { computed } from 'vue';
import { useStore } from 'vuex';

export default {
  name: 'TestList',
  setup() {
    const store = useStore();
    const tests = computed(() => store.state.tests);

    return { tests };
  },
};
</script>

<style scoped>
ul {
  padding: 0;
}

li {
  list-style-type: none;
  margin-bottom: 10px;
}
</style>
