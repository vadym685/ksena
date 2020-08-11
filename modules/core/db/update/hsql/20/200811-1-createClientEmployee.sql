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
);