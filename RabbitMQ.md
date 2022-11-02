# RABBITMQ NOTLAR
## 1. Docker Kurulum

docker run -d --hostname my-rabbit --name rabbit-bilgeadam-microservices -e RABBITMQ_DEFAULT_USER=bilgeadmin -e RABBITMQ_DEFAULT_PASS=bilge! -p 5672:5672 -p:15672:15672 rabbitmq:3-management