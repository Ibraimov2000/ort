# Стартуем с официального образа Node.js
FROM node:22

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем package.json и устанавливаем зависимости
COPY package.json /app/
RUN npm install

# Копируем остальные файлы
COPY . /app/

# Строим приложение
RUN npm run build

# Запускаем сервер
CMD ["npm", "run", "serve"]
