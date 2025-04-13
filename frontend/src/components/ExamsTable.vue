<template>
  <section class="test-table-section mt-4">
    <vue-good-table
        :columns="columns"
        :rows="tests"
        :search-options="{ enabled: true }"
        :pagination-options="{ enabled: true }"
        styleClass="vgt-table striped"
    >
      <template v-slot:table-row="props">
        <span v-if="props.column.field === 'action'">
          <button
              class="btn btn-outline-danger mx-1"
              @click="setTestToDelete(props.row.id)"
          >
            <svg
                class="icon icon-tabler icon-tabler-trash fs-3"
                xmlns="http://www.w3.org/2000/svg"
                width="1em"
                height="1em"
                viewBox="0 0 24 24"
                stroke-width="2"
                stroke="currentColor"
                fill="none"
                stroke-linecap="round"
                stroke-linejoin="round"
            >
              <path stroke="none" d="M0 0h24v24H0z" fill="none" />
              <line x1="4" y1="7" x2="20" y2="7" />
              <line x1="10" y1="11" x2="10" y2="17" />
              <line x1="14" y1="11" x2="14" y2="17" />
              <path d="M5 7l1 12a2 2 0 0 0 2 2h8a2 2 0 0 0 2 -2l1 -12" />
              <path d="M9 7v-3a1 1 0 0 1 1 -1h4a1 1 0 0 1 1 1v3" />
            </svg>
          </button>
          <button
              class="btn btn-outline-primary mx-1"
              @click="editTest(props.row)"
          >
            Редактировать
          </button>
        </span>
      </template>
    </vue-good-table>

    <!-- Модальное окно -->
    <div v-if="isModalVisible" class="modal fade show d-block" tabindex="-1">
      <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-body text-center">
            <h3>Вы уверены?</h3>
          </div>
          <div class="modal-footer">
            <button class="btn btn-light" @click="closeModal">Close</button>
            <button class="btn btn-danger" @click="confirmDelete">
              Удалить
            </button>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { VueGoodTable } from 'vue-good-table-next';
import 'vue-good-table-next/dist/vue-good-table-next.css';
import axiosInstance from '@/api/axiosInstance.js';
import examService from '@/services/examService.js';

const tests = ref([]);
const isModalVisible = ref(false);
let testToDelete = null;

const columns = [
  { label: 'Название', field: 'name' },
  { label: 'Тип', field: 'examType' },
  { label: 'Язык', field: 'language' },
  { label: 'Длительность (мин)', field: 'duration' },
  { label: 'Разделы', field: 'sections.name' },
  { label: 'Действия', field: 'action' },
];

const fetchTests = async () => {
  try {
    const response = await examService.getExams();
    tests.value = response || [];
  } catch (error) {
    console.error('Ошибка при получении тестов', error);
  }
};

const editTest = async (test) => {
  const updatedName = prompt('Edit Test Name', test.name);
  const updatedSections = prompt(
      'Edit Sections (comma-separated)',
      test.sections?.map((s) => s.name).join(', ') || ''
  );

  if (!updatedName || !updatedSections) return;

  try {
    const updatedSectionArray = updatedSections
        .split(',')
        .map((s) => s.trim())
        .filter(Boolean);

    await axiosInstance.put(`/tests/${test.id}`, {
      name: updatedName,
      sections: updatedSectionArray.map((name) => ({ name })),
    });

    await fetchTests();
  } catch (error) {
    console.error('Ошибка при обновлении теста', error);
  }
};

const setTestToDelete = (testId) => {
  isModalVisible.value = true;
  testToDelete = testId;
};

const closeModal = () => {
  isModalVisible.value = false;
  testToDelete = null;
};

const confirmDelete = async () => {
  if (testToDelete !== null) {
    try {
      await examService.deleteExam(testToDelete);
      tests.value = tests.value.filter((test) => test.id !== testToDelete);
    } catch (error) {
      console.error('Ошибка при удалении теста', error);
    }
  }
  closeModal();
};

onMounted(() => {
  fetchTests();
});
</script>

<style scoped>
.modal {
  background-color: rgba(0, 0, 0, 0.5);
}
</style>
