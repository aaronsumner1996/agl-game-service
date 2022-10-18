kubectl delete deployment agl-game-service

kubectl delete service agl-game-service

./gradlew clean build

docker build . -t agl-game-service

kubectl create -f ./k8s