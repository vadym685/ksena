alter table KSENA_TASK_DOCUMENT alter column CLIENT_ID rename to CLIENT_ID__U04985 ^
alter table KSENA_TASK_DOCUMENT drop constraint FK_KSENA_TASK_DOCUMENT_ON_CLIENT ;
alter table KSENA_TASK_DOCUMENT add column COMPANY_ID varchar(36) ;
