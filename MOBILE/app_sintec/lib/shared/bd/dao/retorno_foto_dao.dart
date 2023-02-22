import 'package:app_sintec/shared/model/retorno_foto.dart';
import 'package:sqflite/sqflite.dart';

import '../connectionDB.dart';
import '../sql/retorno_foto_sql.dart';

class RetornoFotoDao{

  ConnectionDB _connection = ConnectionDB.instance;

  Future<Database> _getDatabase() async {
    return await _connection.database;
  }

  Future<RetornoFoto> merge(RetornoFoto retornoFoto) async {
    try {
      Database db = await _getDatabase();
      List<Map> count = (await db.rawQuery(
          RetornoFotoSql.isCadastrado(retornoFoto.id)));
      if (count[0]['qtd'] == 0) {
        return incluir(retornoFoto);
      } else if (count[0]['qtd'] > 0) {
        return alterar(retornoFoto);
      }
      return retornoFoto;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

  Future<RetornoFoto> incluir(RetornoFoto retornoFoto) async {
    try {
      Database db = await _getDatabase();
      int id = await db.rawInsert(RetornoFotoSql.incluir(retornoFoto));
      retornoFoto.id = id;
      return retornoFoto;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

  Future<RetornoFoto> alterar(RetornoFoto retornoFoto) async {
    try {
      Database db = await _getDatabase();
      int id = await db.rawUpdate(RetornoFotoSql.alterar(retornoFoto));
      retornoFoto.id = id;
      return retornoFoto;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

  Future<List<RetornoFoto>> listarPorServico(idServico,idQuestionario) async {
    try {
      Database db = await _getDatabase();
      List<Map> linhas = await db.rawQuery(RetornoFotoSql.listarPorServico(idServico, idQuestionario));
      List<RetornoFoto> lista = RetornoFoto.fromSQLiteList(linhas);
      return lista;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

  Future<void> deleteRetornoFoto(idRetornoFoto) async {
    try{
      Database db = await _getDatabase();
      await db.rawQuery(RetornoFotoSql.deleteRetornoFoto(idRetornoFoto));
    }catch (error) {
      print(error);
      throw Exception();
    }
  }


  Future<List<RetornoFoto>> buscarFotos(idServico) async{
    try{
      Database db = await _getDatabase();
      List<Map> lista = await db.rawQuery(RetornoFotoSql.buscarFotos(idServico));
      List<RetornoFoto> listaRetorno = RetornoFoto.fromSQLiteList(lista);
      return listaRetorno;
    }catch(error){
      print(error);
      throw Exception();
    }
  }

  Future<List<RetornoFoto>> retornoSincronismoPendente() async{
    try{
      Database db = await _getDatabase();
      List<Map> lista = await db.rawQuery(RetornoFotoSql.retornoSincronismoPendente());
      List<RetornoFoto> listaRetorno = RetornoFoto.fromSQLiteList(lista);
      return listaRetorno;
    }catch(error){
      print(error);
      throw Exception();
    }
  }

  Future<int> updateSincronizado(listaIdServico) async{
    try {
      Database db = await _getDatabase();
      int id = await db.rawInsert(RetornoFotoSql.updateSincronizado(listaIdServico));
      return id;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

}