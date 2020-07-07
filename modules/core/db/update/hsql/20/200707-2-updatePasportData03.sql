alter table KSENA_PASPORT_DATA alter column POINT_ID rename to POINT_ID__U57962 ^
alter table KSENA_PASPORT_DATA drop constraint FK_KSENA_PASPORT_DATA_ON_POINT ;
drop index IDX_KSENA_PASPORT_DATA_ON_POINT ;
alter table KSENA_PASPORT_DATA alter column PASPORT_NUMBER rename to PASPORT_NUMBER__U58377 ^
alter table KSENA_PASPORT_DATA alter column SERIES rename to SERIES__U12130 ^
alter table KSENA_PASPORT_DATA add column PASPORT_NUMBER varchar(2) ;
