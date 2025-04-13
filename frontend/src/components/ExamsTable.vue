<template>
  <section class="test-table-section mt-4">
    <vue-good-table
        :columns="columns"
        :rows="tests"
        :search-options="{enabled: true}"
        :pagination-options="{enabled: true}"
        styleClass="vgt-table striped"
    >
      <template v-slot:table-row="props">
        <span v-if="props.column.field === 'action'">
          <!-- Edit Button -->
          <button @click="editTest(props.row)" class="btn btn-outline-warning mx-1">
            ✏️
          </button>

          <!-- Delete Button -->
          <button
              @click="deleteTest(props.row.id)"
              class="btn btn-outline-danger mx-1"
          >
            🗑️
          </button>
        </span>
      </template>
    </vue-good-table>
  </section>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import { VueGoodTable } from 'vue-good-table-next';
import 'vue-good-table-next/dist/vue-good-table-next.css';
import axiosInstance from '@/api/axiosInstance.js';
import examService from '@/services/examService.js';

const tests = ref([]);
const columns = [
  { label: 'Название', field: 'name' },
  { label: 'Тип', field: 'examType' },
  { label: 'Язык', field: 'language' },
  { label: 'Длительность (мин)', field: 'duration' },
  { label: 'Разделы', field: 'sections.name' },
  { label: 'Действия', field: 'action' },
];

// Fetch tests from API
const fetchTests = async () => {
  try {
    const response = await examService.getExams();
    tests.value = response || [];
  } catch (error) {
    console.error('Error fetching tests', error);
    tests.value = [];
  }
};

// Edit test
const editTest = async (test) => {
  const updatedName = prompt('Edit Test Name', test.name);
  const updatedSections = prompt('Edit Sections', test.sections);
  if (updatedName && updatedSections) {
    try {
      await axiosInstance.put(`/tests/${test.id}`, {
        name: updatedName,
        sections: updatedSections,
      });
      await fetchTests();
    } catch (error) {
      console.error('Error updating test', error);
    }
  }
};

// Delete test
const deleteTest = async (id) => {
  try {
    await axiosInstance.delete(`/tests/${id}`);
    tests.value = tests.value.filter((test) => test.id !== id);
  } catch (error) {
    console.error('Error deleting test', error);
  }
};

onMounted(() => {
  fetchTests();
});
</script>

<style scoped>
.test-table-section {
  margin-top: 20px;
}

.test-table-section .vgt-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.test-table-section .vgt-table th,
.test-table-section .vgt-table td {
  padding: 12px;
  text-align: center;
}

.test-table-section .vgt-table th {
  background-color: #f4f4f4;
}

.test-table-section .vgt-table td {
  background-color: #fafafa;
}

.test-table-section .vgt-table button {
  padding: 8px 16px;
  border-radius: 5px;
  border: none;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.test-table-section .vgt-table .btn-outline-warning {
  background-color: #ffc107;
}

.test-table-section .vgt-table .btn-outline-danger {
  background-color: #dc3545;
  color: white;
}

.test-table-section .vgt-table .btn-outline-warning:hover {
  background-color: #e0a800;
}

.test-table-section .vgt-table .btn-outline-danger:hover {
  background-color: #c82333;
}
</style>
