import 'package:app_sintec/shared/model/alternativa.dart';

class AlternativaSql{
  static String incluir(Alternativa alternativa) {
    return '''
          INSERT INTO alternativa(id,descricao,id_pergunta,flag_nconf,ordem_apresentacao,ativo)
          VALUES(${alternativa.id},'${alternativa.descricao}',${alternativa.idPergunta},${alternativa.flagNconf},${alternativa.ordemApresentacao},${alternativa.ativo})
          ''';
  }

  static String alterar(Alternativa alternativa) {
    return '''
        UPDATE alternativa SET id = ${alternativa.id},descricao = '${alternativa.descricao}',id_pergunta=${alternativa.idPergunta},flag_nconf =${alternativa.flagNconf}
        ,ordem_apresentacao=${alternativa.ordemApresentacao},ativo = ${alternativa.ativo} WHERE id =${alternativa.id}
        ''';
  }

  static String isCadastrado(id) {
    return '''
        SELECT COUNT(*) AS qtd FROM alternativa WHERE id = ${id};
    ''';
  }

  static String listarAlternativaPergunta(idQuestionario){
    return '''
        SELECT a.* FROM alternativa AS a
        INNER JOIN pergunta as b on b.id = a.id_pergunta
        where b.id_questionario = ${idQuestionario}
        ORDER BY a.ordem_apresentacao
    ''';
  }


}