-- begin KSENA_CLIENT
create table KSENA_CLIENT (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    FULL_NAME varchar(255) not null,
    PHONE_NUMBER integer not null,
    SERIES varchar(3),
    PASPORT_NUMBER integer,
    AUTHORITY varchar(255),
    DATE_OF_ISSUE date,
    INDIVIDUAL_TAXPAYER_NUMBER varchar(255),
    --
    primary key (ID)
)^
-- end KSENA_CLIENT
-- begin KSENA_EMPLOYEE
create table KSENA_EMPLOYEE (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    FULL_NAME varchar(255) not null,
    PHONE_NUMBER integer,
    SERIES varchar(255),
    PASPORT_NUMBER integer,
    AUTHORITY varchar(255),
    DATEOF_ISSUE date,
    INDIVIDUAL_TAXPAYER_NUMBER varchar(255),
    EMPLOYEE_TYPE varchar(50),
    --
    primary key (ID)
)^
-- end KSENA_EMPLOYEE
