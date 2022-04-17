REST сервис опросов 

Для запуска в docker контейнерах необходимо скачать проект, распаковать и запустить следующим образом:

1. Находясь в папке pull\src\main\resources\dockerDB запустить в терминале команду "docker build --tag pull-db ."
2. Находясь в папке pull запустить в терминале команду "docker build --tag pull ."
3. Находясь в этой же папке запустить в терминале команду "mvn clean install"
4. Находясь в этой же папке запустить в терминале команду "docker-compose up"

Сервис доступен по адресу localhost:8080/api/v1/pulls/ 

Документация по адресу http://localhost:8080/docs.html

Андрей Куранов 
- telegram: https://t.me/AndreyKuranov
- kuranov.andrey@gmail.com
- резюме https://hh.ru/resume/946b79acff0747362c0039ed1f5a4379364f6f