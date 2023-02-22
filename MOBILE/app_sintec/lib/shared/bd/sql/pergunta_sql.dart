import 'package:app_sintec/shared/model/pergunta.dart';

class PerguntaSql {
  static String incluir(Pergunta pergunta) {
    return '''
          INSERT INTO pergunta(id,enunciado,flag_foto,flag_obrigatorio,flag_multipla_escolha,
          id_questionario,ordem_apresentacao,ativo)
          VALUES(${pergunta.id},'${pergunta.enunciado}',${pergunta.flagFoto},${pergunta.flagObrigatorio},
          ${pergunta.flagMultiplaEscolha},
          ${pergunta.idQuestionario},${pergunta.ordemApresentacao},${pergunta.ativo})
          ''';
  }

  static String alterar(Pergunta pergunta) {
    return '''
        UPDATE pergunta SET id = ${pergunta.id},enunciado = '${pergunta.enunciado}',flag_foto =${pergunta.flagFoto},
        flag_obrigatorio=${pergunta.flagObrigatorio},flag_multipla_escolha= ${pergunta.flagMultiplaEscolha},
        id_questionario = ${pergunta.idQuestionario},ordem_apresentacao=${pergunta.ordemApresentacao},ativo = ${pergunta.ativo} WHERE id =${pergunta.id}
        ''';
  }

  static String isCadastrado(id) {
    return '''
        SELECT COUNT(*) AS qtd FROM pergunta WHERE id = ${id};
    ''';
  }

  static String listar(idQuestionario,idServico) {
    return '''
      SELECT a.id as id_pergunta,a.enunciado as enunciado_pergunta,
      a.ordem_apresentacao as ordem_apresentacao, a.flag_foto as flag_foto,
      a.flag_obrigatorio as flag_obrigatorio,
      a.flag_multipla_escolha as flag_multipla_escolha,
      a.id_questionario as id_questionario,${idServico} as id_servico, b.id as id_retorno_resposta,
      b.id_alternativa as id_alternativa,
      b.resposta as resposta,b.sincronizado as resposta_sincronizada
      FROM pergunta AS a 
      left outer join retorno_resposta as b on b.id_pergunta = a.id and b.id_servico = ${idServico}
      where a.id_questionario = ${idQuestionario} and a.ativo = 1
      ORDER BY A.ordem_apresentacao
    ''';
  }
}
