import 'package:app_sintec/shared/bd/sql/alternativa_sql.dart';
import 'package:app_sintec/shared/model/alternativa.dart';
import 'package:sqflite/sqflite.dart';

import '../connectionDB.dart';

class AlternativaDao {

  ConnectionDB _connection = ConnectionDB.instance;

  Future<Database> _getDatabase() async {
    return await _connection.database;
  }

  Future<Alternativa> merge(Alternativa alternativa) async {
    try {
      Database db = await _getDatabase();
      List<Map> count = (await db.rawQuery(
          AlternativaSql.isCadastrado(alternativa.id)));
      if (count[0]['qtd'] == 0) {
        return incluir(alternativa);
      } else if (count[0]['qtd'] > 0) {
        return alterar(alternativa);
      }
      return alternativa;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

  Future<Alternativa> incluir(Alternativa alternativa) async {
    try {
      Database db = await _getDatabase();
      int id = await db.rawInsert(AlternativaSql.incluir(alternativa));
      alternativa.id = id;
      return alternativa;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

  Future<Alternativa> alterar(Alternativa alternativa) async {
    try {
      Database db = await _getDatabase();
      int id = await db.rawUpdate(AlternativaSql.alterar(alternativa));
      alternativa.id = id;
      return alternativa;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

  Future<List<Map>> listarAlternativaPergunta(idPergunta) async {
    try {
      Database db = await _getDatabase();
      List<Map> linhas = await db.rawQuery(AlternativaSql.listarAlternativaPergunta(idPergunta));
      return linhas;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

}

