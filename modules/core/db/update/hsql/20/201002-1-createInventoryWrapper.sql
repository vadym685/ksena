create table KSENA_INVENTORY_WRAPPER (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    INVENTORY_ID varchar(36),
    NOTE_INVENTORY varchar(255),
    QUANTITY_INVENTORY integer,
    TASK_DOCUMENTS_ID varchar(36),
    TASK_ID varchar(36),
    --
    primary key (ID)
);