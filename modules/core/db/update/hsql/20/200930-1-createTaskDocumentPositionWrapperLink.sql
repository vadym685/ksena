create table KSENA_TASK_DOCUMENT_POSITION_WRAPPER_LINK (
    POSITION_WRAPPER_ID varchar(36) not null,
    TASK_DOCUMENT_ID varchar(36) not null,
    primary key (POSITION_WRAPPER_ID, TASK_DOCUMENT_ID)
);
