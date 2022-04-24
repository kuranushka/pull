echo "Compile and running survey service ..."
cd src\main\resources\dockerDB\
docker build --tag survey-db . & cd ../ & cd ../ & cd ../ & cd ../ & mvnw.cmd clean install & docker build --tag survey . & docker-compose up