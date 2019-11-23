# Basic-auth-spring-boot-app

### Installing

Build executable jar file:
```
mvn clean package
```
Package your jar to Docker image:
```
docker build -t {your-image-name} .
```
Run your image in container:
```
docker run -d -p 5000:5000 {your-image-name}
```
## Functionality

* Basic authentication
* RememberMe configuration
* HTTPS configuration
* Session management configuration