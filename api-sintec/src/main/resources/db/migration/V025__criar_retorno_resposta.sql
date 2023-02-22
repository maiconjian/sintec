create table SERV_RETORNO_RESPOSTA (
   ID                   bigint              identity,
   ID_SERVICO           bigint              null,
   ID_ALTERNATIVA       bigint              null,
   CD_LATITUDE          VARCHAR(100)        null,
   CD_LONGITUDE         VARCHAR(100)        null,
   ID_PERGUNTA          bigint              null,
   DS_RESPOSTA          VARCHAR(300)        null,
   DT_EXECUCAO          DATETIME             null,
   constraint PK_SERV_RETORNO_RESPOSTA primary key (ID)
)
go

alter table SERV_RETORNO_RESPOSTA
   add constraint FK_SERV_RET_REFERENCE_CAD_SERVI foreign key (ID_SERVICO)
      references CAD_SERVICO (ID)
go

alter table SERV_RETORNO_RESPOSTA
   add constraint FK_SERV_RET_REFERENCE_CAD_ALTE foreign key (ID_ALTERNATIVA)
      references CAD_ALTERNATIVA (ID)
go

alter table SERV_RETORNO_RESPOSTA
   add constraint FK_SERV_RET_REFERENCE_CAD_PERG foreign key (ID_PERGUNTA)
      references CAD_PERGUNTA (ID)
go
