create table KSENA_TASK_DOCUMENT_INVENTORY_LINK (
    INVENTORY_ID varchar(36) not null,
    TASK_DOCUMENT_ID varchar(36) not null,
    primary key (INVENTORY_ID, TASK_DOCUMENT_ID)
);
