<template>
  <div class="notifications">
    <h2>Notifications</h2>
    <ul>
      <li v-for="(notification, index) in notifications" :key="index">
        {{ notification }}
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useStore } from 'vuex';

const notifications = ref([]);
const store = useStore();

onMounted(() => {
  notifications.value = store.state.notifications;
});
</script>

<style scoped>
.notifications {
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
