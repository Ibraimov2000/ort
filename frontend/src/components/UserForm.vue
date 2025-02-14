<template>
  <form @submit.prevent="submitForm">
    <label>Имя:</label>
    <input v-model="user.firstName" required />

    <label>Фамилия:</label>
    <input v-model="user.lastName" required />

    <label>Email:</label>
    <input v-model="user.email" type="email" required />

    <label>Пароль:</label>
    <input v-model="user.password" type="password" required />

    <label>Роль:</label>
    <input v-model="user.role" required />

    <button type="submit">{{ user.id ? 'Обновить' : 'Создать' }}</button>
  </form>
</template>

<script>
import { ref, onMounted } from 'vue';
import User from '../models/User';
import { getUser, createUser, updateUser } from '../api/userService';

export default {
  props: ['userId'],
  setup(props, { emit }) {
    const user = ref(new User());

    onMounted(async () => {
      if (props.userId) {
        const response = await getUser(props.userId);
        user.value = response.data;
      }
    });

    const submitForm = async () => {
      if (user.value.id) {
        await updateUser(user.value.id, user.value);
      } else {
        await createUser(user.value);
      }
      emit('userSaved');
    };

    return { user, submitForm };
  }
};
</script>