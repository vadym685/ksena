alter table KSENA_PASPORT_DATA alter column TASK_DOCUMENT_ID rename to TASK_DOCUMENT_ID__U24694 ^
alter table KSENA_PASPORT_DATA drop constraint FK_KSENA_PASPORT_DATA_ON_TASK_DOCUMENT ;
drop index IDX_KSENA_PASPORT_DATA_ON_TASK_DOCUMENT ;
