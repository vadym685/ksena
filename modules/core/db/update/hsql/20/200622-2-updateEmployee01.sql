alter table KSENA_EMPLOYEE add constraint FK_KSENA_EMPLOYEE_ON_TASK_DOCUMENT foreign key (TASK_DOCUMENT_ID) references KSENA_TASK_DOCUMENT(ID);
create index IDX_KSENA_EMPLOYEE_ON_TASK_DOCUMENT on KSENA_EMPLOYEE (TASK_DOCUMENT_ID);
