create table CAD_COLABORADOR (
   ID                   BIGINT               identity,
   NM_NOME              VARCHAR(200)         null,
   NR_MATRICULA         BIGINT               null,
   NR_CPF               VARCHAR(20)          null,
   DT_NASCIMENTO        DATE                 null,
   DT_ADMISSAO          DATE                 null,
   DT_ATUALIZACAO       DATETIME             null,
   NR_CNH               VARCHAR(50)          null,
   DT_VALIDADE_CNH      DATE                 null,
   DS_CATEGORIA_CNH     VARCHAR(5)           null,
   ID_REGIONAL          BIGINT               null,
   ST_ATIVO             TINYINT              null,
   constraint PK_CAD_COLABORADOR primary key (ID)
)
go

alter table CAD_COLABORADOR
   add constraint FK_CAD_COLA_REFERENCE_CAD_REGI foreign key (ID_REGIONAL)
      references CAD_REGIONAL (ID)
go
