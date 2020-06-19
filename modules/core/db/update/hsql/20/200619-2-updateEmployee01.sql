alter table KSENA_EMPLOYEE add column AUTHORITY varchar(255) ;
alter table KSENA_EMPLOYEE add column DATEOF_ISSUE date ;
alter table KSENA_EMPLOYEE add column INDIVIDUAL_TAXPAYER_NUMBER varchar(255) ;
alter table KSENA_EMPLOYEE add column EMPLOYEE_TYPE varchar(50) ;
alter table KSENA_EMPLOYEE add column PHONE_NUMBER integer ;
alter table KSENA_EMPLOYEE add column SERIES varchar(255) ;
alter table KSENA_EMPLOYEE add column PASPORT_NUMBER integer ;
update KSENA_EMPLOYEE set FULL_NAME = '' where FULL_NAME is null ;
alter table KSENA_EMPLOYEE alter column FULL_NAME set not null ;
