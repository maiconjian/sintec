import 'package:app_sintec/shared/model/retorno_resposta.dart';

class RetornoRespostaSql {

  static String incluir(RetornoResposta retornoResposta) {
    return '''
          INSERT INTO retorno_resposta(id_servico,id_pergunta,id_alternativa,longitude,latitude,resposta,data_execucao,sincronizado)
          VALUES(${retornoResposta.idServico},${retornoResposta.idPergunta},${retornoResposta.idAlternativa},'${retornoResposta.longitude}',
          '${retornoResposta.latitude}','${retornoResposta.resposta}','${retornoResposta.dataExecucao}',${retornoResposta.sincronizado})
          ''';
  }

  static String alterar(RetornoResposta retornoResposta) {
    return '''
        UPDATE retorno_resposta SET id_servico = ${retornoResposta.idServico},id_pergunta=${retornoResposta.idPergunta},id_alternativa = ${retornoResposta.idAlternativa},
        longitude='${retornoResposta.longitude}',latitude='${retornoResposta.latitude}',resposta='${retornoResposta.resposta}', data_execucao = '${retornoResposta.dataExecucao}',
        sincronizado=${retornoResposta.sincronizado}  WHERE id =${retornoResposta.id}
        ''';
  }

  static String isCadastrado(id) {
    return '''
        SELECT COUNT(*) AS qtd FROM retorno_resposta WHERE id = ${id};
    ''';
  }

  static String buscarRespostas(idServico) {
    return '''
        SELECT *  FROM retorno_resposta WHERE id_servico = ${idServico};
    ''';
  }

  static String retornoSincronismoPendente(){
    return '''
      SELECT * FROM retorno_resposta WHERE sincronizado = 0;
    ''';
  }


  static String updateSincronizado(listaIdServico) {
    return '''
        UPDATE retorno_resposta SET sincronizado = 1 WHERE id_servico in (${listaIdServico.join(', ')});
    ''';
  }


}