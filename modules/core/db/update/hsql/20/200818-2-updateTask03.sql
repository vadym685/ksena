alter table KSENA_TASK add constraint FK_KSENA_TASK_ON_POINT foreign key (POINT_ID) references KSENA_COORDINATES(ID);
create index IDX_KSENA_TASK_ON_POINT on KSENA_TASK (POINT_ID);
