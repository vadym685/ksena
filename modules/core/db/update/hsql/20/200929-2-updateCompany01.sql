alter table KSENA_COMPANY add constraint FK_KSENA_COMPANY_ON_COMPANY_TYPE foreign key (COMPANY_TYPE_ID) references KSENA_COMPANY_TYPE(ID);
create index IDX_KSENA_COMPANY_ON_COMPANY_TYPE on KSENA_COMPANY (COMPANY_TYPE_ID);
