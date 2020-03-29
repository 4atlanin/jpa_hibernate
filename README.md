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
 
 PS. Можно запускать свои скрипты(`function.sql`) на старте примерно c таким конфигом:
 ```
 spring:
   jpa:
     hibernate.ddl-auto: update
  datasource:
     initialization-mode: always
     data:
       - 'classpath:sql/function.sql'
 ```
 
 ##### Аттрибуты
 - @Temporal устарела. Применима только к старм Date.
 - @Transient 
 - На геттеры можно навешивать
 ###### Access types
  - По умолчанию, JPA смотрит на поля, а не геттеры.(Аннотации почитать, если их нету то прост поля)
  - @AccessType может быть над классом, полем или геттером. Над классом самый низкий приоритет.
  - В такой комбинации будет работать геттер.(Те то что над геттером - важнее)
   ```
   @Entity
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
    Он нужен для создания сущности при мапинге из результата селекта в Entity;
 - Быть классом(даже абстрактным), перечисления и интерфейсы не могут энтитями.
 - Быть классом верхнего уровня(не вложенным или внутренним).
 - Финальные классы не могут быть энтитями.
 
#### Айдишники
 - `@EmbeddedId` лучше использовать, чем `@IdClass`. C `EmbeddedId`  нужно прописывать поля только 1 раз, т.е Вероятность ошибки меньше.
 Но `@IdClass` удобен, если нужно использовать в качестве Id-шника класс который мы не можем менять. 
 Несчитая использования в JPQL, они дают одинаковый результат.
##### @ElementCollection
 - Если храним мапу, то в созданной таблице PK будет составной. ПК главной таблицы + ключ Мапы.
##### Embeddable
 - Embeddable сущности можно сувать одна в одну,т.е. полем в Embeddable может быть другая Embeddable
 - В книжке(Изучаем JavaEE 7, стр 189) пишут, что для Embedded сущностей лучше явно задавать тип доступа(@Access), чтобы внутри Embedded класса 
  всегда был тот, который нам нужен(например, если в наружном классе кто-то захочет его переопределить). Но я хз, как по мне лучше его не трогать совсем. 
### Связи
 - `@JoinColumn` используется для настройки столбца FK
 - Если `fetchType` стоит LAZY, и мне нужен только Id связи, LazyInitializationException не бросится, т.к. не нужен запрос в базу. 
     Id связи уже есть внутри владеемой сущности.
 - `@JoinTable` **висит на cтороне владельца!!!**. 
###### OrderBy
 -  `@OrderBy("name ASC")` - jpa добавит `order by locations1_.lob_name asc` в реквест и в коллекции будут лежать записи посортированные по этому признаку.
    Можно указывать и филды и колонки бд. Можно указывать несколько колонок - `@OrderBy("name DESC, id ASC")`.
 - `@OrderBy` не рабтает с `@ElementCollection`
 - `@OrderColumn` применяется только над  `@OneToMany`, `@ManyToMany` or `@ElementCollection`. Самое первое значение = 0.
 - Если применить `@OrderBy` и `@OrderColumn` одновременно, то `@OrderBy` работать не будет.(из доки для аннотации, не тестил)
 - При использовании `@OrderColumn` записи будут отсортированы в порядке вставки. (Выглядит бесполезной хренью, или я не понял глубины мысли, 
 разве что, посортировал перед вставкой и сохраняешь в нужном тебе порядке, но опять же это не выглядит обратно совместимым...).
#### Однонаправленные
 - В однонаправленной владелец связи это тот, у кого лежит фк.  
##### OneToOne
##### OneToMany
 - по умолчанию, @OneToMany создаёт таблицу связи, как в ManyToMany.
#### Двунаправленные
 - `mappedBy` находится на НЕ владеющей стороне связи.
 - `mappedBy` используется только в двунаправленной связи.
 - `mappedBy` не может находится на обеих концах двунаправленной связи.
 - Если в двунаправленной связи нету `mappedBy`, тоже как-то криво, Это будут 2 однонаправленных.
##### OneToOne
 - В двунаправленной OneToOne `mappedBy` не создаёт доп колонки в таблице на которой он провисан. Он просто указывает, что аттрибут связи находится 
   в другой сущности. 
##### @OneToMany
 - `mappedBy` не может находится в аннотации `@ManyToOne`.
##### @ManyToMany
 - нужно явно указывать кто кем владеет с помощью аттрибута `mappedBy`.
## Наследование
#### Single Table
-  Если не использовать никаких доп аннотаций, будет использоваться стратегия `InheritanceType.SINGLE_TABLE`. Нужно прост унаследловать нужные энтити от базовой.
- `InheritanceType.SINGLE_TABLE` создаст таблицуб в которой будут колонки для всех полей, всех классов иерархии + колонка дискриминатора(по дефолту с именем DTYPE, строкового типа и хранящая имя энтити).
- `@DiscriminatorColumn` настраивает колонку дискриминатора.
- `@DiscriminatorValue` настраивает значение, по которому будут различаться Энтити в таблице.
###### Недостатки
1. Новые сущности - новые колонки в таблицу, и может понадобиться миграция для старых, перестройка индексов. 
2. Столбцы дочерних сущностей должны допускать null значения.
#### Joined
- Нужно использовать `@Inheritance`, чтобы поменять стратегию наследования.
- На каждый класс иерархии будет своя таблица. Общие аттрибуты будут в таблице базовой сущности. Аттрибуты дочерних сущностей в своих собственных.
######  Недостатки
1. Чем глубже иерархия, тем тем больще таблиц будет джойниться, что снижает производительность.
2. Тоже самое для запросов, которые захватывают всю иерархию классов, производительнось тем ниче, чем иерархия шире.
#### Table Per Class
- Нужно использовать `@Inheritance`, чтобы поменять стратегию наследования.
- На каждый класс иерархии будет своя таблица. Каждая таблица будет содержать аттрибуты базовой(-ых) сущности(-ей) + аттрибуты самой сущности.
###### Достатки
- нету джойнов при запросе к конкретной сущности.
######  Недостатки
1. Чем глубже иерархия, тем тем больще таблиц будет джойниться, что снижает производительность.
2. Тоже самое для запросов, которые захватывают всю иерархию классов, производительнось тем ниче, чем иерархия шире.
3. JPA 2.1 не требует поддержки этой стратегии.
