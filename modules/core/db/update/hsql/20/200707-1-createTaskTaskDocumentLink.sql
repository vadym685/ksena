create table KSENA_TASK_TASK_DOCUMENT_LINK (
    TASK_ID varchar(36) not null,
    TASK_DOCUMENT_ID varchar(36) not null,
    primary key (TASK_ID, TASK_DOCUMENT_ID)
);
