import 'package:app_sintec/shared/bd/sql/questionario_sql.dart';
import 'package:app_sintec/shared/dto/questionario_dto.dart';
import 'package:app_sintec/shared/model/questionario.dart';
import 'package:sqflite/sqflite.dart';

import '../connectionDB.dart';

class QuestionarioDao{

  ConnectionDB _connection = ConnectionDB.instance;

  Future<Database> _getDatabase() async {
    return await _connection.database;
  }

  Future<Questionario> merge(Questionario questioanrio) async{
    try{
      Database db = await _getDatabase();
      List<Map> count = (await db.rawQuery(QuestionarioSql.isCadastrado(questioanrio.id)));
      if(count[0]['qtd'] == 0){
        return incluir(questioanrio);
      }else if(count[0]['qtd'] > 0){
        return alterar(questioanrio);
      }
      return questioanrio;
    }catch(error){
      print(error);
      throw Exception();
    }
  }

  Future<Questionario> incluir(Questionario questioanrio) async {
    try {
      Database db = await _getDatabase();
      int id = await db.rawInsert(QuestionarioSql.incluir(questioanrio));
      questioanrio.id = id;
      return questioanrio;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

  Future<Questionario> alterar(Questionario questioanrio) async {
    try {
      Database db = await _getDatabase();
      int id = await db.rawUpdate(QuestionarioSql.alterar(questioanrio));
      questioanrio.id = id;
      return questioanrio;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

  Future<List<QuestionarioDto>> listar(idTipoServico,idServico) async{
    try{
      Database db = await _getDatabase();
      List<Map> linhas = await db.rawQuery(QuestionarioSql.lista(idTipoServico,idServico));
      List<QuestionarioDto> lista = QuestionarioDto.fromSQLiteList(linhas);
      return lista;
    }catch(error){
      print(error);
      throw Exception();
    }
  }

  Future<Map> numNConfQuestionario(idServico) async {
    try{
      Database db = await _getDatabase();
      List<Map> linhas = await db.rawQuery(QuestionarioSql.numNConfQuestionario(idServico));
      return linhas[0];
    }catch(error){
      print(error);
      throw Exception();
    }
  }

}