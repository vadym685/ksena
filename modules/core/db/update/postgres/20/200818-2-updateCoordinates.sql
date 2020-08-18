alter table KSENA_COORDINATES rename column adress to adress__u42857 ;
alter table KSENA_COORDINATES add column STREET varchar(255) ;
alter table KSENA_COORDINATES add column POSTCODE varchar(255) ;
alter table KSENA_COORDINATES add column HOUSE_NUMBER varchar(255) ;
alter table KSENA_COORDINATES add column CITY varchar(255) ;
