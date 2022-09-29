# TrainingSpringProject
## Содержание проекта
Предметная область проекта - склад товаров, который также занимается закупкой и перепродажей продукции. 
[Подобную вещь](https://github.com/elementalius-2k/Warehouse-2) я уже делал в рамках обучения, но не на Spring, да и качество кода там достаточно низкое.
Немного про требования:
+ система должна хранить информацию о товарах, которые разделены по группам, производителях, партнерах (это и поставщики, и покупатели), работниках склада, накладных
+ система должна предоставлять базовые CRUD-операции для этих данных (если это не противоречит общей логике)
+ система должна предоставлять возможность покупки/продажи товаров
## Используемые технологии
+ Java 11
+ фреймворк Spring
+ база данных PostgreSQL
+ для версионирования БД используется Flyway
+ Docker-контейнер с приложением доступен на Docker Hub [здесь](https://hub.docker.com/r/elementalius/warehouse)
+ приложение и БД можно поднять с помощью `docker-compose up`
+ доступные запросы после подъема можно посмотреть с помощью Swagger

Развертывания на каком-либо хостинге пока не планируется.
