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
);