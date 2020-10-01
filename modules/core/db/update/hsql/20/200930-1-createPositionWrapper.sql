create table KSENA_POSITION_WRAPPER (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    POSITION_ID varchar(36),
    PRIORITY_CLEANING_POSITION integer,
    NOTE_CLEANING_POSITION varchar(255),
    --
    primary key (ID)
);