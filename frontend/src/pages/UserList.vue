<template>
  <div>
    <h2>Список пользователей</h2>
    <button @click="showCreateForm = true">Добавить пользователя</button>
    <UserForm v-if="showCreateForm" @userSaved="handleUserSaved" />
    <ul>
      <li v-for="user in users" :key="user.id">
        {{ user.firstName }} {{ user.lastName }} ({{ user.email }})
        <button @click="editUser(user.id)">Редактировать</button>
        <button @click="removeUser(user.id)">Удалить</button>
      </li>
    </ul>
    <UserForm v-if="selectedUserId" :userId="selectedUserId" @userSaved="handleUserSaved" />
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { getUsers, deleteUser } from '../api/userService';
import UserForm from '../components/UserForm.vue';

export default {
  components: { UserForm },
  setup() {
    const users = ref([]);
    const showCreateForm = ref(false);
    const selectedUserId = ref(null);

    const fetchUsers = async () => {
      const response = await getUsers();
      users.value = response.data;
    };

    const removeUser = async (id) => {
      await deleteUser(id);
      users.value = users.value.filter(user => user.id !== id);
    };

    const editUser = (id) => {
      selectedUserId.value = id;
    };

    const handleUserSaved = () => {
      showCreateForm.value = false;
      selectedUserId.value = null;
      fetchUsers();
    };

    onMounted(fetchUsers);

    return { users, showCreateForm, selectedUserId, removeUser, editUser, handleUserSaved };
  }
};
</script>