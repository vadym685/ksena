create table KSENA_SERVER_CONSTANTS (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    GOOGLE_URL varchar(255),
    GOOGLE_TOKEN varchar(255),
    --
    primary key (ID)
);