import 'package:app_sintec/shared/model/retorno.dart';

class RetornoSql{

  static String incluir(Retorno retorno) {
    return '''
                INSERT INTO retorno(id,id_usuario,id_servico,id_ocorrencia,data_execucao,
                longitude,latitude,flag_racp,marca_aparelho,modelo_aparelho,sincronizado,ativo)
               VALUES(${retorno.id},${retorno.idUsuario},${retorno.idServico},${retorno.idOcorrencia},
               '${retorno.dataExecucao}','${retorno.longitude}','${retorno.latitude}',${retorno.flagRacp},'${retorno.marcaAparelho}','${retorno.modeloAparelho}',${retorno.sincronizado},${retorno.ativo});
                ''';
  }

  static String buscarPorId(idServico) {
    return '''
        SELECT *  FROM retorno WHERE id_servico = ${idServico};
    ''';
  }

  static String updateSincronizado(listaIdServico) {
    return '''
        UPDATE retorno SET sincronizado = 1 WHERE id_servico in (${listaIdServico.join(', ')});
    ''';
  }

  static String retornoSincronismoPendente(){
    return '''
        SELECT * FROM retorno WHERE sincronizado = 0 AND ativo = 1
    ''';
  }
}