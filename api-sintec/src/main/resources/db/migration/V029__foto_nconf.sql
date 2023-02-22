create table FOTO_NCONF (
   ID                   BIGINT               identity,
   ID_NCONF             BIGINT               null,
   ID_SERV_RETORNO_FOTO BIGINT               null,
   constraint PK_FOTO_NCONF primary key (ID)
)
go

alter table FOTO_NCONF
   add constraint FK_FOTO_NCO_REFERENCE_SERV_RET foreign key (ID_SERV_RETORNO_FOTO)
      references SERV_RETORNO_FOTO (ID)
go

alter table FOTO_NCONF
   add constraint FK_FOTO_NCO_REFERENCE_CAD_RACP foreign key (ID_NCONF)
      references CAD_RACP_NCONF (ID)
go
