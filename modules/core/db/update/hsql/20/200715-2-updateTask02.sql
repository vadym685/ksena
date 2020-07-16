alter table KSENA_TASK alter column CLIENT_ID rename to CLIENT_ID__U16142 ^
alter table KSENA_TASK drop constraint FK_KSENA_TASK_ON_CLIENT ;
drop index IDX_KSENA_TASK_ON_CLIENT ;
alter table KSENA_TASK add column COMPANY_ID varchar(36) ;
