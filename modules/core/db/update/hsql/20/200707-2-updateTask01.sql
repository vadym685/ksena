alter table KSENA_TASK alter column CLIENT_ID rename to CLIENT_ID__U95379 ^
alter table KSENA_TASK drop constraint FK_KSENA_TASK_ON_CLIENT ;
drop index IDX_KSENA_TASK_ON_CLIENT ;
