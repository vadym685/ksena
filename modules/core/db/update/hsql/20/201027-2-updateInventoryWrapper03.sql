alter table KSENA_INVENTORY_WRAPPER add constraint FK_KSENA_INVENTORY_WRAPPER_ON_TASK foreign key (TASK_ID) references KSENA_TASK(ID);
create index IDX_KSENA_INVENTORY_WRAPPER_ON_TASK on KSENA_INVENTORY_WRAPPER (TASK_ID);
