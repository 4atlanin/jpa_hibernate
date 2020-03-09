# __Repo for studing jpa/hibernate features:__ [![Build Status](https://travis-ci.com/4atlanin/jpa_hibernate.svg?branch=master)](https://travis-ci.com/4atlanin/jpa_hibernate)

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
 
 ##### Аттрибуты
 - @Temporal устарела. Применима только к старм Date.
 - @Transient 
 - На геттеры можно навешивать
 ###### Access types
  - По умолчанию, JPA смотрит на поля, а не геттеры.(Аннотации почитать, если их нету то прост поля)
  - @AccessType может быть над классом, полем или геттером. Над классом самый низкий приоритет.
  - В такой комбинации будет работать геттер.(Те то что над геттером - важнее)
   ```@Entity
   @Table( name = "entity_attributes" )
   @Access( AccessType.FIELD )
   public class EntityAttributes
   {
       @Id
       private Integer id;
   
       @Column( name = "not_work" )
       @Access( AccessType.FIELD )
       private String accessTesticus;
   
       @Access( AccessType.PROPERTY )
       @Column( name = "accessTesticus" )
       public String getAccessTesticus()
       {
           return accessTesticulus;
       }
   }
   ```
   
#### Entity
###### Условия
 - Класс сущности должен быть помечен @Entity и иметь @Id(в случае простого PK).
 - Иметь public или protected конструктор без аргументов.
 - Быть классом(даже абстрактным), перечисления и интерфейсы не могут энтитями.
 - Быть классом верхнего уровня(не вложенным или внутренним).
 - Финальные классы не могут быть энтитями.
 
#### Айдишники
 - `@EmbeddedId` лучше использовать, чем `@IdClass`. C `EmbeddedId`  нужно прописывать поля только 1 раз, т.е Вероятность ошибки меньше.
 Но `@IdClass` удобен, если нужно использовать в качестве Id-шника класс который мы не можем менять. 
 Несчитая использования в JPQL, они дают одинаковый результат.
##### @ElementCollection
- Если храним мапу, то в созданной таблице PK будет составной. ПК главной таблицы + ключ Мапы.
 
 
 
