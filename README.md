# FileUploaderService
## Requirement
#### JAVA: 11
#### Maven: 3.5.3
#### SpringBoot: 2.5.6
## Before run:
#### To build 
```
mvn clean pachage
```
#### To run the server
```
java -jar file-uploader-service-0.0.1-SNAPSHOT.jar
```
#### Example of interaction with the endpoints
###### To up load file with tag
```
curl -v -X POST -F "file=@./receipt.txt" -F "tag=Finance" http://localhost:8080/api/upload
```
