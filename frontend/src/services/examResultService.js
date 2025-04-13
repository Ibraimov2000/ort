import axiosInstance from "@/api/axiosInstance.js";

export default {
    getAll() {
        return axiosInstance.get('/exam-results');
    },
    get(id) {
        return axiosInstance.get(`/exam-results/${id}`);
    },
    create(data) {
        return axiosInstance.post('/exam-results', data);
    },
    update(id, data) {
        return axiosInstance.put(`/exam-results/${id}`, data);
    },
    delete(id) {
        return axiosInstance.delete(`/exam-results/${id}`);
    }
};
