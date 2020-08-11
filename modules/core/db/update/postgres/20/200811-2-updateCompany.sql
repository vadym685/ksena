alter table KSENA_COMPANY rename column contact_phone to contact_phone__u99997 ;
alter table KSENA_COMPANY add column COMPANY_TYPE_ID uuid ;
alter table KSENA_COMPANY add column CONTACT_PHONE varchar(255) ;
