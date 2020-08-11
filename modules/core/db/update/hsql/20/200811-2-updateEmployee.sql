alter table KSENA_EMPLOYEE alter column IMAGE_FILE rename to IMAGE_FILE__U71484 ^
alter table KSENA_EMPLOYEE drop constraint FK_KSENA_EMPLOYEE_ON_IMAGE_FILE ;
drop index IDX_KSENA_EMPLOYEE_ON_IMAGE_FILE ;
alter table KSENA_EMPLOYEE alter column PHONE_NUMBER rename to PHONE_NUMBER__U11131 ^
alter table KSENA_EMPLOYEE add column IMAGE_FILE_ID varchar(36) ;
alter table KSENA_EMPLOYEE add column PHONE_NUMBER varchar(255) ;
