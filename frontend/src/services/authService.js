import axiosInstance from "@/api/axiosInstance.js";

const authService = {
    async login(loginRequest) {
        try {
            const response = await axiosInstance.post("/auth/login", loginRequest, {
                withCredentials: true,
            });
            const { access_token: accessToken, refresh_token: refreshToken } = response.data;

            localStorage.setItem("access_token", accessToken);
            localStorage.setItem("refresh_token", refreshToken);

            axiosInstance.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
            return accessToken;
        } catch (error) {
            console.error("Error during login:", error);
            throw error;
        }
    },

    async logout() {
        try {
            await axiosInstance.get('/auth/logout');
        } catch (error) {
            console.error("Error during logout:", error);
        }
        localStorage.removeItem('accessToken');
        localStorage.removeItem('user');
    },

    async register(email, password) {
        try {
            const response = await axiosInstance.post('/auth/register', { email, password });
            localStorage.setItem('accessToken', response.data.token);
            return response.data;
        } catch (error) {
            console.error("Error during registration:", error);
            throw error;
        }
    },

    async getUserProfile() {
        try {
            const response = await axiosInstance.get('/users/current');
            return response.data;
        } catch (error) {
            console.error('Error fetching user profile:', error);
            throw error;
        }
    },

    async getUserStatistics() {
        try {
            const response = await axiosInstance.get('/users/statistics');
            return response.data;
        } catch (error) {
            console.error('Error fetching user statistics:', error);
            throw error;
        }
    },

    isAuthenticated() {
        return !!localStorage.getItem('accessToken');
    }
};

export default authService;
