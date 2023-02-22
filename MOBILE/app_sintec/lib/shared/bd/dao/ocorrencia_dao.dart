import 'package:app_sintec/shared/bd/sql/ocorrencia_sql.dart';
import 'package:sqflite/sqflite.dart';

import '../../model/ocorrencia.dart';
import '../connectionDB.dart';

class OcorrenciaDao{

  ConnectionDB _connection = ConnectionDB.instance;

  Future<Database> _getDatabase() async {
    return await _connection.database;
  }

  Future<Ocorrencia> merge(Ocorrencia ocorrencia) async{
    try{
      Database db = await _getDatabase();
      List<Map> count = (await db.rawQuery(OcorrenciaSql.isCadastrado(ocorrencia.id)));
      if(count[0]['qtd'] == 0){
        return incluir(ocorrencia);
      }else if(count[0]['qtd'] > 0){
        return alterar(ocorrencia);
      }
      return ocorrencia;
    }catch(error){
      print(error);
      throw Exception();
    }
  }

  Future<Ocorrencia> incluir(Ocorrencia ocorrencia) async {
    try {
      Database db = await _getDatabase();
      int id = await db.rawInsert(OcorrenciaSql.incluir(ocorrencia));
      ocorrencia.id = id;
      return ocorrencia;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

  Future<Ocorrencia> alterar(Ocorrencia ocorrencia) async {
    try {
      Database db = await _getDatabase();
      int id = await db.rawUpdate(OcorrenciaSql.alterar(ocorrencia));
      ocorrencia.id = id;
      return ocorrencia;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

  Future<List<Ocorrencia>> listar() async {
    try {
      Database db = await _getDatabase();
      List<Map> linhas = await db.rawQuery(OcorrenciaSql.listar());
      List<Ocorrencia> lista = Ocorrencia.fromSQLiteList(linhas);
      return lista;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

  Future<List<Ocorrencia>> listarPorContrato(idContrato) async {
    try {
      Database db = await _getDatabase();
      List<Map> linhas = await db.rawQuery(OcorrenciaSql.listaPorContrato(idContrato));
      List<Ocorrencia> lista = Ocorrencia.fromSQLiteList(linhas);
      return lista;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }
}