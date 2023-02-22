import '../../model/servico.dart';

class ServicoSql {
  static String incluir(Servico servico) {
    return '''
         INSERT INTO servico(id,codigo,data_ref,id_contrato,id_regional,nome_regional,id_categoria_tipo_servico ,nome_categoria_tipo_servico,id_tipo_servico ,nome_tipo_servico ,
         id_imovel ,nome_imovel ,bairro_imovel ,logradouro_imovel ,numero_logradouro ,id_veiculo ,placa_veiculo ,
         cor_veiculo ,modelo_veiculo ,id_colaborador ,nome_colaborador,cnh ,validade_cnh ,
         categoria_cnh ,data_programada,id_usuario)
         VALUES(${servico.id},'${servico.codigo}','${servico.dataRef}',${servico.idContrato},${servico.idRegional},'${servico.nomeRegional}',
         ${servico.idCategoriaTipoServico},'${servico.nomeCategoriaTipoServico}',${servico.idTipoServico},'${servico.nomeTipoServico}',
         ${servico.idImovel},'${servico.nomeImovel}','${servico.bairro}','${servico.logradouro}',${servico.numeroLogradouro},
         ${servico.idVeiculo},'${servico.placaVeiculo}','${servico.corVeiculo}','${servico.modeloVeiculo}',${servico.idColaborador},'${servico.nomeColaborador}',
         '${servico.cnh}','${servico.validadeCnh}','${servico.categoriaCnh}','${servico.dataProgramada}',${servico.idUsuario});
                ''';
  }

  static String alterar(Servico servico) {
    return '''
        UPDATE servico SET id = ${servico.id},codigo = '${servico.codigo}',data_ref ='${servico.dataRef}',
        id_contrato = ${servico.idContrato}, id_regional = ${servico.idRegional},nome_regional = '${servico.nomeRegional}',
        id_categoria_tipo_servico =${servico.idCategoriaTipoServico},nome_categoria_tipo_servico ='${servico.nomeCategoriaTipoServico}',
        id_tipo_servico=${servico.idTipoServico} ,nome_tipo_servico ='${servico.nomeTipoServico}',
        id_imovel = ${servico.idImovel},nome_imovel ='${servico.nomeImovel}',bairro_imovel ='${servico.bairro}',logradouro_imovel ='${servico.logradouro}',
        numero_logradouro = ${servico.numeroLogradouro},id_veiculo=${servico.idVeiculo},placa_veiculo ='${servico.placaVeiculo}',
        cor_veiculo='${servico.corVeiculo}',modelo_veiculo='${servico.modeloVeiculo}' ,id_colaborador=${servico.idColaborador} ,nome_colaborador ='${servico.nomeColaborador}',
        cnh = '${servico.cnh}' ,validade_cnh ='${servico.validadeCnh}',
        categoria_cnh ='${servico.categoriaCnh}',data_programada='${servico.dataProgramada}',id_usuario = ${servico.idUsuario} WHERE id = ${servico.id}
        ''';
  }

  static String isCadastrado(id) {
    return '''
        SELECT COUNT(*) AS qtd FROM servico WHERE id = ${id};
    ''';
  }

  static String listaPendente(idUsuario, idRegional, idTipoServico) {
    return '''
      SELECT A.* FROM servico AS A 
      LEFT OUTER JOIN retorno AS B ON B.id_servico = A.id
      WHERE B.ID IS NULL AND A.id_usuario = ${idUsuario}
      AND (a.id_regional = ${idRegional} or ${idRegional} = 0)
      AND (a.id_tipo_servico = ${idTipoServico} or ${idTipoServico} = 0)
      ORDER BY A.data_programada ASC
    ''';
  }

  static String listaRealizados(idUsuario, idRegional, idTipoServico) {
    return '''
      SELECT A.id as id,A.codigo as codigo,B.data_execucao as dataExecucao,A.placa_veiculo as placaVeiculo,A.nome_imovel as nomeImovel,A.nome_colaborador as nomeColaborador,
      A.id_categoria_tipo_servico as idCategoriaTipoServico,B.sincronizado as sincronizado  FROM servico AS A 
      LEFT OUTER JOIN retorno AS B ON B.id_servico = A.id
      WHERE B.ID NOTNULL AND A.id_usuario = ${idUsuario}
      AND (a.id_regional = ${idRegional} or ${idRegional} = 0)
      AND (a.id_tipo_servico = ${idTipoServico} or ${idTipoServico} = 0)
      ORDER BY B.data_execucao DESC
    ''';
  }

  static String listaRegional(idUsuario) {
    return '''
       SELECT a.id_regional as id, a.nome_regional as nome, A.id_contrato as id_contrato FROM servico AS A
       WHERE A.id_usuario = ${idUsuario}
       GROUP BY A.id_regional,A.nome_regional
    ''';
  }

  static String listaTipoServico(idUsuario, idRegional) {
    return '''
      SELECT a.id_tipo_servico as id, a.nome_tipo_servico as nome FROM servico AS A
      WHERE A.id_usuario = ${idUsuario} AND A.id_regional = ${idRegional}
      GROUP BY A.id_tipo_servico,A.nome_tipo_servico
  ''';
  }

  static String deleteServico(idServico) {
    return '''
     DELETE FROM servico AS A 
     WHERE A.ID = (SELECT B.ID from servico  B
      LEFT JOIN retorno  C on C.id_servico = B.id
      where B.id = ${idServico} and  C.id ISNULL)
  ''';
  }
}
