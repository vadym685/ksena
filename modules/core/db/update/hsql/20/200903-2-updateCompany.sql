alter table KSENA_COMPANY alter column ACTUAL_ADDRESS rename to ACTUAL_ADDRESS__U43896 ^
alter table KSENA_COMPANY alter column LEGAL_ADDRESS rename to LEGAL_ADDRESS__U13856 ^
alter table KSENA_COMPANY add column BILL_SEND_TYPE varchar(50) ;
alter table KSENA_COMPANY add column ACTUAL_CITY varchar(255) ;
alter table KSENA_COMPANY add column FIELD_OF_ACTIVITY_FULL varchar(255) ;
alter table KSENA_COMPANY add column LEGAL_STREET varchar(255) ;
alter table KSENA_COMPANY add column ACTUAL_HOUSE_NUMBER varchar(255) ;
alter table KSENA_COMPANY add column LEGAL_HOUSE_NUMBER varchar(255) ;
alter table KSENA_COMPANY add column LEGAL_CITY varchar(255) ;
alter table KSENA_COMPANY add column ACTUAL_STREET varchar(255) ;
alter table KSENA_COMPANY add column FIELD_OF_ACTIVITY varchar(255) ;
