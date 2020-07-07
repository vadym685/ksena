create table KSENA_TASK_EMPLOYEE_LINK (
    EMPLOYEE_ID varchar(36) not null,
    TASK_ID varchar(36) not null,
    primary key (EMPLOYEE_ID, TASK_ID)
);
