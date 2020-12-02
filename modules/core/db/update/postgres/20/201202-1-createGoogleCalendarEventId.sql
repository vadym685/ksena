create table KSENA_GOOGLE_CALENDAR_EVENT_ID (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    EVENT_ID varchar(255),
    TASK_ID uuid,
    --
    primary key (ID)
);