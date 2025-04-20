import axiosInstance from "@/api/axiosInstance.js";

export default {
    async getAll() {
        const response = await axiosInstance.get('/sections');
        return response.data;
    },
    get(id) {
        return axiosInstance.get(`/sections/${id}`).then(res => res.data);
    },
    create(data) {
        return axiosInstance.post('/sections', data).then(res => res.data);
    },
    update(id, data) {
        return axiosInstance.put(`/sections/${id}`, data).then(res => res.data);
    },
    delete(id) {
        return axiosInstance.delete(`/sections/${id}`).then(res => res.data);
    }
};
