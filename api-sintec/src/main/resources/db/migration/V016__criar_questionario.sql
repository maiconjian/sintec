create table CAD_QUESTIONARIO (
   ID                   bigint              identity,
   ID_TIPO_SERVICO      bigint              null,
   NM_TITULO            VARCHAR(100)         null,
   NR_ORDEM_APRESENTACAO INTEGER              null,
   FLAG_OBRIGATORIO     TINYINT              null,
   DT_ATUALIZACAO       DATETIME             null,
   ST_ATIVO             TINYINT              null,
   constraint PK_CAD_QUESTIONARIO primary key (ID)
)
go

alter table CAD_QUESTIONARIO
   add constraint FK_CAD_QUES_REFERENCE_CAD_TIPO foreign key (ID_TIPO_SERVICO)
      references CAD_TIPO_SERVICO (ID)
go
