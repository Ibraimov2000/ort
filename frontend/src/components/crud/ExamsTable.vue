<template>
  <section class="container my-8">
    <el-card shadow="hover">
      <template #header>
        <div class="flex justify-between items-center">
          <h2 class="text-xl font-semibold">Список тестов</h2>
          <el-button type="primary" @click="openCreateModal">Новый тест</el-button>
        </div>
      </template>

      <el-table :data="tests" v-loading="isLoading" style="width: 100%">
        <el-table-column prop="name" label="Название" />
        <el-table-column prop="examType" label="Тип" />
        <el-table-column prop="language" label="Язык" />
        <el-table-column prop="duration" label="Длительность (мин)" />
        <el-table-column label="Действия" width="200">
          <template v-slot="scope">
            <el-button size="small" @click="openEditModal(scope.row)">Редактировать</el-button>
            <el-button size="small" type="danger" @click="openDeleteModal(scope.row.id)">Удалить</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="isEditModal" title="Редактирование теста" width="80%">
      <ExamForm
          v-if="selectedTest"
          :initial-exam="selectedTest"
          mode="edit"
          @saved="handleSaved"
          @cancel="isEditModal = false"
      />
    </el-dialog>

    <el-dialog v-model="isCreateModal" title="Создание теста" width="80%">
      <ExamForm
          :initial-exam="emptyExam"
          mode="create"
          @saved="handleCreated"
          @cancel="isCreateModal = false"
      />
    </el-dialog>

    <el-dialog v-model="isDeleteModal" title="Подтвердите удаление" width="400px">
      <template #footer>
        <el-button @click="isDeleteModal = false">Отмена</el-button>
        <el-button type="danger" @click="confirmDelete">Удалить</el-button>
      </template>
    </el-dialog>
  </section>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import ExamForm from "@/components/crud/ExamForm.vue";
import examService from '@/services/examService.js';
import { ElMessage } from 'element-plus';
import { cloneDeep as deepCopy } from "lodash-es";

const tests = ref([]);
const isLoading = ref(true);
const isDeleteModal = ref(false);
const isEditModal = ref(false);
const isCreateModal = ref(false);
const selectedTestId = ref(null);
const selectedTest = ref(null);

const emptyExam = computed(() => ({
  name: '',
  description: '',
  duration: 180,
  language: 'RU',
  examType: 'MAIN',
  sections: []
}));

async function loadTests() {
  try {
    isLoading.value = true;
    tests.value = await examService.getExams();
  } catch (e) {
    ElMessage.error('Не удалось загрузить тесты');
  } finally {
    isLoading.value = false;
  }
}

function openEditModal(test) {
  selectedTest.value = deepCopy(test);
  isEditModal.value = true;
}

function openCreateModal() {
  isCreateModal.value = true;
}

function openDeleteModal(id) {
  selectedTestId.value = id;
  isDeleteModal.value = true;
}

async function confirmDelete() {
  try {
    await examService.deleteExam(selectedTestId.value);
    tests.value = tests.value.filter(t => t.id !== selectedTestId.value);
    ElMessage.success('Тест удалён');
  } catch {
    ElMessage.error('Ошибка при удалении');
  } finally {
    isDeleteModal.value = false;
  }
}

async function handleSaved() {
  isEditModal.value = false;
  await loadTests();
  ElMessage.success('Изменения сохранены');
}

async function handleCreated() {
  isCreateModal.value = false;
  await loadTests();
  ElMessage.success('Тест создан');
}

onMounted(loadTests);
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: auto;
}
</style>