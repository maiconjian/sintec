create table CAD_RACP_NCONF (
   ID                   BIGINT               identity,
   ID_RACP              BIGINT               null,
   OBS_ACAO             text                 null,
   DT_ATUALIZACAO       DATETIME             null,
   ST_ATIVO             TINYINT              null,
   ID_USUARIO           BIGINT               null,
   ID_RETORNO_RESPOSTA  BIGINT               null,
   constraint PK_CAD_RACP_NCONF primary key (ID)
)
go

alter table CAD_RACP_NCONF
   add constraint FK_CAD_RACP_REFERENCE_SERV_RET foreign key (ID_RETORNO_RESPOSTA)
      references SERV_RETORNO_RESPOSTA (ID)
go

alter table CAD_RACP_NCONF
   add constraint FK_CAD_RACP_REFERENCE_SYS_USUA foreign key (ID_USUARIO)
      references SYS_USUARIO (ID)
go

alter table CAD_RACP_NCONF
   add constraint FK_CAD_RACP_REFERENCE_CAD_RACP foreign key (ID_RACP)
      references CAD_RACP (ID)
go