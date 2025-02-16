import axios from 'axios';

const API_URL = 'http://localhost:8080/api/v1/exams';

export default {
    getAll() {
        return axios.get(API_URL);
    },
    get(id) {
        return axios.get(`${API_URL}/${id}`);
    },
    create(data) {
        return axios.post(API_URL, data);
    },
    update(id, data) {
        return axios.put(`${API_URL}/${id}`, data);
    },
    delete(id) {
        return axios.delete(`${API_URL}/${id}`);
    },
    getEndTime() {
        return axios.get(`${API_URL}/endTime`).then(response => {
            const endTime = response.data.endTime;
            startTimer(new Date(endTime));
        })
    }
};