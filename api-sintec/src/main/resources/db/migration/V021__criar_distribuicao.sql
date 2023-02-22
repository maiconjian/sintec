create table SERV_DISTRIBUICAO (
   ID                   bigint              identity,
   ID_SERVICO           bigint              null,
   ID_USUARIO           bigint              null,
   FLAG_ASSOCIADO         INT                  null,
   DT_ATUALIZACAO       DATETIME             null,
   constraint PK_SERV_DISTRIBUICAO primary key (ID)
)
go

alter table SERV_DISTRIBUICAO
   add constraint FK_SERV_DIS_REFERENCE_CAD_SERV foreign key (ID_SERVICO)
      references CAD_SERVICO (ID)
go

alter table SERV_DISTRIBUICAO
   add constraint FK_SERV_DIS_REFERENCE_SYS_USUA foreign key (ID_USUARIO)
      references SYS_USUARIO (ID)
go
