alter table KSENA_INVENTORY_WRAPPER rename column task_document_id to task_document_id__u66692 ;
alter table KSENA_INVENTORY_WRAPPER drop constraint FK_KSENA_INVENTORY_WRAPPER_ON_TASK_DOCUMENT ;
drop index IDX_KSENA_INVENTORY_WRAPPER_ON_TASK_DOCUMENT ;
alter table KSENA_INVENTORY_WRAPPER add column TASK_DOCUMENTS_ID uuid ;
