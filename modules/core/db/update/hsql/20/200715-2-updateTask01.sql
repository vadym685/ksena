alter table KSENA_TASK add constraint FK_KSENA_TASK_ON_CLIENT foreign key (CLIENT_ID) references KSENA_COMPANY(ID);
create index IDX_KSENA_TASK_ON_CLIENT on KSENA_TASK (CLIENT_ID);
