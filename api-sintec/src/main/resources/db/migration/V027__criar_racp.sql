create table CAD_RACP (
   ID                   BIGINT               identity,
   ID_SERVICO           BIGINT               null,
   DT_REF               DATE                 null,
   CD_RACP              VARCHAR(50)          null,
   NR_PRIORIDADE        INTEGER              null,
   DT_GERACAO           DATE                 null,
   DT_PROGRAMADA        DATE                 null,
   DT_EXECUCAO          DATE                 null,
   DT_ATUALIZACAO       DATETIME             null,
   ID_SITUACAO          BIGINT               null,
   constraint PK_CAD_RACP primary key (ID)
)
go

alter table CAD_RACP
   add constraint FK_CAD_RACP_REFERENCE_CAD_SERV foreign key (ID_SERVICO)
      references CAD_SERVICO (ID)
go

alter table CAD_RACP
   add constraint FK_CAD_RACP_REFERENCE_CAD_SITU foreign key (ID_SITUACAO)
      references CAD_SITUACAO_RACP (ID)
go
