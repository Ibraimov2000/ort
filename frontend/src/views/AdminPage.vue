<template>
  <section class="admin-page py-5 mt-5">
    <div class="container">
      <header>
        <h1 class="display-4 fw-bold mb-5">
          <span class="underline">Admin</span> Page
        </h1>
        <h2>{{ message }}</h2>
      </header>

      <UsersTable/>
      <ExamsTable/>
      <SectionsTable/>
    </div>
  </section>
</template>


<script setup>
import { ref, onMounted } from "vue";
import axiosInstance from "@/api/axiosInstance.js";
import UsersTable from "@/components/UsersTable.vue";
import examService from "@/services/examService.js";
import ExamsTable from "@/components/crud/ExamsTable.vue";
import SectionsTable from "@/components/crud/SectionsTable.vue";

const message = ref("");
const tests = ref([]);


const getMessage = async () => {
  try {
    const response = await axiosInstance.get("/admin");
    if (response.status === 200) {
      message.value = response.data;
    }
  } catch (e) {
    message.value = "Error loading admin data";
  }
};

const fetchTests = async () => {
  try {
    const response = await examService.getExams();
    tests.value = response || [];
  } catch (error) {
    tests.value = [];
  }
};

onMounted(() => {
  getMessage();
  fetchTests();
});
</script>

<style scoped>
.admin-page {
  font-family: Arial, sans-serif;
}

header h1 {
  font-size: 3rem;
  color: #333;
}

header h2 {
  font-size: 1.25rem;
  color: #555;
}

.test-table-section table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.test-table th,
.test-table td {
  padding: 12px;
  border: 1px solid #ddd;
  text-align: center;
}

.test-table th {
  background-color: #f4f4f4;
  color: #333;
}

.test-table td {
  background-color: #fafafa;
}

.test-table button {
  padding: 8px 16px;
  border-radius: 5px;
  border: none;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.test-table .edit-btn {
  background-color: #ffc107;
}

.test-table .delete-btn {
  background-color: #dc3545;
  color: white;
}

.test-table .edit-btn:hover {
  background-color: #e0a800;
}

.test-table .delete-btn:hover {
  background-color: #c82333;
}

.test-admin {
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.test-admin h2 {
  margin-bottom: 20px;
  color: #333;
}

.test-admin .form-group {
  margin-bottom: 15px;
}

.test-admin label {
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
  display: block;
}

.test-admin input,
.test-admin select {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 1rem;
}

.test-admin button.submit-btn {
  padding: 12px;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 1.1rem;
  width: 100%;
}

.test-admin button.submit-btn:hover {
  background-color: #218838;
}

.test-admin input::placeholder {
  font-style: italic;
}

.test-admin ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.test-admin li {
  margin-top: 10px;
  padding: 10px;
  background: #f9f9f9;
  border-radius: 5px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.test-admin button:last-child {
  background-color: #dc3545;
  color: white;
}

.test-admin button:last-child:hover {
  background-color: #c82333;
}
</style>
