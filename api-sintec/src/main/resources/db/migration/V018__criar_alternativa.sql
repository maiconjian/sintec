create table CAD_ALTERNATIVA (
   ID                   BIGINT               identity,
   ID_PERGUNTA          BIGINT               null,
   NM_DESCRICAO         VARCHAR(300)         null,
   FLAG_NCONF           TINYINT              null,
   NR_NCONF_PRIORIDADE  INTEGER              null,
   DS_NCONF_RECOMENDACAO VARCHAR(300)        null,
   NR_ORDEM_APRESENTACAO INTEGER             null,
   DT_ATUALIZACAO       DATETIME             null,
   ST_ATIVO             TINYINT              null,
   constraint PK_CAD_ALTERNATIVA primary key (ID)
)
go

alter table CAD_ALTERNATIVA
   add constraint FK_CAD_ALTE_REFERENCE_CAD_PERG foreign key (ID_PERGUNTA)
      references CAD_PERGUNTA (ID)
go
