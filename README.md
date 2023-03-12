# ets-demo


open your browser http://localhost:8082

used h2 database Run following SQL insert statements:

spring.datasource.url=jdbc:h2:~/development/apps/db/ets-app
spring.datasource.username=sa
spring.datasource.password=

http://localhost:8082/h2
INSERT INTO roles(name) VALUES('ROLE_USER'); 
INSERT INTO roles(name) VALUES('ROLE_MODERATOR'); 
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

Swagger link http://localhost:8082/swagger-ui.html#/


Postman collection: src/main/resource/static/collection


