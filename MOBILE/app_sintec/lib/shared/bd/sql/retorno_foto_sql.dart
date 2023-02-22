import 'package:app_sintec/shared/model/retorno.dart';
import 'package:app_sintec/shared/model/retorno_foto.dart';

class RetornoFotoSql{

  static String incluir(RetornoFoto retornoFoto) {
    return '''
          INSERT INTO retorno_foto(nome,longitude,latitude,path,id_questionario,id_servico,flag_assinatura,observacao,data_atualizacao,sincronizado)
          VALUES('${retornoFoto.nome}','${retornoFoto.longitude}','${retornoFoto.latitude}','${retornoFoto.path}',
          ${retornoFoto.idQuestionario},${retornoFoto.idServico},
          ${retornoFoto.flagAssinatura},'${retornoFoto.observacao}','${retornoFoto.dataAtualizacao}',${retornoFoto.sincronizado});
          ''';
  }

  static String alterar(RetornoFoto retornoFoto) {
    return '''
        UPDATE retorno_foto SET nome = '${retornoFoto.nome}', longitude= '${retornoFoto.longitude}',latitude='${retornoFoto.latitude}',
        path='${retornoFoto.path}',id_questionario=${retornoFoto.idQuestionario},id_servico=${retornoFoto.idServico},flag_assinatura=${retornoFoto.flagAssinatura},
        observacao= '${retornoFoto.observacao}',data_atualizacao = '${retornoFoto.dataAtualizacao}',  sincronizado = ${retornoFoto.sincronizado}  WHERE id =${retornoFoto.id}
        ''';
  }

  static String isCadastrado(id) {
    return '''
        SELECT COUNT(*) AS qtd FROM retorno_foto WHERE id = ${id};
    ''';
  }

  static String listarPorServico(idServico,idQuestionario){
    return '''
        SELECT * FROM retorno_foto 
        WHERE id_servico = ${idServico}
        AND (id_questionario = ${idQuestionario} OR ${idQuestionario} = 0 );
    ''';
  }

  static String deleteRetornoFoto(idRetornoFoto){
     return '''
        DELETE FROM retorno_foto where id = ${idRetornoFoto}
     ''';
  }


  static String buscarFotos(idServico) {
    return '''
        SELECT  *  FROM retorno_foto WHERE id_servico = ${idServico};
    ''';
  }

  static String retornoSincronismoPendente() {
    return '''
        SELECT  *  FROM retorno_foto WHERE sincronizado = 0;
    ''';
  }

  static String updateSincronizado(listaIdServico) {
    return '''
        UPDATE retorno_foto SET sincronizado = 1 WHERE id_servico in (${listaIdServico.join(', ')});
    ''';
  }
}