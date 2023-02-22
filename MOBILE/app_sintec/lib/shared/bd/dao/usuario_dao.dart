import 'package:app_sintec/shared/bd/sql/usuario_sql.dart';
import 'package:app_sintec/shared/model/usuario.dart';
import 'package:sqflite/sqflite.dart';

import '../connectionDB.dart';

class UsuarioDao {
  ConnectionDB _connection = ConnectionDB.instance;

  Future<Database> _getDatabase() async {
    return await _connection.database;
  }

  Future<Usuario> merge(Usuario usuario) async{
      try{
        Database db = await _getDatabase();
        List<Map> count = (await db.rawQuery(UsuarioSql.isCadastrado(usuario.id)));
        if(count[0]['qtd'] == 0){
          return incluir(usuario);
        }else if(count[0]['qtd'] > 0){
          return alterar(usuario);
        }
        return usuario;
      }catch(error){
        print(error);
        throw Exception();
      }
  }

  Future<Usuario> incluir(Usuario usuario) async {
    try {
      Database db = await _getDatabase();
      int id = await db.rawInsert(UsuarioSql.incluir(usuario));
      usuario.id = id;
      return usuario;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

  Future<Usuario> alterar(Usuario usuario) async {
    try {
      Database db = await _getDatabase();
      int id = await db.rawUpdate(UsuarioSql.alterar(usuario));
      usuario.id = id;
      return usuario;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

  Future<List<Usuario>> listarUsuario() async {
    try {
      Database db = await _getDatabase();
      List<Map> linhas = await db.rawQuery(UsuarioSql.listarUsuario());
      List<Usuario> listaUsuario = Usuario.fromSQLiteList(linhas);
      return listaUsuario;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

  Future<List<Usuario>> authenticar(matricula,senha) async {
    try {
      Database db = await _getDatabase();
      List<Map> linhas = await db.rawQuery(UsuarioSql.autenticar(matricula, senha));
      List<Usuario> listaUsuario = Usuario.fromSQLiteList(linhas);
      return listaUsuario;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }
  
}
