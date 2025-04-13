import { createRouter, createWebHistory } from "vue-router";
import HomePage from "../views/Home.vue";
import UserProfile from "@/components/UserProfile.vue";
import Exams from "@/views/Exams.vue";
import Exam from "@/components/Exam.vue";
import ExamResult from "@/components/ExamResult.vue";
import Achievements from "@/views/Achievements.vue";
import Reward from "@/components/Reward.vue";
import Register from "@/views/Register.vue";
import Login from "@/views/Login.vue";
import AdminPage from "@/views/AdminPage.vue";
import ResetPasswordPage from "@/views/ResetPasswordPage.vue";
import AuthenticatedUserPage from "@/views/AuthenticatedUserPage.vue";
import { adminGuard, authenticatedGuard, redirectIfAuthenticated, userGuard } from "@/router/guards.js";
import UserPage from "@/views/UserPage.vue";
import NotFoundView from "@/views/NotFoundView.vue";

const routes = [
    {
        path: "/",
        name: "Home",
        component: HomePage,
    },
    {
        path: "/profile",
        name: "UserProfile",
        component: UserProfile,
        meta: { requiresAuth: true },
    },
    {
        path: "/tests",
        name: "TestsPage",
        component: Exams,
    },
    {
        path: "/test/:id",
        name: "TestPage",
        component: Exam,
        meta: { requiresAuth: true },
    },
    {
        path: "/test-results/:id",
        name: "TestResultPage",
        component: ExamResult,
        meta: { requiresAuth: true },
    },
    {
        path: "/achievements",
        name: "AchievementsPage",
        component: Achievements,
        meta: { requiresAuth: true },
    },
    {
        path: "/rewards",
        name: "RewardsPage",
        component: Reward,
        meta: { requiresAuth: true },
    },
    {
        path: "/register",
        name: "RegisterPage",
        component: Register,
    },
    {
        path: "/login",
        name: "LoginPage",
        component: Login,
    },
    {
        path: "/reset-password",
        name: "reset-password",
        component: ResetPasswordPage,
        beforeEnter: redirectIfAuthenticated,
    },
    {
        path: "/authenticated",
        name: "authenticated",
        component: AuthenticatedUserPage,
        beforeEnter: authenticatedGuard,
    },
    {
        path: "/signup",
        name: "signup",
        component: Register,
        beforeEnter: redirectIfAuthenticated,
    },
    {
        path: "/admin",
        name: "admin",
        component: AdminPage,
        beforeEnter: adminGuard,
    },
    {
        path: "/user",
        name: "userPage",
        component: UserPage,
        beforeEnter: userGuard,
    },
    {
        path: "/404",
        name: "not-found",
        component: NotFoundView,
    },
    {
        path: "/:pathMatch(.*)*",
        redirect: "/404",
    },
];

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes,
});

export default router;
