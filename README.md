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
- если Хибер видит `import.sql` в руте класпаса, то он его выполнит после генерации схемы.
- если нужно указать много файлов, можно передать их списком в `hibernate.hbm2ddl.import_files` параметр.
- Можно расширить `AbstractAuxiliaryDatabaseObject` чтобы програмно управлять генерацией схемы.
- Больше ништяков можно прочитать в книжке, стр 248 - 249

 ##### Аттрибуты
 - @Temporal устарела. Применима только к старм Date.
 - @Transient 
 - На геттеры можно навешивать
- Аннотации бесмысленно вешать над setteraми
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
####### Dirty Checking
- от аннотации `@Id` зависит то, как будет получен доступ к полям, через методы доступа(если `@Id` над методом) или напрямую к полям(если `@Id` над полем);
- хибер сравнивает состояния объектов по значению за исключением коллекций, коллекции сравниваются по идентичности. 
- т.к. у хибера по умолчанию доступ к полям напрямую, обычно проблем не возникает, 
    но если сделать доступ через методы get (`@Access( AccessType.PROPERTY` )), то при работе с коллекциями могут быть нежданчики(см `DirtyCheckingTest`)
    например если вернуть новую коллекцию со старыми сущностями, в базу улетят якобы изменения(). C не коллекциями такого нет.
#### Entity
###### Условия
 - Класс сущности должен быть помечен `@Entity` и иметь @Id(в случае простого PK).
 - Иметь public или protected конструктор без аргументов. 
    Он нужен для создания сущности при мапинге из результата селекта в Entity;
 - Быть классом(даже абстрактным), перечисления и интерфейсы не могут энтитями.
 - Быть классом верхнего уровня(не вложенным или внутренним).
 - Финальные классы не могут быть энтитями.
 
#### Айдишники
 - `@EmbeddedId` лучше использовать, чем `@IdClass`. C `EmbeddedId`  нужно прописывать поля только 1 раз, т.е Вероятность ошибки меньше.
 Но `@IdClass` удобен, если нужно использовать в качестве Id-шника класс который мы не можем менять. 
 Несчитая использования в JPQL, они дают одинаковый результат.
- `@MapsId` возьмёт id из поля или энтити над которой он висит и засунет в PK Энтити. Может сувать также и в часть составного ПК.
- все классы использующиеся в качестве _ID_ обязаны имплементировать `Serializable`, т.к. они могут использоваться в качестве ключей в кеше второго уровня.
##### @ElementCollection
- Если храним мапу, то в созданной таблице PK будет составной. ПК главной таблицы + ключ Мапы. Тоже самое если храним мапу у которой значения это `@Embeddable`;
- А вот если использовать `@Embeddable` как ключ, то:
    - поля `@Embeddable` не могут быть _null_;
    - в `@Embeddable` должен быть переопределён _equals_ and _hashCode_;
    - не имеют смысла `@AttributOveride` и `@MapKeyColumn` над коллекцией;
- Если храним Set, то в созданной таблице PK будет составной. ПК главной таблицы + Знечение Set. Если в Set лежит `@Embeddable`, то все его поля попадут в PK
- `Collection = new ArrayList();` называется _контейнером(bag)_, делается через сурогатный ключь (используя `@CollectionId`).
Порядок не гарантирует в отличии от `List = new ArrayList();`. Посортировать с помощью `@SortComparator` или '`@SortNatural` неполучится
- Настройка таблицы для коллекций осуществляется аннотацией `@CollectionTable`.
- Если не указывается тип коллекции(_Set_ вместо _Set<String>_), то нужно сообщить эту информацию через `@ElementCollection(targetClass = String.class)`;
- Предыдущий пункт применим и к _Map_, плюс ещё нужно указать тип ключа через аннотацию `@MapKeyClass`;
- Чтобы персистить свою собственную коллекцию, нужно нужно заимплементить интерфейс `PersistentCollection`
- Вызов _clear()_ на коллеции `@ElementCollection` отправит 1 **DELETE**;
- В Колонках _Map_ для настройки value используются `@@Column`, а для key `@MapKeyColumn`.
- Если ключ в _Map_ это перечисление, нужно помечать коллекцию аннотацией `@MapKeyEnumerated`. Угадай для чего `@MapKeyTemporal`.
- Можно использовать `@ElementCollection` внутри `@Embeddable`, выглядит всё также как и из обычной энтити;
##### Embeddable
- Embeddable сущности можно сувать одна в одну,т.е. полем в Embeddable может быть другая Embeddable
- В книжке(Изучаем JavaEE 7, стр 189) пишут, что для Embedded сущностей лучше явно задавать тип доступа(@Access), чтобы внутри Embedded класса 
  всегда был тот, который нам нужен(например, если в наружном классе кто-то захочет его переопределить). Но я хз, как по мне лучше его не трогать совсем. 
- При генерации DDL Валидационные аннотации игнорируются. Баг HVAL-3.
- Если мы сохранили не null `@Embeddable`, но со всеми пустыми полями, то при получении записи из БД, `@Embeddable` поле будет null.
- Не обязательно ставить одновременно`@Embeddable` над классом и `@Embedded` над полем, важно хотябы одно.
- Можно наследовать `@Embeddable` от `@MappedSuperClass`, но при этом ссылка в Энтити всегда должна указывать на Кокретную реализацию `@Embeddable`, не `@MappedSuperClass`.
    Причина в том, что Hibernate не умеет полиморфно сохранять базовый не `@Embeddable` класс, так как нету признака различия между ними.
- Можно использовать `@ElementCollection` внутри `@Embeddable`, выглядит всё также как и из обычной энтити;
- `@Embeddable` могут определять однонаправленные связи, направленные к сущности.
### OrderBy
-  JPA `@OrderBy("name ASC")` - jpa добавит `order by locations1_.lob_name asc` в реквест и в коллекции будут лежать записи посортированные по этому признаку.
    Можно указывать и филды и колонки бд. Можно указывать несколько колонок - `@OrderBy("name DESC, id ASC")`.
- Хибернейтовский 
~~- `@OrderBy` из JPA не рабтает с `@ElementCollection`, в отличии от хибернейтовского аналога.~~  
!!!! Странное утверждени, в книге по хиберу(стр 186) пишут что работает, но только как _ASC_ и имя колонки не нужно указывать для простых типов. PS. Это всего лишь Поведение по умолчанию.
PPS. Проверил в  `testOrderWithElementCollection` и `testWithEmbeddableElementCollection`, всё ок с простыми и коллекциями, и коллекциями Embedded
- `@OrderColumn` применяется только над  `@OneToMany`, `@ManyToMany` or `@ElementCollection`. Самое первое значение = 0.
- Если применить `@OrderBy` и `@OrderColumn` одновременно, то `@OrderBy` работать не будет.(из доки для аннотации, не тестил)
- При использовании `@OrderColumn` записи будут отсортированы в порядке вставки. (Выглядит бесполезной хренью, или я не понял глубины мысли, 
    разве что, посортировал перед вставкой и сохраняешь в нужном тебе порядке, но опять же это не выглядит обратно совместимым...).
- `@OrderColumn` для каждой связи генерирет отдельную последовательность. 
- Если есть разрывы в последовальностях колонки порядка(0, 1, 3), то в _List_ запишется 4 элемента, вместо третьего будет _null_;
- При удалении записи, которая посортирована с помощью доп колонки, в базу улетит delete и, при необходисмости, несколько update, чтобы выровнять сортировочный индекс.
- Сортировка прямо в памяти производится с помощью аннотации `@SortComparator`, которая принимает класс реализующий `Comparator`.
- Или `@SortNatural`, тогда для сравнения будет вызываться `Comparable#compareTo`. Тип лежащий в коллекции должен обязательно имплементить этот интерфейс.
### Связи
 - `@JoinColumn` используется для настройки столбца FK
 - Если `fetchType` стоит LAZY, и мне нужен только Id связи, LazyInitializationException не бросится, т.к. не нужен запрос в базу. 
     Id связи уже есть внутри владеемой сущности.
 - `@JoinTable` **висит на cтороне владельца!!!**. 
- каскад _REMOVE_ заставит хибер загрузить все значения из например коллекции и к каждой применить _remove_. 
Нужно для того, чтобы энтити прошла весь жизненный цикл, темболее хибер хочет посмотреть, какие связи есть у удаляемых по каскаду энтитей.
И это не эффективно. Можно тогда написать свой квери, удаляющий сразу всех по ID. Или сделать зависимую энтитю встраиваемым компонентом `@Embeddable`.
- по спеке у `@PrimaryKeyJoinColumn` Lazy не работает с **optional = true**. Но можно обойти ограничение через модификацию байткода
- а вот Для связи по фк (@JoinColumn) всё будет ок, т.е. владелец связи имеет fk на зависимую таблицу, и точно чзнает о существовании энтити. Это позволяет создать прокси объект.
#### Однонаправленные
 - В однонаправленной владелец связи это тот, у кого лежит фк.
##### ManyToOne
- Самая простая связь;
- `@ManyToOne(optional = false)` тоже что и `@JoinColumn`  
- cascade в тут выглядит ненужным, т.к. _Many_ сторона не должна влиять на _One_ сторону.
##### OneToOne
- `@PrimaryJoinColumn` при использовании такой стратегии, владеющему объекту придётся вручную установить id(тотже что и у зависимого);
- `@OneToOne` не поддерживает Lazy при optional = false. Но можно обойти ограничение через модификацию байткода.
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
 - лучшая коллекция для связи `@OneToMany` это **bag** (_Collection<Any> a = new ArrayList<>()_). По дефолту Хибер для коллекций использует ленивую загрузку, а
Bag не хранит порядок и не проверяет уникальность, поэтому при добавлении в такую коллекцию не инициируется загрузка данных.
- если использовать Bag в двунаправленой связи как в `OneToManyBagTest` можно лениво загружать коллекцию. Даже добавление в коллекцию новой записи не тригернёт загрузку.
- Хибер игнорирует повторяющиесе энтити в коллекции;
- `@ManyToOne` находится всегда на владеющей стороне, у неё нету аттрибута _mappedBy_
- можно сделать `@ManyToOne` [владеемой стороной:](https://www.baeldung.com/hibernate-one-to-many#3-cart-as-the-owning-side)
```
    @ManyToOne
    @JoinColumn(name = "cart_id", insertable = false, updatable = false)
    private CartOIO cart;
    --------------
    @OneToMany
    @JoinColumn(name = "cart_id") // we have to duplicate the physical information
    private Set<ItemsOIO> items;
```
- Из `@Embeddded` тоже можно делать однонаправленную связь(стр 230 - 231 книги)
##### @ManyToMany
- нужно явно указывать кто кем владеет с помощью аттрибута `mappedBy`.
- сторона помеченная `mappedBy` по факту доступна только для чтения, т.к. Хибернейт при сохранении анализирует противоположную сторону;
- для связи `@ManyToMany` бесмысленны(вредны) операции `CascadeType.ALL`, `CascadeType.REMOVE` и _orphanRemoval_. Т.к. по цепочке можно снести очень много, [хорошая статья на тему](https://thorben-janssen.com/avoid-cascadetype-delete-many-assocations/)
## Наследование
- Если Энтитя наследует обычный POJO, то поля из пождика не будут персиститься.
- Чтобы персистить поля наследуемой POJO, нужно пометить поджу аннотацией `@MappedSuperClass`.
- Нельзя применять аннотации наследования к интерфейсам, кэп.
- Но можн замутить полиморфизм реализуя интерфейс.
#### Single Table
-  Если не использовать никаких доп аннотаций, будет использоваться стратегия `InheritanceType.SINGLE_TABLE`. Нужно прост унаследловать нужные энтити от базовой.
- `InheritanceType.SINGLE_TABLE` создаст таблицу в которой будут колонки для всех полей, всех классов иерархии + колонка дискриминатора(по дефолту с именем _DTYPE_, строкового типа и хранящая имя энтити).
- `@DiscriminatorColumn` настраивает колонку дискриминатора, по дефолту будет `DTYPE`.
- `@DiscriminatorValue` настраивает значение, по которому будут различаться Энтити в таблице, по дефолту будет имя класса.
###### Недостатки
1. Новые сущности - новые колонки в таблицу, и может понадобиться миграция для старых, перестройка индексов. 
2. Столбцы дочерних сущностей должны допускать null значения.
3. По дефолту(без аннотаций) используется подобие `Table Per Class`, но при полиморфном запросе будет N селектов в базу, где N - к-во энтитей в иерархии.
####  Joined
- Нужно использовать `@Inheritance`, чтобы поменять стратегию наследования.
- На каждый класс иерархии будет своя таблица. Общие аттрибуты будут в таблице базовой сущности. Аттрибуты дочерних сущностей в своих собственных.
- Колонка дискриминатора будет находится в базовой таблице.
- При поиске по базовому классу используется `LEFT JOIN`, При поиске бо расширенному классу используется `INNER JOIN`
######  Недостатки
1. Чем глубже иерархия, тем тем больще таблиц будет джойниться, что снижает производительность.
2. Тоже самое для запросов, которые захватывают всю иерархию классов, производительнось тем ниче, чем иерархия шире.
#### Table Per Class
- Нужно использовать `@Inheritance`, чтобы поменять стратегию наследования.
- На каждый класс иерархии будет своя таблица. Каждая таблица будет содержать аттрибуты базовой(-ых) сущности(-ей) + аттрибуты самой сущности.
- Не нужна колонка дискриминатора
- Если базовая Энтити абстарктный класс, то ему не нужна будет доп таблица для хранения записей.
###### Достатки
- нету джойнов при запросе к конкретной сущности.
######  Недостатки
1. Чем глубже иерархия, тем тем больще таблиц будет джойниться, что снижает производительность.
2. Тоже самое для запросов, которые захватывают всю иерархию классов, производительнось тем ниже, чем иерархия шире.
3. JPA 2.1 не требует поддержки этой стратегии.
4. Запросы к таблицам подклассов будут через UNION.
## Переопределение аттрибутов энтити
#### Переопределение в @Embedded классах
- Для переопределения аттрибутов в _@Embedded_ классе, нужно над полем встраимываемого типа вешать аннотацию `@AttributeOverride(s)`.
- Для переопределения таблицы или столбца отображения в _@Embedded_ классе, нужно над полем встраимываемого типа вешать аннотацию `@AssociationOverride`.
так нельзя поменять стратегию отображения, только её настройки.
- при вложенных `@Embedded`, можно ставить `@AttributeOverride` на самом последнем уровне и обращаться ко внутренним `@Embedded` полям через точку.
 В других местах(над классом) она работать не будет для @Embedded.
#### Переопределение аттрибутов при наследовании
- с помощью `@AttributeOverride` можно переопределять настройки колонки из базового класса 
- `@AttributeOverride` затирает любые JPA/Hibernate(но не валидационные) аннотации над переопределяемыми полями. 
## EntityManager
- Все EntityManager произведённые одной EntityManagerFactory будут иметь одиаковую конфигурацию
- Persistence Context == First Level Cache.
- Каждый EntityManager имеет свой Persistence Context.
- У Заинжекченого Спрингом EntityManager, транзакции тоже управляются спригом. Т.е. для этого EM нельзя создавать ручные транзакции.
Я про _entityManager.getTransaction().commit();_ , C _TransactionTemplate_ всё будет норм, т.к. этими транзакциями управляет спринг.
- EntityManager#persist - ложит энтитю в Persistence Context, записана она будет при коммите транзакции
- _find_ вернёт null если не найдёт запись в бд.
- _getReference_ не подаст виду, если не найдёт записи в бд. Кинет _EntityNotFoundException_ при обращении к любому не айди полю обьекта.
вызов getId на респонсе с несуществующей энтитей вернёт id который передали в метод.
- после _merge_ изменения в сущности не отслеживаются Манагером. Т.е. после _merge_, мы меняем энтитю внутри транзакции и эти изменения не попадают в бд при коммите.
#### Запросы
- с помощью оператора NEW возможно засетать результат JPQL запроса в DTO см. `JPQLTest#testNewInJPQL`
Бывают 
1. Динамические - те которые простая строка. В строку можно собирать по кускам, поэтому они и называются динамическими.
 Строку передают в `EntityManager#createQuery`
2. Именованные - неизменяемая строка. Задаются над энтитей с помощью аннотации `@NamedQuery`
- Хорошая практика добавлять имя Entity в название запроса **Entity.findAll** 
- Хорошая практика делать имена кверей константами, меньше вероятность опечаток и прочей херни
3. Criteria API
- Criteria API поддерживает все, что может сделать JPQL, но с использованием основанного на объектах синтаксиса.
4. Native запросы - могут быть именованными
5. Хранимые процедуры - могут быть именованными
#### orphanRemoval
- Для связи _OneToOne_, при установке связи в _null_ будет удалена владеемая сущность.
- orphanRemoval тоже что и каскад + удаление владеемых сущносте нпри разрыве связи в бд.
### Second Level Cache
- по дефолту включён, но т.к. нету провайдера используется `NoCachingRegionFactory` т.е. обращения к кэшу игнорятся.
- для использования нужно подрубить `hibernate-jcache` + провайдер кэша, например `ehcache`, и добавить соответствующие проперти в `application.yaml`
- hibernate  игнорирует аннотацию `@Cacheable`, совсем. Он смотрит на @Cached
- перед загрузкой Entity генерируется `LoadEevent`, `DefaultLoadEventListener` перехватывает и хэндлит его: 
     - Если искомое есть в кэше сесси и оно консистентно и не удалено, то берём оттуда;
     - Если искомое есть в кэше второго уровня, то берём из второго кэша.
     - Иначе делаем запрос в базку данных.
### Locks
#### OptimisticLock
- использовать, в случае с малым уровнем конкуренции транзакции. Блокировка на уровне приложения, т.е. в БД ничего не блокируется.
- поле с `@Version` обязательно, иначе `HibernateException: [OPTIMISTIC_FORCE_INCREMENT] not supported for non-versioned entities`
- если вручную меняем занчение колонки `@Version`, хибернейт всё равно отрабатывает корректно. Он не смотрит на изменённое значение.
- `LockModeType.OPTIMISTIC` - значение `@Version` инкрементится на 1 при каждом изменении записи в бд.
- `LockModeType.OPTIMISTIC` - cравнение версий выполняется только при модификации записи в текущей транзакции. Если мы просто прочитали, потом кто-то в соседней транзакции поменял запись, мы про это не узнаем при простом чтении
- `LockModeType.OPTIMISTIC_FORCE_INCREMENT` - версия инкрементится 1 раз после чтения, и ещё 1 раз если запись была модифицированна в этой транзакции.
- `LockModeType.OPTIMISTIC_FORCE_INCREMENT` - сравнение версий выполняется в конце транзакции. даже если энтитя не модифицировалась в ней.
 Т.е. если прочитали энтитю, а потом её в этот момент модифицировали в другой транзакции, то при закрытии первой транзакции будет `OptimisticLockException`
### Callbacks
- все @Pre- выполняются перед соответствующей операцией, но есть исключения. Если энтитя уже приатачена к контексту, то merge не вызовет `@PreUpdate` и `@PostUpdate`. Похожая ситуация и с `EntityManager#find`.
- все @Post(кроме @PostLoad) выполняются при коммите транзакции или сбросе(flush) изменений.
- `@PostLoad`  работет сразу после загрузки энтити.
- callback метод не может быть `final` или `static`.
- на callback метод можно несколько аннотации @Pre, @Post вешать.
- callback метод не должен генерировать проверяемые исключения
- для класса не можеты быть несколько одинаковых анноитаций(даже над разными методами)
- callback метод суперкласса вызывается раньше callback метода в дочернем классе.
- при каскадировании событий, вызовы callback методов тоже каскадируются.
#### EntityListeners
- Методы лисенеров вызываются раньше чем калбэки на энтити или на её суперклассе.
- лисенер и callback метод могут содержать одинаковые @Pre и @Post аннотации.
- лисенер суперкласса вызывается чем лисенер подкласса.
- `@ExcludeSuperclassListeners` используется для запрета наследования лисенеров от суперкласса. Т.е объявленные в суперклассе, действовать не будут;
- `@ExcludeDefaultListeners` отрубает дефолтные лисенеры. **TODO** Видел объявление дефолтных только в XMLе.
### Преобразование типов при сохранении
#### Basic Type
- BasicType имеется ввиду, что мапится в одну колонку. Пока не вижу причин зачем он ещё может пригодиться.
#### User Type
- По сути маппинг в несколько колонок, но опять же не актуально. Можно заранее создать @Embeddable с нужными колонками.
#### Attribute converter
- Не работает с `@Enumerated` и `@Temporal`
#### Ограничения SQL
- ограничения домена(т.е. по типу);
- ограничения столбца(NOT_NULL or custom constraints defined with `Check` keyword)
- ограничения таблицы(UNIQUE или кастомные ограничения между несколькими колонками)
- ограничения БД(FK, FK может нарушаться если стоит _ON DELETE_ or _ON UPDATE_)
### Всячина
- от аннотации `@Id` зависит то, как будет получен доступ к полям, через методы доступа(если `@Id` над методом) или напрямую к полям(если `@Id` над полем);
- JPA аннотации нельзя вешать на уровне пакета(в фале `package-info.java`). Если сильно нужно, юзайте Хибернейтовские аналоги. 
- Валидационные аннотации на энтити участвуют в генерации схемы. 
- Доступ к полям Энтити для валидации можно настраивать отдельно от остального хибернейта, через метод или напрямую к полю;
- `hydration` - преобразование `ResultSet` в массив значений.  `hydrated state` сохраняется в текущий context внутри `EntityEntry` и содержит состояние энтити на момент загрузки. Нужно для:
    - работы `dirty checking` механизма;
    - `second-level cache`, записи в котором формируются из `hydrated state`.
- `dehydration` операция обратная `hydration`, копирует состояние энтити в INSERT или UPDATE statementы.
- если поле not null, то желательно ставить и `@NotNull` аннотацию, чтобы можно было проверить вручную при необходимости.
- Аннотации бесмысленно вешать над setteraми
- `@ColumnTransformer` почти как `@Formula`, но он даёт возможность для записи. Можно использовать для шифрования/дешифрования или конвертации чего-то.
- `@Generated` помечает автогенерируемое поле и значит, что Hiber сразу после INSERT или UPDATE сделает select, чтобы узнать сгенерированное значение.
- Без или с пустой `@Enumerated` Хибер  будет сохранять ordinal значение Энама. 
- `@Converter` не работает с `@Enumerated` и `@Temporal`
- @Nationalized — позволяет явно управлять выбором кодировки и менять типы столбцов на `NCHAR`, `NVARCHAR` или `NCLOB`.
- `@Type` позволяет выбрать адаптер.  `@Type(type = "yes_no")`
- Если каскад в БД удаляет все связи, то нужно что-то придумывать, чтобы синхронизировать состояния БД и прилаге + в кэше второго уровня. 
К тому же `@PreRemove` не сработает, т.к. хибер ничего не знает про удаление каскадом в БД. Короче, Лучше не юзать каскад в БД.
- RЕсли объявить энтитю неизменяемой(`@Immutable`) nто хибер может применить оптимизации, например не проверять состояние энтити(_Dirty checking_ ?) при коммите.
- `@Check` позволяет задавать кастомные ограничения, которые применятся при автогенерации схемы.
- Через `@JoinColumn(referencedColumnName = "..."")` можно указать конкретную колонку, на которую будет указывать FK, т.е. это не обязательно PK