alter table KSENA_CLEANING_POSITION rename column room_id to room_id__u55950 ;
alter table KSENA_CLEANING_POSITION drop constraint FK_KSENA_CLEANING_POSITION_ON_ROOM ;
drop index IDX_KSENA_CLEANING_POSITION_ON_ROOM ;
