alter table KSENA_TASK_DOCUMENT alter column CLIENT_ID rename to CLIENT_ID__U77232 ^
drop index IDX_KSENA_TASK_DOCUMENT_ON_CLIENT ;
alter table KSENA_TASK_DOCUMENT add column CLIENT_ID varchar(36) ;
