create table CAD_CONTRATO (
   ID                   bigint              identity,
   NM_NOME              VARCHAR(100)         null,
   DT_VIGENCIA_INI      DATE                 null,
   DT_VIGENCIA_FIM      DATE                 null,
   DS_EMAIL             VARCHAR(150)         null,
   ID_EMPRESA           bigint              null,
   NM_GERENTE           VARCHAR(200)         null,
   DT_ATUALIZACAO       DATETIME             null,
   ST_ATIVO             TINYINT              null,
   constraint PK_CAD_CONTRATO primary key (ID)
)
go

alter table CAD_CONTRATO
   add constraint FK_CAD_CONT_REFERENCE_CAD_EMPR foreign key (ID_EMPRESA)
      references CAD_EMPRESA (ID)
go
