create table KSENA_CLIENT_EMPLOYEE_POINT_LINK (
    POINT_ID varchar(36) not null,
    CLIENT_EMPLOYEE_ID varchar(36) not null,
    primary key (POINT_ID, CLIENT_EMPLOYEE_ID)
);
