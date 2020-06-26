-- begin KSENA_PASPORT_DATA
create table KSENA_PASPORT_DATA (
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
    FULL_NAME varchar(255),
    PHONE_NUMBER integer,
    SERIES varchar(2),
    PASPORT_NUMBER integer,
    AUTHORITY varchar(255),
    DATE_OF_ISSUE date,
    INDIVIDUAL_TAXPAYER_NUMBER varchar(255),
    --
    -- from ksena_Employee
    EMPLOYEE_TYPE varchar(50),
    --
    -- from ksena_Client
    POINT_ID varchar(36),
    --
    primary key (ID)
)^
-- end KSENA_PASPORT_DATA
-- begin KSENA_COORDINATES
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
    NAME varchar(255),
    ADRESS varchar(255),
    COMENT varchar(255),
    --
    primary key (ID)
)^
-- end KSENA_COORDINATES
-- begin KSENA_TASK_DOCUMENT
create table KSENA_TASK_DOCUMENT (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CREATE_DATE date,
    IS_ACTIVE boolean,
    CLIENT_ID varchar(36),
    TASK_TYPE varchar(50),
    POINT_ID varchar(36),
    --
    primary key (ID)
)^
-- end KSENA_TASK_DOCUMENT
-- begin KSENA_INVENTORY
create table KSENA_INVENTORY (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    DESCRIPTION varchar(255),
    --
    primary key (ID)
)^
-- end KSENA_INVENTORY
-- begin KSENA_TASK_DOCUMENT_EMPLOYEE_LINK
create table KSENA_TASK_DOCUMENT_EMPLOYEE_LINK (
    EMPLOYEE_ID varchar(36) not null,
    TASK_DOCUMENT_ID varchar(36) not null,
    primary key (EMPLOYEE_ID, TASK_DOCUMENT_ID)
)^
-- end KSENA_TASK_DOCUMENT_EMPLOYEE_LINK
-- begin KSENA_TASK_DOCUMENT_INVENTORY_LINK
create table KSENA_TASK_DOCUMENT_INVENTORY_LINK (
    INVENTORY_ID varchar(36) not null,
    TASK_DOCUMENT_ID varchar(36) not null,
    primary key (INVENTORY_ID, TASK_DOCUMENT_ID)
)^
-- end KSENA_TASK_DOCUMENT_INVENTORY_LINK
