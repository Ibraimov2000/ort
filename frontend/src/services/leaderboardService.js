import axiosInstance from "@/api/axiosInstance.js";

const leaderboardService = {
    getTopUsers() {
        return axiosInstance.get('/leaderboard/top')
            .then(response => response.data)
            .catch(error => {
                console.error('Error fetching leaderboard:', error);
                throw error;
            });
    }
};

export default leaderboardService;
