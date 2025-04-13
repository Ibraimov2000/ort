import axiosInstance from "@/api/axiosInstance";
import router from "@/router/index.js"
import store from "@/store/index.js";
import authService from "@/services/authService.js";

axiosInstance.interceptors.response.use(
    (response) => response,
    async (error) => {
        if (
            error.response.status === 401 &&
            error.response.data === "Access token expired"
        ) {
            console.log("Access token expired, trying to refresh it");
            try {
                console.log(
                    "Sending a POST request to the refresh token endpoint",
                    localStorage.getItem("refresh_token"),
                );
                const response = await axiosInstance.post(
                    "/auth/refresh-token",
                    null,
                    {
                        headers: {
                            Authorization: `Bearer ${localStorage.getItem("refresh_token")}`,
                        },
                    },
                );
                console.log("response", response);

                console.log("New access token: ", response.data.access_token);
                localStorage.setItem("access_token", response.data.access_token);

                const { config } = error;
                config.headers.Authorization = `Bearer ${response.data.access_token}`;
                return axiosInstance.request(config);
            } catch (refreshError) {
                if (refreshError.response.status === 403) {
                    console.log("Refresh token expired, logging out the user");
                    localStorage.removeItem("access_token");
                    localStorage.removeItem("refresh_token");

                    const authStore = store;

                    authStore.logout();
                    await authService.logout();

                    await router.push({
                        path: "/login",
                        query: { sessionExpired: true },
                    });
                }
            }
        }
        return Promise.reject(error);
    },
);
