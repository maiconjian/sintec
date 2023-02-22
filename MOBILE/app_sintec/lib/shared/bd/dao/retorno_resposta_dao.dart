import 'package:app_sintec/shared/bd/sql/retorno_resposta_sql.dart';
import 'package:app_sintec/shared/model/retorno_resposta.dart';
import 'package:sqflite/sqflite.dart';

import '../connectionDB.dart';

class RetornoRespostaDao {

  ConnectionDB _connection = ConnectionDB.instance;

  Future<Database> _getDatabase() async {
    return await _connection.database;
  }

  Future<RetornoResposta> merge(RetornoResposta retornoResposta) async {
    try {
      Database db = await _getDatabase();
      List<Map> count = (await db.rawQuery(
          RetornoRespostaSql.isCadastrado(retornoResposta.id)));
      if (count[0]['qtd'] == 0) {
        return incluir(retornoResposta);
      } else if (count[0]['qtd'] > 0) {
        return alterar(retornoResposta);
      }
      return retornoResposta;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

  Future<RetornoResposta> incluir(RetornoResposta retornoResposta) async {
    try {
      Database db = await _getDatabase();
      int id = await db.rawInsert(RetornoRespostaSql.incluir(retornoResposta));
      retornoResposta.id = id;
      return retornoResposta;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

  Future<RetornoResposta> alterar(RetornoResposta retornoResposta) async {
    try {
      Database db = await _getDatabase();
      int id = await db.rawUpdate(RetornoRespostaSql.alterar(retornoResposta));
      retornoResposta.id = id;
      return retornoResposta;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

  Future<List<RetornoResposta>> buscarRespostas(idServico) async{
    try{
      Database db = await _getDatabase();
      List<Map> lista = await db.rawQuery(RetornoRespostaSql.buscarRespostas(idServico));
      List<RetornoResposta> listaRetorno = RetornoResposta.fromSQLiteList(lista);
      return listaRetorno;
    }catch(error){
      print(error);
      throw Exception();
    }
  }

  Future<List<RetornoResposta>> retornoSincronismoPendente() async{
    try{
      Database db = await _getDatabase();
      List<Map> lista = await db.rawQuery(RetornoRespostaSql.retornoSincronismoPendente());
      List<RetornoResposta> listaRetorno = RetornoResposta.fromSQLiteList(lista);
      return listaRetorno;
    }catch(error){
      print(error);
      throw Exception();
    }
  }


  Future<int> updateSincronizado(listaIdServico) async{
    try {
      Database db = await _getDatabase();
      int id = await db.rawInsert(RetornoRespostaSql.updateSincronizado(listaIdServico));
      return id;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

}