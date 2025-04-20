<template>
  <section class="container my-8">
    <el-card shadow="hover">
      <template #header>
        <div class="flex justify-between items-center">
          <h2 class="text-xl font-semibold">Список разделов</h2>
          <el-button type="primary" @click="openCreateModal">Новый раздел</el-button>
        </div>
      </template>

      <el-table :data="sections" v-loading="isLoading" style="width: 100%">
        <el-table-column prop="name" label="Название" />
        <el-table-column prop="questionsCount" label="Вопросов" width="100" />
        <el-table-column label="Действия" width="200">
          <template v-slot="scope">
            <el-button size="small" @click="openEditModal(scope.row)">Редактировать</el-button>
            <el-button size="small" type="danger" @click="openDeleteModal(scope.row.id)">Удалить</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="isEditModal" title="Редактирование раздела" width="60%">
      <SectionForm
          mode="edit"
          :initial-section="selectedSection"
          @saved="handleSaved"
          @cancel="isEditModal = false"
      />
    </el-dialog>

    <el-dialog v-model="isCreateModal" title="Создание раздела" width="60%">
      <SectionForm
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
import { ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import sectionService from '@/services/sectionService';
import SectionForm from '@/components/crud/SectionForm.vue';
import { cloneDeep } from 'lodash-es';

const sections = ref([]);
const isLoading = ref(true);
const isEditModal = ref(false);
const isDeleteModal = ref(false);
const isCreateModal = ref(false);

const selectedSection = ref(null);
const selectedSectionId = ref(null);

async function loadSections() {
  try {
    isLoading.value = true;
    sections.value = await sectionService.getAll();
  } catch (e) {
    ElMessage.error('Не удалось загрузить разделы');
  } finally {
    isLoading.value = false;
  }
}

function openEditModal(section) {
  selectedSection.value = cloneDeep(section);
  isEditModal.value = true;
}

function openCreateModal() {
  isCreateModal.value = true;
}

function openDeleteModal(id) {
  selectedSectionId.value = id;
  isDeleteModal.value = true;
}

async function confirmDelete() {
  try {
    await sectionService.delete(selectedSectionId.value);
    sections.value = sections.value.filter(s => s.id !== selectedSectionId.value);
    ElMessage.success('Раздел удалён');
  } catch (e) {
    ElMessage.error('Ошибка при удалении');
  } finally {
    isDeleteModal.value = false;
  }
}

async function handleSaved() {
  isEditModal.value = false;
  await loadSections();
  ElMessage.success('Изменения сохранены');
}

async function handleCreated() {
  isCreateModal.value = false;
  await loadSections();
  ElMessage.success('Раздел создан');
}

onMounted(loadSections);
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: auto;
}
</style>
