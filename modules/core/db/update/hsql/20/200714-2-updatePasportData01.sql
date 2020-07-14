alter table KSENA_PASPORT_DATA alter column COMPLETED_TRAINING rename to COMPLETED_TRAINING__U55740 ^
alter table KSENA_PASPORT_DATA add column RESIDENCE_PERMANENT boolean ;
alter table KSENA_PASPORT_DATA add column RESIDENCE_NUMBER varchar(255) ;
alter table KSENA_PASPORT_DATA add column RESIDENCE_END_TIME date ;
