REST сервис опросов

Для запуска в операционной системе **должен быть установлен docker и jdk.**

Для запуска в Windows скачать, распаковать архив, запустить файл, лежащий в корне проекта **run.bat**

Запуск в Linux:
1. Находясь в папке pull\src\main\resources\dockerDB запустить в терминале команду "docker build --tag survey-db ."
2. Перейти в папку pull, запустить в терминале команду "mvnw.cmd clean install"
3. Находясь в этой же папке запустить в терминале команду "docker build --tag survey ."
4. Находясь в этой же папке запустить в терминале команду "docker-compose up"

Сервис доступен по адресу **http://localhost:8111/api/v1/surveys/**

Документация по адресу **http://localhost:8111/swagger-ui.html**

Андрей Куранов

- telegram: https://t.me/AndreyKuranov
- kuranov.andrey@gmail.com
- резюме https://hh.ru/resume/946b79acff0747362c0039ed1f5a4379364f6f
