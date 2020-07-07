create table KSENA_TASK_INVENTORY_LINK (
    INVENTORY_ID varchar(36) not null,
    TASK_ID varchar(36) not null,
    primary key (INVENTORY_ID, TASK_ID)
);
