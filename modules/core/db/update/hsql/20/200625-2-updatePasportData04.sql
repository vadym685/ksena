alter table KSENA_PASPORT_DATA add constraint FK_KSENA_PASPORT_DATA_ON_TASK_DOCUMENT foreign key (TASK_DOCUMENT_ID) references KSENA_TASK_DOCUMENT(ID);
create index IDX_KSENA_PASPORT_DATA_ON_TASK_DOCUMENT on KSENA_PASPORT_DATA (TASK_DOCUMENT_ID);
