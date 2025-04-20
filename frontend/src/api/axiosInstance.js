import axios from "axios";

const baseURL = "http://localhost:8080/api/v1";

const axiosInstance = axios.create({
    baseURL,
});

const token = localStorage.getItem("access_token");
if (token) {
    axiosInstance.defaults.headers.common["Authorization"] = `Bearer ${token}`;
}

export default axiosInstance;
