Run the application
===============================
 1. профиль local с [H2]. 
 2. профиль prod с [MySQL].
 
 #### Через Eclipse: 
 Чтобы запустить приложение на панели инструментов, 
 выберите Run-> Run Configurations-> Arguments-> VM Arguments. 
 Добавить -Dspring.profiles.active = local или 
 -Dspring.profiles.active = prod
 
 #### Через IDEA:
 --spring.profiles.active=local или --spring.profiles.active=prod
 
 #### Через командную строку
 в корне проекта > java -jar target/SpringBootCRUDPublisherAndApplication-1.0.0.jar 
 --spring.profiles.active=local
 
 Откройте браузер и перейдите по адресу http://localhost:8080/springboot/