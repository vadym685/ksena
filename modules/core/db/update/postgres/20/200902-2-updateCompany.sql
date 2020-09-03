alter table KSENA_COMPANY rename column actual_address to actual_address__u63366 ;
alter table KSENA_COMPANY rename column legal_address to legal_address__u08413 ;
alter table KSENA_COMPANY add column ACTUAL_CITY varchar(255) ;
alter table KSENA_COMPANY add column LEGAL_STREET varchar(255) ;
alter table KSENA_COMPANY add column ACTUAL_HOUSE_NUMBER varchar(255) ;
alter table KSENA_COMPANY add column LEGAL_HOUSE_NUMBER varchar(255) ;
alter table KSENA_COMPANY add column LEGAL_CITY varchar(255) ;
alter table KSENA_COMPANY add column ACTUAL_STREET varchar(255) ;
