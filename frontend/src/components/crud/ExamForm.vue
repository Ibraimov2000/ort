<template>
  <el-form :model="exam" label-position="top" @submit.prevent>
    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
      <el-form-item label="Название">
        <el-input v-model="exam.name" required />
      </el-form-item>

      <el-form-item label="Описание">
        <el-input type="textarea" v-model="exam.description" :rows="2" />
      </el-form-item>

      <el-form-item label="Длительность (мин)">
        <el-input-number v-model.number="exam.duration" :min="1" />
      </el-form-item>

      <el-form-item label="Тип теста">
        <el-select v-model="exam.examType" placeholder="Выберите тип">
          <el-option label="Основной" value="MAIN" />
          <el-option label="Дополнительный" value="ADDITIONAL" />
        </el-select>
      </el-form-item>

      <el-form-item label="Язык">
        <el-select v-model="exam.language" placeholder="Выберите язык">
          <el-option label="Кыргызский" value="KY" />
          <el-option label="Русский" value="RU" />
        </el-select>
      </el-form-item>
    </div>

    <h4 class="mt-6 mb-2 font-semibold">Разделы</h4>
    <div v-if="!exam.sections.length" class="text-gray-500 mb-2">Разделы не добавлены</div>
    <EditSectionForm
        v-for="(s, i) in exam.sections"
        :key="s.id || i"
        :section="s"
        @remove="exam.sections.splice(i, 1)"
    />

    <div class="flex gap-2 items-center mt-3">
      <el-select v-model="templateSectionId" placeholder="Выбрать шаблон раздела">
        <template v-for="s in allSections" :key="s.id">
          <el-option :label="s.name" :value="s.id" />
        </template>
      </el-select>
      <el-button type="primary" :disabled="!templateSectionId" @click="addFromTemplate">Добавить</el-button>
    </div>

    <div class="text-end mt-6">
      <el-button @click="$emit('cancel')">Отмена</el-button>
      <el-button type="primary" :loading="saving" @click="save">Сохранить</el-button>
    </div>
  </el-form>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue';
import { cloneDeep as deepCopy } from "lodash-es";
import sectionService from '@/services/sectionService.js';
import examService from '@/services/examService.js';
import EditSectionForm from "@/components/crud/EditSectionForm.vue";

const props = defineProps({ initialExam: Object });
const emit = defineEmits(['saved', 'cancel']);

function parseISOToMinutes(isoDuration) {
  if (typeof isoDuration === 'number') return isoDuration;
  if (typeof isoDuration !== 'string') return 0;

  const match = isoDuration.match(/PT(?:(\d+)H)?(?:(\d+)M)?/);
  const hours = match?.[1] ? parseInt(match[1]) : 0;
  const minutes = match?.[2] ? parseInt(match[2]) : 0;
  return hours * 60 + minutes;
}

function toISOFromMinutes(mins) {
  const h = Math.floor(mins / 60);
  const m = mins % 60;
  return `PT${h ? h + 'H' : ''}${m ? m + 'M' : ''}`;
}

function cleanSection(section) {
  return {
    id: section.id,
    name: section.name,
    questionsCount: section.questionsCount ?? 0,
    questions: section.questions?.map(cleanQuestion)
  };
}

function cleanQuestion(q) {
  return {
    id: q.id,
    text: q.text,
    score: q.score,
    questionType: q.questionType,
    answers: q.answers?.map(a => ({ id: a.id, text: a.text, isCorrect: a.isCorrect }))
  };
}

const exam = reactive({ ...deepCopy(props.initialExam) });
exam.sections = props.initialExam.sections.map(s => ({ ...s }));
exam.duration = parseISOToMinutes(props.initialExam.duration);

const allSections = ref([]);
const templateSectionId = ref('');
const saving = ref(false);

onMounted(async () => {
  try {
    const response = await sectionService.getAll();
    allSections.value = Array.isArray(response) ? response.filter(s => s?.id && s?.name) : [];
  } catch (e) {
    console.error('Ошибка при загрузке шаблонов разделов', e);
  }
});

function addFromTemplate() {
  const tmpl = allSections.value.find(s => s.id === templateSectionId.value);
  if (!tmpl) return;
  exam.sections.push({
    id: null,
    name: tmpl.name,
    questionsCount: tmpl.questionsCount ?? 0,
    questions: tmpl.questions ?? []
  });
  templateSectionId.value = '';
}

async function save() {
  try {
    saving.value = true;
    const payload = {
      ...exam,
      duration: toISOFromMinutes(exam.duration),
      sections: exam.sections.map(cleanSection)
    };
    console.log(payload);
    await examService.updateExam(payload);
    emit('saved');
  } catch (e) {
    console.error(e);
  } finally {
    saving.value = false;
  }
}
</script>
