alter table KSENA_CLIENT_EMPLOYEE rename column position_id to position_id__u72516 ;
drop index IDX_KSENA_CLIENT_EMPLOYEE_ON_POSITION ;
alter table KSENA_CLIENT_EMPLOYEE add column POSITION_ varchar(255) ;
