<template>
  <el-form :model="section" label-position="top" @submit.prevent>
    <el-form-item label="Название раздела" prop="name" :rules="[{ required: true, message: 'Введите название', trigger: 'blur' }]">
      <el-input v-model="section.name" placeholder="Введите название раздела" />
    </el-form-item>

    <el-form-item label="Количество вопросов" prop="questionsCount" :rules="[{ required: true, message: 'Введите количество вопросов', trigger: 'blur' }]">
      <el-input-number v-model.number="section.questionsCount" :min="0" placeholder="0" />
    </el-form-item>

    <div class="text-end mt-4">
      <el-button @click="$emit('cancel')">Отмена</el-button>
      <el-button type="primary" :loading="saving" @click="save">Сохранить</el-button>
    </div>
  </el-form>
</template>

<script setup>
import { reactive, ref } from 'vue';
import sectionService from '@/services/sectionService';
import { cloneDeep } from 'lodash-es';

const props = defineProps({
  initialSection: {
    type: Object,
    default: () => ({ name: '', questionsCount: 0 })
  },
  mode: {
    type: String,
    default: 'create' // or 'edit'
  }
});

const emit = defineEmits(['saved', 'cancel']);

const section = reactive(cloneDeep(props.initialSection));
const saving = ref(false);

async function save() {
  if (!section.name.trim()) return;

  try {
    saving.value = true;
    if (props.mode === 'edit') {
      await sectionService.update(section.id, section);
    } else {
      await sectionService.create(section);
    }

    emit('saved');
  } catch (e) {
    console.error(e);
  } finally {
    saving.value = false;
  }
}
</script>