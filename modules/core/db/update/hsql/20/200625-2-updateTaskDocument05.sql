alter table KSENA_TASK_DOCUMENT alter column EMPLOYEES_ID rename to EMPLOYEES_ID__U94571 ^
alter table KSENA_TASK_DOCUMENT drop constraint FK_KSENA_TASK_DOCUMENT_ON_EMPLOYEES ;
drop index IDX_KSENA_TASK_DOCUMENT_ON_EMPLOYEES ;
