create table KSENA_TASK_CLEANING_POSITION_LINK (
    TASK_ID varchar(36) not null,
    CLEANING_POSITION_ID varchar(36) not null,
    primary key (TASK_ID, CLEANING_POSITION_ID)
);
