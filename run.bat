echo "Compile and running pull service ..."
cd src\main\resources\dockerDB\
docker build --tag pull-db . & cd ../ & cd ../ & cd ../ & cd ../ & mvnw.cmd clean install & docker build --tag pull . &docker-compose up