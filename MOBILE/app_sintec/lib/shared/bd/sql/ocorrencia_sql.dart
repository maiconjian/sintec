
import '../../model/ocorrencia.dart';

class OcorrenciaSql {
  static String incluir(Ocorrencia ocorrencia) {
    return '''
                INSERT INTO ocorrencia(id,descricao,id_contrato,ativo)
               VALUES(${ocorrencia.id},'${ocorrencia.descricao}',${ocorrencia
        .idContrato},${ocorrencia.ativo})
                ''';
  }

  static String alterar(Ocorrencia ocorrencia) {
    return '''
        UPDATE ocorrencia SET id = ${ocorrencia.id},descricao = '${ocorrencia
        .descricao}',id_contrato = ${ocorrencia.idContrato},
       ativo = ${ocorrencia.ativo} WHERE id =${ocorrencia.id}
        ''';
  }

  static String listar() {
    return 'SELECT * FROM ocorrencia;';
  }

  static String isCadastrado(id) {
    return '''
        SELECT COUNT(*) AS qtd FROM ocorrencia WHERE id = ${id};
    ''';
  }

  static String listaPorContrato(idContrato){
    return '''
      SELECT * FROM OCORRENCIA WHERE ID_CONTRATO = ${idContrato} 
    ''';
  }
}