create table KSENA_TASK_DOCUMENT_DAY_INTERVAL_LINK (
    DAY_INTERVAL_ID varchar(36) not null,
    TASK_DOCUMENT_ID varchar(36) not null,
    primary key (DAY_INTERVAL_ID, TASK_DOCUMENT_ID)
);
