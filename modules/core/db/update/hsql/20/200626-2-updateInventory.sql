alter table KSENA_INVENTORY alter column TASK_DOCUMENT_ID rename to TASK_DOCUMENT_ID__U62946 ^
alter table KSENA_INVENTORY drop constraint FK_KSENA_INVENTORY_ON_TASK_DOCUMENT ;
drop index IDX_KSENA_INVENTORY_ON_TASK_DOCUMENT ;