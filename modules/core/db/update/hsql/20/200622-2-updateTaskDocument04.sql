alter table KSENA_TASK_DOCUMENT add constraint FK_KSENA_TASK_DOCUMENT_ON_POINT foreign key (POINT_ID) references KSENA_COORDINATES(ID);
create index IDX_KSENA_TASK_DOCUMENT_ON_POINT on KSENA_TASK_DOCUMENT (POINT_ID);
