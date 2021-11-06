# FileUploaderService
## Requirement
#### JAVA: 11
#### Maven: 3.5.3
#### SpringBoot: 2.5.6
## Environment
It is able to run on Mac/Linux with the current version of code.  
If it is required to run on Windows, it is recommended to firstly  edit the file of:
```
src/main/resources/application.properties
```
Comment the line of:
```
files.root-dir=${HOME}/file-uploader-service/files
```
And uncomment the line of: 
```
#files.root-dir=C:\\file-uploader-service\\files
```
Then build and run

## Before run:
#### To build 
```
mvn clean package
```
#### To run the server
```
java -jar file-uploader-service-0.0.1-SNAPSHOT.jar
```
## Example of interaction with the endpoints
#### To view all uploaded files
```
curl -v http://localhost:8080/api
```
#### To upload file
```
curl -v -X POST -F "file=@./dummy" http://localhost:8080/api/upload
```
#### To upload file with one tag
```
curl -v -X POST -F "file=@./receipt.txt" -F "tag=Finance" http://localhost:8080/api/upload
```
#### To upload file with tags
```
curl -v -X POST -F "file=@./contract.pdf" -F "tag=Finance" -F "tag=Contracts" http://localhost:8080/api/upload
```
#### To download file
```
curl -v http://localhost:8080/api/download/receipt.txt --output receipt.txt
```
#### To view files with tag
```
curl -v http://localhost:8080/api/category?tag=Contracts
```



