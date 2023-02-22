import '../../model/questionario.dart';

class QuestionarioSql {
  static String incluir(Questionario questionario) {
    return '''
          INSERT INTO questionario(id,titulo,id_tipo_servico,ordem_apresentacao,ativo)
          VALUES(${questionario.id},'${questionario.titulo}',${questionario.idTipoServico},'${questionario.ordemApresentacao}',${questionario.ativo})
          ''';
  }

  static String alterar(Questionario questionario) {
    return '''
        UPDATE questionario SET id = ${questionario.id},titulo = '${questionario.titulo}',id_tipo_servico = ${questionario.idTipoServico},
        ordem_apresentacao = ${questionario.ordemApresentacao},ativo = ${questionario.ativo} WHERE id =${questionario.id}
        ''';
  }

  static String isCadastrado(id) {
    return '''
        SELECT COUNT(*) AS qtd FROM questionario WHERE id = ${id};
    ''';
  }

  // static String lista(idTipoServico,idServico) {
  //   return '''
  //       SELECT A.id as id_questionario, A.titulo as titulo_questionario, count(*) as qtd_pergunta,
  //       SUM(CASE WHEN C.id ISNULL THEN 0 ELSE 1 END ) AS qtd_respondida
  //       FROM questionario as a
  //       INNER JOIN pergunta AS B ON B.id_questionario = a.id
  //       LEFT OUTER JOIN retorno_resposta as c on c.id_pergunta = b.id and c.id_servico = ${idServico}
  //       WHERE A.id_tipo_servico = ${idTipoServico}
  //       GROUP BY A.titulo
  //       ORDER BY A.ordem_apresentacao
  //   ''';
  // }

  static String lista(idTipoServico,idServico) {
    return '''
      SELECT A.id as id_questionario, A.titulo as titulo_questionario, count(*) as qtd_pergunta,
        SUM(CASE WHEN C.id ISNULL THEN 0 ELSE 1 END ) AS qtd_respondida,
        SUM(CASE WHEN D.ID NOTNULL AND D.flag_nconf = 1 THEN 1 ELSE 0 END ) AS qtd_nconf
        FROM questionario as a 
        INNER JOIN pergunta AS B ON B.id_questionario = a.id
        LEFT OUTER JOIN retorno_resposta as c on c.id_pergunta = b.id and c.id_servico = $idServico
        LEFT OUTER JOIN alternativa AS D ON D.ID = C.id_alternativa
        WHERE A.id_tipo_servico = $idTipoServico  AND A.ativo = 1
        GROUP BY A.titulo
        ORDER BY A.ordem_apresentacao
    ''';
  }

  static String numNConfQuestionario(idServico){
    return '''
        select count(*) as qtd_nconf from retorno_resposta as a
        left outer join alternativa as b on b.id = a.id_alternativa
        where a.id_servico = $idServico and b.id NOTNULL and b.flag_nconf = 1
    ''';
  }
}
