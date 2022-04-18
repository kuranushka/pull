REST сервис опросов 

Для запуска в операционной системе **должен быть установлен docker и jre.** 

Необходимо скачать проект, распаковать и запустить в терминале файл, лежащий в корне проекта **run.bat**


Или пошагово проделать следующе действия:

1. Находясь в папке pull\src\main\resources\dockerDB запустить в терминале команду "docker build --tag pull-db ."
2. Перейти в папку pull, запустить в терминале команду "mvnw.cmd clean install"
3. Находясь в этой же папке запустить в терминале команду "docker build --tag pull ."
4. Находясь в этой же папке запустить в терминале команду "docker-compose up"

Сервис доступен по адресу **localhost:8080/api/v1/pulls/** 

Документация по адресу **http://localhost:8080/docs.html**

Андрей Куранов 
- telegram: https://t.me/AndreyKuranov
- kuranov.andrey@gmail.com
- резюме https://hh.ru/resume/946b79acff0747362c0039ed1f5a4379364f6f