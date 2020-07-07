create table KSENA_COMPANY_CLIENT_EMPLOYEE_LINK (
    CLIENT_EMPLOYEE_ID varchar(36) not null,
    COMPANY_ID varchar(36) not null,
    primary key (CLIENT_EMPLOYEE_ID, COMPANY_ID)
);
