create table SERV_RETORNO (
   ID                   bigint              identity,
   ID_USUARIO           bigint              null,
   ID_SERVICO           bigint              null,
   ID_OCORRENCIA        bigint              null,
   DT_EXECUCAO          DATETIME             null,
   CD_LATITUDE          VARCHAR(100)         null,
   CD_LONGITUDE         VARCHAR(100)         null,
   NM_MODELO_APARELHO   VARCHAR(100)         null,
   NM_MARCA_APARELHO    VARCHAR(100)         null,
   FLAG_RACP            TINYINT              null,
   ST_ATIVO             TINYINT              null,
   constraint PK_SERV_RETORNO primary key (ID)
)
go

alter table SERV_RETORNO
   add constraint FK_SERV_RET_REFERENCE_CAD_SERV foreign key (ID_SERVICO)
      references CAD_SERVICO (ID)
go

alter table SERV_RETORNO
   add constraint FK_SERV_RET_REFERENCE_SYS_USUA foreign key (ID_USUARIO)
      references SYS_USUARIO (ID)
go

alter table SERV_RETORNO
   add constraint FK_SERV_RET_REFERENCE_CAD_OCOR foreign key (ID_OCORRENCIA)
      references CAD_OCORRENCIA (ID)
go
