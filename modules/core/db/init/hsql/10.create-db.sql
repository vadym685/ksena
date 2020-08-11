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
    COMPANY_ID varchar(36),
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
    SERIAL_NUMBER varchar(255),
    COLOUR varchar(255),
    COMMISSIONING_DATE date,
    DECOMMISSIONING_DATE date,
    AVAILABLE_FOR_USE boolean,
    REASON_FOR_DECOMMISSIONING varchar(255),
    --
    primary key (ID)
)^
-- end KSENA_INVENTORY

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
    NEED_TIME time,
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
    PRICE double precision,
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
    COMPANY_ID varchar(36),
    COST double precision,
    TASK_TIME_PLANE time,
    TASK_TIME_FACTUAL time,
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
    COMPANY_TYPE_ID varchar(36),
    FULL_NAME varchar(255),
    LEGAL_ADDRESS varchar(255),
    ACTUAL_ADDRESS varchar(255),
    EMAIL varchar(255),
    CONTACT_PHONE varchar(255),
    INDIVIDUAL_TAXPAYER_NUMBER varchar(255),
    --
    primary key (ID)
)^
-- end KSENA_COMPANY
-- begin KSENA_BREAKAGE
create table KSENA_BREAKAGE (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    DESCRIPTION varchar(255),
    BREAKDOWN_DATE date,
    FIX_DATE date,
    INVENTORY_ID varchar(36),
    --
    primary key (ID)
)^
-- end KSENA_BREAKAGE
-- begin KSENA_CLIENT_EMPLOYEE
create table KSENA_CLIENT_EMPLOYEE (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    FULL_NAME varchar(255),
    EMAIL varchar(255),
    PHONE_NUMBER varchar(255),
    PASPORT_NUMBER varchar(2),
    AUTHORITY varchar(255),
    DATE_OF_ISSUE date,
    INDIVIDUAL_TAXPAYER_NUMBER varchar(255),
    SEX varchar(50),
    --
    COMPANIES_ID varchar(36),
    POSITION_ varchar(255),
    --
    primary key (ID)
)^
-- end KSENA_CLIENT_EMPLOYEE
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
    FULL_NAME varchar(255),
    EMAIL varchar(255),
    PHONE_NUMBER varchar(255),
    PASPORT_NUMBER varchar(2),
    AUTHORITY varchar(255),
    DATE_OF_ISSUE date,
    INDIVIDUAL_TAXPAYER_NUMBER varchar(255),
    SEX varchar(50),
    --
    EMPLOYEE_TYPE varchar(50),
    FULL_NAME_PRONUNCIATION varchar(255),
    IS_ACTIVE boolean,
    QUALIFICATION varchar(50),
    MOBILE_PHONE_ID varchar(36),
    DATE_OF_EMPLOYMENT date,
    DATE_OF_DISMISSAL date,
    IMAGE_FILE_ID varchar(36),
    COMENT varchar(255),
    PERSONAL_PHONE_NUMBER varchar(255),
    RELATIVES_PHONE_NUMBER varchar(255),
    RESIDENCE_NUMBER varchar(255),
    RESIDENCE_PERMANENT boolean,
    RESIDENCE_END_TIME date,
    RESIDENCE_ADDRESS varchar(255),
    RESIDENCE_PLACE varchar(255),
    --
    primary key (ID)
)^
-- end KSENA_EMPLOYEE
-- begin KSENA_CLIENT_EMPLOYEE_POINT_LINK
create table KSENA_CLIENT_EMPLOYEE_POINT_LINK (
    POINT_ID varchar(36) not null,
    CLIENT_EMPLOYEE_ID varchar(36) not null,
    primary key (POINT_ID, CLIENT_EMPLOYEE_ID)
)^
-- end KSENA_CLIENT_EMPLOYEE_POINT_LINK
-- begin KSENA_COMPANY_TYPE
create table KSENA_COMPANY_TYPE (
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
    --
    primary key (ID)
)^
-- end KSENA_COMPANY_TYPE
