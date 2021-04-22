# WorkFleek Wallet 

### Running WorkFleek Wallet locally
WorkFleek Wallet is a [Spring Boot](https://spring.io/guides/gs/spring-boot) application built using [Maven](https://spring.io/guides/gs/maven/). You can build a jar file and run it from the command line:

```
git clone https://github.com/NdiranguMuchai/workfleek-test-API.git
cd workfleek-test-API
./mvnw package
java -jar target/*.jar
```
Or you can run it from Maven directly using the Spring Boot Maven plugin. If you do this it will pick up changes that you make in the project immediately (changes to Java source files require a compile as well - most people use an IDE for this):

```
./mvnw spring-boot:run
```
### Postman
A postman collection has been provided to assist with endpoint requests

* Open Postman
* Select the `Import` button
* Import the file under `src/main/resources/Springboot-Dev-Test-REST API.postman_collection.json`
* Expand th folder in postman

Each entry in the `collection` contains information in its `Body` tab if necessary.