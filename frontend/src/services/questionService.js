import axiosInstance from "@/api/axiosInstance.js";

export default {
    getAll() {
        return axiosInstance.get('/questions');
    },
    get(id) {
        return axiosInstance.get(`/questions/${id}`);
    },
    create(data) {
        return axiosInstance.post('/questions/create', data);
    },
    update(id, data) {
        return axiosInstance.put(`/questions/${id}`, data);
    },
    delete(id) {
        return axiosInstance.delete(`/questions/${id}`);
    }
};
