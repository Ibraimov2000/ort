<template>
  <section class="container my-8">
    <el-card shadow="hover">
      <template #header>
        <div class="flex justify-between items-center">
          <h2 class="text-xl font-semibold">Управление пользователями</h2>
          <el-radio-group v-model="filter" @change="handleFilterChange">
            <el-radio-button label="all">Все</el-radio-button>
            <el-radio-button label="locked">Заблокированные</el-radio-button>
            <el-radio-button label="unlocked">Разблокированные</el-radio-button>
          </el-radio-group>
        </div>
      </template>

      <div v-if="errorMessage" class="my-4">
        <el-alert type="error" show-icon :closable="false" :title="'Ошибка'" :description="errorMessage" />
      </div>

      <vue-good-table
          :columns="columns"
          :rows="users"
          :search-options="{ enabled: true }"
          :pagination-options="{ enabled: true }"
          style-class="vgt-table striped"
      >
        <template v-slot:table-row="props">
          <template v-if="props.column.field === 'status'">
            <el-tag :type="props.row.accountNonLocked ? 'success' : 'danger'">
              {{ props.row.accountNonLocked ? 'Активен' : 'Заблокирован' }}
            </el-tag>
          </template>

          <template v-if="props.column.field === 'action'">
            <el-button
                size="small"
                type="danger"
                class="me-2"
                @click="setUserToDelete(props.row.email, props.row.originalIndex)"
            >Удалить</el-button>

            <el-button
                size="small"
                type="primary"
                class="me-2"
                v-if="props.row.accountNonLocked"
                @click="lockUserAccount(props.row.email, props.row.originalIndex)"
            >Заблокировать</el-button>

            <el-button
                size="small"
                type="success"
                v-else
                @click="unlockUserAccount(props.row.email, props.row.originalIndex)"
            >Разблокировать</el-button>
          </template>
        </template>
      </vue-good-table>
    </el-card>

    <el-dialog v-model="isModalVisible" title="Удаление пользователя" width="500px">
      <div class="text-center my-4">
        <h3>Вы уверены, что хотите удалить пользователя?</h3>
      </div>
      <template #footer>
        <el-button @click="closeModal">Отмена</el-button>
        <el-button type="danger" @click="deleteUserAccount(selectedUserEmail, selectedUserIndex)">Удалить</el-button>
      </template>
    </el-dialog>
  </section>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import axiosInstance from "@/api/axiosInstance.js";
import { VueGoodTable } from "vue-good-table-next";
import 'vue-good-table-next/dist/vue-good-table-next.css';

const users = ref([]);
const filter = ref("all");
const columns = [
  { label: 'Имя', field: 'firstName' },
  { label: 'Фамилия', field: 'lastName' },
  { label: 'Логин', field: 'email' },
  { label: 'Статус', field: 'status' },
  { label: 'Действия', field: 'action' }
];

let selectedUserEmail = null;
let selectedUserIndex = null;
let errorMessage = '';
const isModalVisible = ref(false);

onMounted(() => {
  getAllUsers();
});

function handleFilterChange(val) {
  if (val === 'all') getAllUsers();
  if (val === 'locked') getLockedUsers();
  if (val === 'unlocked') getUnlockedUsers();
}

const getAllUsers = async () => {
  errorMessage = '';
  try {
    const response = await axiosInstance.get("admin/users");
    users.value = response.status === 200 ? response.data : [];
  } catch {
    errorMessage = 'Не удалось загрузить пользователей';
    users.value = [];
  }
};

const getUnlockedUsers = async () => {
  errorMessage = '';
  try {
    const response = await axiosInstance.get("admin/unlocked-users");
    users.value = response.status === 200 ? response.data : [];
  } catch {
    errorMessage = 'Не удалось загрузить пользователей';
    users.value = [];
  }
};

const getLockedUsers = async () => {
  errorMessage = '';
  try {
    const response = await axiosInstance.get("admin/locked-users");
    users.value = response.status === 200 ? response.data : [];
  } catch {
    errorMessage = 'Не удалось загрузить пользователей';
    users.value = [];
  }
};

const deleteUserAccount = async (email, index) => {
  errorMessage = '';
  try {
    const response = await axiosInstance.delete(`admin/users/${email}`);
    if (response.status === 200) {
      users.value.splice(index, 1);
      closeModal();
    } else {
      errorMessage = 'Не удалось удалить пользователя';
    }
  } catch (error) {
    errorMessage = 'Не удалось удалить пользователя';
    console.log(error);
  }
};

const setUserToDelete = (email, index) => {
  selectedUserEmail = email;
  selectedUserIndex = index;
  isModalVisible.value = true;
};

const closeModal = () => {
  isModalVisible.value = false;
};

const unlockUserAccount = async (email, index) => {
  errorMessage = '';
  try {
    const response = await axiosInstance.post(`admin/unlock-user/${email}`);
    if (response.status === 200) {
      users.value.splice(index, 1);
    } else {
      errorMessage = 'Не удалось разблокировать пользователя';
    }
  } catch (error) {
    errorMessage = 'Не удалось разблокировать пользователя';
    console.log(error);
  }
};

const lockUserAccount = async (email, index) => {
  errorMessage = '';
  try {
    const response = await axiosInstance.post(`admin/lock-user/${email}`);
    if (response.status === 200) {
      users.value.splice(index, 1);
    } else {
      errorMessage = 'Не удалось заблокировать пользователя';
    }
  } catch (error) {
    errorMessage = 'Не удалось заблокировать пользователя';
    console.log(error);
  }
};
</script>