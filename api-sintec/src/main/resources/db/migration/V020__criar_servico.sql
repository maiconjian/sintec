create table CAD_SERVICO (
   ID                   BIGINT               identity,
   CD_SERVICO           VARCHAR(50)          null,
   DT_REF               DATE                 null,
   ID_REGIONAL          BIGINT               null,
   ID_TIPO_SERVICO      BIGINT               null,
   ID_VEICULO           BIGINT               null,
   ID_IMOVEL            BIGINT               null,
   ID_COLABORADOR       BIGINT               null,
   DT_SOLICITACAO       DATE                 null,
   DT_PROGRAMADA        DATE                 null,
   FLAG_AVULSA          TINYINT              null,
   NR_DIAS_AGENDAMENTO  INTEGER              null,
   DT_ATUALIZACAO       DATETIME             null,
   ST_ATIVO             TINYINT              null,
   constraint PK_CAD_SERVICO primary key (ID)
)
go

alter table CAD_SERVICO
   add constraint FK_CAD_SERV_REFERENCE_CAD_TIPO foreign key (ID_TIPO_SERVICO)
      references CAD_TIPO_SERVICO (ID)
go

alter table CAD_SERVICO
   add constraint FK_CAD_SERV_REFERENCE_CAD_IMOV foreign key (ID_IMOVEL)
      references CAD_IMOVEL (ID)
go

alter table CAD_SERVICO
   add constraint FK_CAD_SERV_REFERENCE_CAD_COLA foreign key (ID_COLABORADOR)
      references CAD_COLABORADOR (ID)
go

alter table CAD_SERVICO
   add constraint FK_CAD_SERV_REFERENCE_CAD_VEIC foreign key (ID_VEICULO)
      references CAD_VEICULO (ID)
go

alter table CAD_SERVICO
   add constraint FK_CAD_SERV_REFERENCE_CAD_REGI foreign key (ID_REGIONAL)
      references CAD_REGIONAL (ID)
go