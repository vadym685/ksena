alter table KSENA_CLIENT_EMPLOYEE add constraint FK_KSENA_CLIENT_EMPLOYEE_ON_POSITION foreign key (POSITION_ID) references KSENA_POSITION(ID);
create index IDX_KSENA_CLIENT_EMPLOYEE_ON_POSITION on KSENA_CLIENT_EMPLOYEE (POSITION_ID);