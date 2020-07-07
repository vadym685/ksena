create table KSENA_TASK (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TASK_DOCUMENT_ID varchar(36),
    CREATE_DATE timestamp,
    DATE_OF_COMPLETION date,
    TASK_STATUS varchar(50),
    CLIENT_ID varchar(36),
    POINT_ID varchar(36),
    --
    primary key (ID)
);