create table CAD_PERGUNTA (
   ID                   bigint              identity,
   ID_QUESTIONARIO      bigint              null,
   NM_ENUNCIADO         VARCHAR(300)         null,
   NR_ORDEM_APRESENTACAO INTEGER              null,
   FLAG_FOTO            TINYINT              null,
   FLAG_OBRIGATORIO     TINYINT              null,
   FLAG_MULTIPLA_ESCOLHA TINYINT              null,
   DT_ATUALIZACAO       DATETIME             null,
   ST_ATIVO             TINYINT              null,
   constraint PK_CAD_PERGUNTA primary key (ID)
)
go

alter table CAD_PERGUNTA
   add constraint FK_CAD_PERG_REFERENCE_CAD_QUES foreign key (ID_QUESTIONARIO)
      references CAD_QUESTIONARIO (ID)
go
