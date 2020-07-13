create table KSENA_EMPLOYEE_FILE_DESCRIPTOR_LINK (
    EMPLOYEE_ID varchar(36) not null,
    FILE_DESCRIPTOR_ID varchar(36) not null,
    primary key (EMPLOYEE_ID, FILE_DESCRIPTOR_ID)
);
