alter table KSENA_TASK add constraint FK_KSENA_TASK_ON_COMPANY foreign key (COMPANY_ID) references KSENA_COMPANY(ID);
create index IDX_KSENA_TASK_ON_COMPANY on KSENA_TASK (COMPANY_ID);
