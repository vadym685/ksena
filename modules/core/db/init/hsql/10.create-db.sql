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
    PASPORT_NUMBER varchar(2),
    AUTHORITY varchar(255),
    DATE_OF_ISSUE date,
    INDIVIDUAL_TAXPAYER_NUMBER varchar(255),
    --
    -- from ksena_Employee
    EMPLOYEE_TYPE varchar(50),
    COMPLETED_TRAINING boolean,
    IS_ACTIVE boolean,
    MOBILE_PHONE_ID varchar(36),
    DATE_OF_EMPLOYMENT date,
    DATE_OF_DISMISSAL date,
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
    DOC_NUMBER varchar(255),
    CREATE_DATE date,
    DATE_OF_COMPLETION date,
    COST_PER_HOUR double precision,
    TYPE_OF_COST_FORMATION varchar(50),
    IS_ACTIVE boolean,
    TASK_TYPE varchar(50),
    POINT_ID varchar(36),
    COMPANY_ID varchar(36),
    TYPE_OF_PERIODICITY varchar(50),
    INTERVAL integer,
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
-- begin KSENA_CLEANING_POSITION
create table KSENA_CLEANING_POSITION (
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
    NEED_TIME double precision,
    PRICE double precision,
    --
    primary key (ID)
)^
-- end KSENA_CLEANING_POSITION
-- begin KSENA_EXPENDABLE_MATERIAL
create table KSENA_EXPENDABLE_MATERIAL (
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
    STANDART_AMOUNT double precision,
    --
    primary key (ID)
)^
-- end KSENA_EXPENDABLE_MATERIAL
-- begin KSENA_CLEANING_POSITION_EXPENDABLE_MATERIAL_LINK
create table KSENA_CLEANING_POSITION_EXPENDABLE_MATERIAL_LINK (
    EXPENDABLE_MATERIAL_ID varchar(36) not null,
    CLEANING_POSITION_ID varchar(36) not null,
    primary key (EXPENDABLE_MATERIAL_ID, CLEANING_POSITION_ID)
)^
-- end KSENA_CLEANING_POSITION_EXPENDABLE_MATERIAL_LINK
-- begin KSENA_TASK_DOCUMENT_CLEANING_POSITION_LINK
create table KSENA_TASK_DOCUMENT_CLEANING_POSITION_LINK (
    TASK_DOCUMENT_ID varchar(36) not null,
    CLEANING_POSITION_ID varchar(36) not null,
    primary key (TASK_DOCUMENT_ID, CLEANING_POSITION_ID)
)^
-- end KSENA_TASK_DOCUMENT_CLEANING_POSITION_LINK
-- begin KSENA_MOBILE_PHONE
create table KSENA_MOBILE_PHONE (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    EMEI varchar(255),
    ANDROID_VERSION varchar(255),
    PHONE_NAME varchar(255),
    EMPLOYEE_ID varchar(36),
    --
    primary key (ID)
)^
-- end KSENA_MOBILE_PHONE
-- begin KSENA_DAY_INTERVAL
create table KSENA_DAY_INTERVAL (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NUMBER_DAY integer,
    NAME_DAY varchar(50),
    --
    primary key (ID)
)^
-- end KSENA_DAY_INTERVAL
-- begin KSENA_TASK_DOCUMENT_DAY_INTERVAL_LINK
create table KSENA_TASK_DOCUMENT_DAY_INTERVAL_LINK (
    DAY_INTERVAL_ID varchar(36) not null,
    TASK_DOCUMENT_ID varchar(36) not null,
    primary key (DAY_INTERVAL_ID, TASK_DOCUMENT_ID)
)^
-- end KSENA_TASK_DOCUMENT_DAY_INTERVAL_LINK
-- begin KSENA_TASK
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
    DATE_OF_COMPLETION date,
    TASK_STATUS varchar(50),
    POINT_ID varchar(36),
    --
    primary key (ID)
)^
-- end KSENA_TASK

-- begin KSENA_TASK_EMPLOYEE_LINK
create table KSENA_TASK_EMPLOYEE_LINK (
    EMPLOYEE_ID varchar(36) not null,
    TASK_ID varchar(36) not null,
    primary key (EMPLOYEE_ID, TASK_ID)
)^
-- end KSENA_TASK_EMPLOYEE_LINK
-- begin KSENA_TASK_INVENTORY_LINK
create table KSENA_TASK_INVENTORY_LINK (
    INVENTORY_ID varchar(36) not null,
    TASK_ID varchar(36) not null,
    primary key (INVENTORY_ID, TASK_ID)
)^
-- end KSENA_TASK_INVENTORY_LINK
-- begin KSENA_TASK_CLEANING_POSITION_LINK
create table KSENA_TASK_CLEANING_POSITION_LINK (
    TASK_ID varchar(36) not null,
    CLEANING_POSITION_ID varchar(36) not null,
    primary key (TASK_ID, CLEANING_POSITION_ID)
)^
-- end KSENA_TASK_CLEANING_POSITION_LINK
-- begin KSENA_COMPANY
create table KSENA_COMPANY (
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
    FULL_NAME varchar(255),
    LEGAL_ADDRESS varchar(255),
    ACTUAL_ADDRESS varchar(255),
    EMAIL varchar(255),
    CONTACT_PHONE integer,
    INDIVIDUAL_TAXPAYER_NUMBER varchar(255),
    --
    primary key (ID)
)^
-- end KSENA_COMPANY
-- begin KSENA_COMPANY_CLIENT_EMPLOYEE_LINK
create table KSENA_COMPANY_CLIENT_EMPLOYEE_LINK (
    CLIENT_EMPLOYEE_ID varchar(36) not null,
    COMPANY_ID varchar(36) not null,
    primary key (CLIENT_EMPLOYEE_ID, COMPANY_ID)
)^
-- end KSENA_COMPANY_CLIENT_EMPLOYEE_LINK
