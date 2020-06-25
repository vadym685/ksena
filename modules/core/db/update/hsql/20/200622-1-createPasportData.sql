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
    -- from ksena_Client
    POINT_ID varchar(36),
    --
    -- from ksena_Employee
    EMPLOYEE_TYPE varchar(50),
    --
    primary key (ID)
);