kubectl delete deployment agl-game-service -n agl-dev

kubectl delete service agl-game-service -n agl-dev

./gradlew clean build

docker build . -t agl-game-service

kubectl create -f ./k8sLive -n agl-dev