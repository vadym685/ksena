create table KSENA_COORDINATES (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    DTYPE varchar(100),
    --
    LATITUDE double precision,
    LONGITUDE double precision,
    --
    -- from ksena_Point
    ADRESS varchar(255),
    COMENT varchar(255),
    --
    primary key (ID)
);