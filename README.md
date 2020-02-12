#### Инициализация БД
 - Если инициализируем базу данных с помощью хибернейта, и хотим выполнить на старте какие-то скрипты после инициализации, то нужно закинуть в ресурсы
  файл `data.sql`, пропертю выставить `spring.datasource.initialization-mode=always`(`spring.jpa.hibernate.ddl-auto` должен быть не `none`)
 - Если мы не хотим инициализации бд хибернейтом, а хотим своим скриптом: нужно закинуть в ресурсы
  файл `schema.sql`, пропертю выставить `spring.datasource.initialization-mode=always`(`spring.jpa.hibernate.ddl-auto` должен быть `none`);
 - schema.sql и data.sql неприменимы вместе
 
 Для того, чтобы выполнить инициализацию бд на старте приложения нужно в ресурсы положить файлик `schema.sql` и включить `spring.datasource.initialization-mode=always`. 
 Нужно помнить, что `schema.sql` не будет работать, если мы генерируем схему с помощбю хибернейта(`hibernate.ddl-auto` должен быть `none`).
 - Если мы генерируем схему приложения с помощью хибернейта, то мы можем выполнить какие-то sql скрипты после генерации. Для этого нужно в в ресурсы закинуть `data.sql`.
 Нужно помнить, что data.sql не работает 

#### Entity
###### Условия
 - Класс сущности должен быть помечен @Entity и иметь @Id(в случае простого PK).
 - Иметь public или protected конструктор без аргументов.
 - Быть классом(даже абстрактным), перечисления и интерфейсы не могут энтитями.
 - Быть классом верхнего уровня(не вложенным или внутренним).
 - Финальные классы не могут быть энтитями.
 
 
 