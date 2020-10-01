create table KSENA_INVENTORY_WRAPPER (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    INVENTORY_ID uuid,
    NOTE_INVENTORY varchar(255),
    QUANTITY_INVENTORY integer,
    TASK_DOCUMENT_ID uuid,
    --
    primary key (ID)
);