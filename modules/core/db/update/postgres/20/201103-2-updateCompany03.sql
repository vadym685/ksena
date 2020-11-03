alter table KSENA_COMPANY rename column actual_index to actual_index__u33304 ;
alter table KSENA_COMPANY rename column legal_index to legal_index__u71416 ;
alter table KSENA_COMPANY rename column legal_house_number to legal_house_number__u47475 ;
alter table KSENA_COMPANY rename column actual_house_number to actual_house_number__u67932 ;
alter table KSENA_COMPANY add column TEMP_COMPANY_TYPE varchar(255) ;
alter table KSENA_COMPANY add column LEGAL_INDEX varchar(255) ;
alter table KSENA_COMPANY add column ACTUAL_INDEX varchar(255) ;
