import { createStore } from 'vuex';
import axios from 'axios';

export default createStore({
    state: {
        tests: [],
        user: null,
        userStatistics: {
            completedTests: 0,
            averageScore: 0,
            totalPoints: 0
        },
        leaderboard: [],
        notification: "",
    },
    mutations: {
        setNotification(state, message) {
            state.notification = message;
        },
        setTest(state, test) {
            state.test = test;
        },
        setTestResults(state, results) {
            state.testResults = results;
        },
        setTests(state, tests) {
            state.tests = tests;
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
            localStorage.setItem('user', JSON.stringify(userData));
        },
        addNotification(state, message) {
            state.notifications.push(message);
        },
        setUserStatistics(state, statistics) {
            state.userStatistics = statistics;
        },
        setLeaderboard(state, leaderboard) {
            state.leaderboard = leaderboard;
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
                const response = await axios.get('/api/v1/leaderboard/top');
                commit('setLeaderboard', response.data);
            } catch (error) {
                console.error('Error fetching leaderboard:', error);
            }
        },
        async register({ commit }, { email, password }) {
            try {
                const response = await axios.post('/api/v1/auth/register', { email, password });
                commit('setUser', response.data);
            } catch (error) {
                console.error("Error during registration:", error);
                throw error;
            }
        },
        async login({ commit }, { email, password }) {
            try {
                const response = await axios.post('/api/v1/auth/login', { email, password });
                commit('setUser', response.data);  // Сохраняем токен в store
            } catch (error) {
                console.error("Error during login:", error);
                throw error;
            }
        },
        async fetchRewards({ commit }) {
            try {
                const response = await axios.get('/api/v1/rewards');
                commit('setRewards', response.data);
            } catch (error) {
                console.error('Error fetching rewards:', error);
            }
        },
        async fetchAchievements({ commit }) {
            try {
                const response = await axios.get('/api/v1/achievements');
                commit('setAchievements', response.data);
            } catch (error) {
                console.error('Error fetching achievements:', error);
            }
        },
        async fetchTestResults({ commit }, testId) {
            try {
                const response = await axios.get(`/api/v1/exam-results/${testId}`);
                commit('setTestResults', response.data);  // Сохраняем результаты в состояние
            } catch (error) {
                console.error('Error fetching test results:', error);
            }
        },
        async submitTestAnswers({ commit }, answers) {
            try {
                const response = await axios.post('/api/v1/user-answers/submit', { answers });
                commit('setTestResults', response.data);
            } catch (error) {
                console.error('Error submitting answers:', error);
            }
        },
        async fetchTests({ commit }) {
            try {
                const response = await axios.get('/api/v1/exams');
                commit('setTests', response.data);
            } catch (error) {
                console.error('Error fetching tests:', error);
            }
        },
        async fetchTestById({ commit }, id) {
            try {
                const response = await axios.get(`/api/v1/exams/${id}`);
                commit('setTest', response.data);
            } catch (error) {
                console.error('Error fetching test:', error);
            }
        },
        async fetchTopUsers({ commit }) {
            try {
                const response = await axios.get('/api/v1/users/top');
                commit('setTopUsers', response.data);
            } catch (error) {
                console.error('Error fetching top users:', error);
            }
        },
        async fetchUserProfile({ commit }) {
            try {
                const response = await axios.get('/api/v1/users/current');
                commit('setUser', response.data);
            } catch (error) {
                console.error('Error fetching user profile:', error);
            }
        },
        async sendTestReminder({ commit }, userId) {
            try {
                await axios.post(`/api/v1/notifications/send-reminder?userId=${userId}`);
                commit('addNotification', 'Test reminder sent successfully');
            } catch (error) {
                console.error('Error sending reminder:', error);
            }
        },
        async fetchUserStatistics({ commit }) {
            try {
                const response = await axios.get('/api/v1/users/statistics');
                commit('setUserStatistics', response.data);
            } catch (error) {
                console.error('Error fetching user statistics:', error);
            }
        }
    },
    getters: {
        userScore: (state) => {
            return state.user ? state.user.score : 0;
        },
    },
});
