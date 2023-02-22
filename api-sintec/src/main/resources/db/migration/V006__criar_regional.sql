create table CAD_REGIONAL (
   ID                   bigint              identity,
   ID_CONTRATO          bigint              null,
   NM_NOME              VARCHAR(150)         null,
   DT_ATUALIZACAO       datetime             null,
   ST_ATIVO             TINYINT              null,
   constraint PK_CAD_REGIONAL primary key (ID)
)
go

alter table CAD_REGIONAL
   add constraint FK_CAD_REGI_REFERENCE_CAD_CONT foreign key (ID_CONTRATO)
      references CAD_CONTRATO (ID)
go
