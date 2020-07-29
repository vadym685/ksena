alter table KSENA_MOBILE_PHONE rename column employee_id to employee_id__u04863 ;
drop index IDX_KSENA_MOBILE_PHONE_ON_EMPLOYEE ;
alter table KSENA_MOBILE_PHONE add column EMPLOYEE_ID uuid ;
