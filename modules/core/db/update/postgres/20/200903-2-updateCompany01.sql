alter table KSENA_COMPANY rename column actual_address to actual_address__u16628 ;
alter table KSENA_COMPANY rename column legal_address to legal_address__u42916 ;
alter table KSENA_COMPANY add column BILL_SEND_TYPE varchar(50) ;
alter table KSENA_COMPANY add column ACTUAL_CITY varchar(255) ;
alter table KSENA_COMPANY add column FIELD_OF_ACTIVITY_FULL varchar(255) ;
alter table KSENA_COMPANY add column LEGAL_STREET varchar(255) ;
alter table KSENA_COMPANY add column ACTUAL_HOUSE_NUMBER varchar(255) ;
alter table KSENA_COMPANY add column LEGAL_HOUSE_NUMBER varchar(255) ;
alter table KSENA_COMPANY add column LEGAL_CITY varchar(255) ;
alter table KSENA_COMPANY add column ACTUAL_STREET varchar(255) ;
alter table KSENA_COMPANY add column FIELD_OF_ACTIVITY varchar(255) ;
