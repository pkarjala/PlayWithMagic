# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table magician (
  id                        bigint not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  stage_name                varchar(255),
  location                  varchar(255),
  biography                 varchar(255),
  interests                 varchar(255),
  influences                varchar(255),
  experience_level          varchar(255),
  magician_type_id          bigint,
  year_started              integer,
  organizations             varchar(255),
  website                   varchar(255),
  email                     varchar(255),
  facebook                  varchar(255),
  twitter                   varchar(255),
  linked_in                 varchar(255),
  google_plus               varchar(255),
  flickr                    varchar(255),
  instagram                 varchar(255),
  constraint pk_magician primary key (id))
;

create table magician_type (
  id                        bigint not null,
  name                      varchar(255),
  description               varchar(255),
  display_order             integer,
  constraint pk_magician_type primary key (id))
;

create sequence magician_seq;

create sequence magician_type_seq;

alter table magician add constraint fk_magician_magicianType_1 foreign key (magician_type_id) references magician_type (id) on delete restrict on update restrict;
create index ix_magician_magicianType_1 on magician (magician_type_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists magician;

drop table if exists magician_type;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists magician_seq;

drop sequence if exists magician_type_seq;

