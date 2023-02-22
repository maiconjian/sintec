
import 'package:app_sintec/shared/bd/sql/pergunta_sql.dart';
import 'package:app_sintec/shared/dto/pergunta_dto.dart';
import 'package:sqflite/sqflite.dart';
import '../../model/pergunta.dart';
import '../connectionDB.dart';

class PerguntaDao {

  ConnectionDB _connection = ConnectionDB.instance;

  Future<Database> _getDatabase() async {
    return await _connection.database;
  }

  Future<Pergunta> merge(Pergunta pergunta) async {
    try {
      Database db = await _getDatabase();
      List<Map> count = (await db.rawQuery(
          PerguntaSql.isCadastrado(pergunta.id)));
      if (count[0]['qtd'] == 0) {
        return incluir(pergunta);
      } else if (count[0]['qtd'] > 0) {
        return alterar(pergunta);
      }
      return pergunta;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

  Future<Pergunta> incluir(Pergunta pergunta) async {
    try {
      Database db = await _getDatabase();
      int id = await db.rawInsert(PerguntaSql.incluir(pergunta));
      pergunta.id = id;
      return pergunta;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

  Future<Pergunta> alterar(Pergunta pergunta) async {
    try {
      Database db = await _getDatabase();
      int id = await db.rawUpdate(PerguntaSql.alterar(pergunta));
      pergunta.id = id;
      return pergunta;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

  Future<List<Map>> listar(idQuestionario,idServico) async {
    try {
      Database db = await _getDatabase();
      List<Map> linhas = await db.rawQuery(PerguntaSql.listar(idQuestionario,idServico));
      return linhas;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }
}

