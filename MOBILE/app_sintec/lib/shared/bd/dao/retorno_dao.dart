import 'package:app_sintec/shared/bd/sql/retorno_sql.dart';
import 'package:app_sintec/shared/model/retorno.dart';
import 'package:sqflite/sqflite.dart';

import '../connectionDB.dart';

class RetornoDao{
  ConnectionDB _connection = ConnectionDB.instance;

  Future<Database> _getDatabase() async {
    return await _connection.database;
  }

  Future<Retorno> incluir(Retorno retorno) async {
    try {
      Database db = await _getDatabase();
      int id = await db.rawInsert(RetornoSql.incluir(retorno));
      retorno.id = id;
      return retorno;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

  Future<Retorno> buscarPorId(idServico) async{
    try{
      Database db = await _getDatabase();
      List<Map> lista = await db.rawQuery(RetornoSql.buscarPorId(idServico));
      Retorno retorno = Retorno.fromSQLite(lista[0]);
      return retorno;
    }catch(error){
      print(error);
      throw Exception();
    }
  }

  Future<int> updateSincronizado(listaIdServico) async{
    try {
      Database db = await _getDatabase();
      int id = await db.rawInsert(RetornoSql.updateSincronizado(listaIdServico));
      return id;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

  Future<List<Retorno>> retornoSincronismoPendente() async{
    try{
      Database db = await _getDatabase();
      List<Map> lista = await db.rawQuery(RetornoSql.retornoSincronismoPendente());
      List<Retorno> listaRetorno = Retorno.fromSQLiteList(lista);
      return listaRetorno;
    } catch(error){
      print(error);
      throw Exception();
    }
  }


}