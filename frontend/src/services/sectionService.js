import axiosInstance from "@/api/axiosInstance.js";

export default {
    getAll() {
        return axiosInstance.get('/sections');
    },
    get(id) {
        return axiosInstance.get(`/sections/${id}`);
    },
    create(data) {
        return axiosInstance.post('/sections', data);
    },
    update(id, data) {
        return axiosInstance.put(`/sections/${id}`, data);
    },
    delete(id) {
        return axiosInstance.delete(`/sections/${id}`);
    }
};
