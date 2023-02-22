create table CAD_CONTATO (
   ID                   bigint              identity,
   NM_NOME              VARCHAR(200)         null,
   DS_EMAIL             VARCHAR(150)         null,
   DS_FUNCAO            VARCHAR(200)         null,
   ID_REGIONAL          BIGINT               null,
   DT_ATUALIZACAO       DATETIME             null,
   ST_ATIVO             TINYINT              null,
   constraint PK_CAD_CONTATO primary key (ID)
)
go

alter table CAD_CONTATO
   add constraint FK_CAD_CONT_REFERENCE_CAD_REGI foreign key (ID_REGIONAL)
      references CAD_REGIONAL (ID)
go
