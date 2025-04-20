import store from "@/store/index.js"; // Импортируем существующий store вместо создания нового

/**
 * Guards для защиты маршрутов от несанкционированного доступа.
 * Проверяют, аутентифицирован ли пользователь и соответствует ли его роль требованиям маршрута.
 **/

function redirectIfAuthenticated(to, from, next) {
    if (store.getters.isAuthenticated) {
        console.warn("Вы уже аутентифицированы.");
        next({ name: "home" }); // Перенаправление на главную страницу
    } else {
        next(); // Продолжить навигацию
    }
}

// Проверяет, аутентифицирован ли пользователь (роль не имеет значения)
function authenticatedGuard(to, from, next) {
    if (!store.getters.isAuthenticated) {
        console.warn("Вы не аутентифицированы.");
        next({ name: "login" }); // Перенаправление на страницу входа
    } else {
        next(); // Продолжить навигацию
    }
}

// Общий guard для проверки роли пользователя
function roleGuard(role) {
    return (to, from, next) => {
        if (!store.getters.isAuthenticated) {
            console.warn("Вы не аутентифицированы.");
            next({ name: "login" });
            return;
        }

        if (role === "admin" && store.getters.isAdmin) {
            next(); // Разрешить доступ администратору
        } else if (role === "user" && store.getters.isUser) {
            next(); // Разрешить доступ пользователю
        } else {
            console.warn(`Доступ запрещен: требуется роль ${role}.`);
            next({ name: "home" }); // Перенаправление на главную страницу
        }
    };
}

const adminGuard = roleGuard("admin");
const userGuard = roleGuard("user");

export { redirectIfAuthenticated, authenticatedGuard, adminGuard, userGuard };
