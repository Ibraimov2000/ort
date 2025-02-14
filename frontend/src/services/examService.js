import axios from 'axios';

const API_URL = 'http://localhost:8080/api/exams';

export default {
    getAll() {
        return axios.get(API_URL);
    },
    getById(id) {
        return axios.get(`${API_URL}/${id}`);
    },
    create(exam) {
        return axios.post(API_URL, exam);
    },
    update(id, exam) {
        return axios.put(`${API_URL}/${id}`, exam);
    },
    delete(id) {
        return axios.delete(`${API_URL}/${id}`);
    }
};