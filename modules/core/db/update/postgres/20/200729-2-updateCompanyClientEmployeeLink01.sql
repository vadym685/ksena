alter table KSENA_COMPANY_CLIENT_EMPLOYEE_LINK add constraint FK_KSENA_COMPANY_CLIENT_EMPLOYEE_LINK_ON_CLIENT_EMPLOYEE foreign key (CLIENT_EMPLOYEE_ID) references KSENA_CLIENT_EMPLOYEE(ID);