alter table KSENA_COORDINATES rename column coment to coment__u43436 ;
alter table KSENA_COORDINATES add column OBJECT_ACCESS varchar(255) ;
alter table KSENA_COORDINATES add column IS_CLEANING_BOOK boolean ;
alter table KSENA_COORDINATES add column POINT_AREA double precision ;
alter table KSENA_COORDINATES add column COMMENT varchar(255) ;
alter table KSENA_COORDINATES add column GET_TO_OBJECT varchar(255) ;
