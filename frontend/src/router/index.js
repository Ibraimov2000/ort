import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '../views/Home.vue';
import UserProfile from "@/views/UserProfile.vue";
import TestsPage from "@/views/TestsPage.vue";
import TestPage from "@/views/TestPage.vue";
import TestResultPage from "@/views/TestResultPage.vue";
import AchievementsPage from "@/views/AchievementsPage.vue";
import Reward from "@/components/Reward.vue";
import Register from "@/components/Register.vue";
import Login from "@/components/Login.vue";

const routes = [
    {
        path: '/',
        name: 'Home',
        component: HomePage,
    },
    {
        path: '/profile',
        name: 'UserProfile',
        component: UserProfile,
    },
    {
        path: '/tests',
        name: 'TestsPage',
        component: TestsPage,
    },
    {
        path: '/test/:id',
        name: 'TestPage',
        component: TestPage,
    },
    {
        path: '/test-results/:id',
        name: 'TestResultPage',
        component: TestResultPage,
    },
    {
        path: '/achievements',
        name: 'AchievementsPage',
        component: AchievementsPage,
    },
    {
        path: '/rewards',
        name: 'RewardsPage',
        component: Reward,
    },
    {
        path: '/register',
        name: 'RegisterPage',
        component: Register,
    },
    {
        path: '/login',
        name: 'LoginPage',
        component: Login,
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

router.beforeEach((to, from, next) => {
    const user = JSON.parse(localStorage.getItem('user'));
    if (to.meta.requiresAuth && !user) {
        next('/login');
    } else {
        next();
    }
});
export default router;
