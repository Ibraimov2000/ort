import { createStore } from 'vuex';
import leaderboardService from "@/services/leaderboardService.js";
import authService from "@/services/authService.js";
import examService from "@/services/examService.js";
import userService from "@/services/userService.js";

export default createStore({
    state: {
        exams: [],
        examResults: [],
        user: null,
        userStatistics: {
            completedTests: 0,
            averageScore: 0,
            totalPoints: 0
        },
        leaderboard: [],
        notification: "",
        isAuthenticated: false,
        exam: null,
        userRole: null,
    },
    mutations: {
        setNotification(state, message) {
            state.notification = message;
        },
        setExam(state, exam) {
            state.exam = exam;
        },
        setExamResults(state, results) {
            state.examResults = results;
        },
        setExams(state, exams) {
            state.exams = exams;
        },
        setTopUsers(state, users) {
            state.topUsers = users;
        },
        setAchievements(state, achievements) {
            state.achievements = achievements;
        },
        setRewards(state, rewards) {
            state.rewards = rewards;
        },
        setUser(state, userData) {
            state.user = userData;
            state.isAuthenticated = true;
            localStorage.setItem('user', JSON.stringify(userData));
            localStorage.setItem('accessToken', userData.token);
        },
        logout(state) {
            state.userRole = null;
            state.isAuthenticated = false;
            localStorage.removeItem('user');
            localStorage.removeItem('accessToken');
        },
        setUserStatistics(state, statistics) {
            state.userStatistics = statistics;
        },
        setLeaderboard(state, leaderboard) {
            state.leaderboard = leaderboard;
        },
        setAuthenticated(state, isAuthenticated) {
            state.isAuthenticated = isAuthenticated;
        },
        setUserRole(state, userRole) {
            state.userRole = userRole;
        }
    },
    actions: {
        triggerNotification({ commit }, message) {
            commit('setNotification', message);
            setTimeout(() => {
                commit('setNotification', "");
            }, 5000);
        },
        async fetchLeaderboard({ commit }) {
            try {
                const leaderboard = await leaderboardService.getTopUsers();
                commit('setLeaderboard', leaderboard);
            } catch (error) {
                console.error('Error fetching leaderboard:', error);
            }
        },
        async register({ commit }, { email, password }) {
            try {
                const response = await authService.register(email, password);
                commit('setUser', response);
            } catch (error) {
                console.error("Error during registration:", error);
                throw error;
            }
        },
        login({commit}, {userRole}) {
            commit('setAuthenticated', true);
            commit('setUserRole', userRole);
        },
        logout({commit}) {
            commit('logout');
        },
        async fetchExams({ commit }) {
            try {
                const exams = await examService.getExams();
                commit('setExams', exams);
            } catch (error) {
                console.error('Error fetching tests:', error);
            }
        },
        async fetchExamById({ commit }, id) {
            try {
                const exam = await examService.getExamById(id);
                commit('setExam', exam);
            } catch (error) {
                console.error('Error fetching exam:', error);
            }
        },
        async submitExamAnswers({ commit }, answers) {
            try {
                const results = await examService.submitAnswers(answers);
                commit('setExamResults', results);
            } catch (error) {
                console.error('Error submitting answers:', error);
            }
        },
        async fetchUserProfile({ commit }) {
            try {
                const userProfile = await authService.getUserProfile();
                commit('setUser', userProfile);
            } catch (error) {
                console.error('Error fetching user profile:', error);
            }
        },
        async fetchUserStatistics({ commit }) {
            try {
                const stats = await authService.getUserStatistics();
                commit('setUserStatistics', stats);
            } catch (error) {
                console.error('Error fetching user statistics:', error);
            }
        },
        checkAuthentication({ commit }) {
            const token = localStorage.getItem('accessToken');
            if (token) {
                commit('setAuthenticated', true);
            } else {
                commit('setAuthenticated', false);
            }
        },
        async fetchAchievements({commit}) {
            const achievements = userService.
            commit('setAchievements')
        }
    },
    getters: {
        isAuthenticated: state => state.isAuthenticated,
        userScore: state => {
            return state.user ? state.user.score : 0;
        },
        isUserAuthenticated: (state) => state.isAuthenticated,
        isAdmin: (state) => state.userRole === "ADMIN",
        isUser: (state) => state.userRole === "USER",
    },
});
