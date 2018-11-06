create table PARTY
(
  ID         INTEGER not null
    primary key,
  NAME       VARCHAR(255),
  ADMINTOKEN VARCHAR(10),
  CREATED_AT TIMESTAMP,
  UPDATED_AT INTEGER
);

create table SONG
(
  ID       INTEGER      not null
    primary key,
  URL      VARCHAR(255) not null,
  PARTY_ID INTEGER
    constraint SONG_PARTY
    references PARTY,
  VOTES    INTEGER default 0
);