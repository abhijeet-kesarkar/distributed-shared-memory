# distributed-shared-memory


To run the app

gradlew assemble

cd build/libs

java -jar distributed-shared-memory-1.0-SNAPSHOT.jar --server.port=9000 --cache.port=8000 --cache.remote.ports=8001
java -jar distributed-shared-memory-1.0-SNAPSHOT.jar --server.port=9001 --cache.port=8001 --cache.remote.ports=8000
