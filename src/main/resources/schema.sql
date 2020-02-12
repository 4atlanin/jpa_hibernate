create table hibernate_sequence
(
    next_val      bigint not null
);

insert into hibernate_sequence(next_val) values (0);

CREATE TABLE table_sample
(
    ts_id BIGINT NOT NULL,
    field varchar(100),
    PRIMARY KEY (ts_id)
);

CREATE TABLE secondary_table_sample
(
    sts_id     BIGINT NOT NULL,
    sts_street varchar(100),
    PRIMARY KEY (sts_id)
);

CREATE TABLE city
(
    sts_id BIGINT NOT NULL,
    city    varchar(100),
    PRIMARY KEY (sts_id)
);


CREATE TABLE country
(
    sts_id  BIGINT NOT NULL,
    country varchar(100),
    PRIMARY KEY (sts_id)
);