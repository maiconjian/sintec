import 'package:app_sintec/shared/bd/sql/servico_sql.dart';
import 'package:app_sintec/shared/dto/servico_dto.dart';
import 'package:app_sintec/shared/util/local_storage.dart';
import 'package:sqflite/sqflite.dart';

import '../../dto/regional-dto.dart';
import '../../dto/tipo_servico_dto.dart';
import '../../model/servico.dart';
import '../connectionDB.dart';

class ServicoDao{
  ConnectionDB _connection = ConnectionDB.instance;

  Future<Database> _getDatabase() async {
    return await _connection.database;
  }

  Future<Servico> merge(Servico servico) async{
    try{
      Database db = await _getDatabase();
      List<Map> count = (await db.rawQuery(ServicoSql.isCadastrado(servico.id)));
      if(count[0]['qtd'] == 0){
        return incluir(servico);
      }else if(count[0]['qtd'] > 0){
        return alterar(servico);
      }
      return servico;
    }catch(error){
      print(error);
      throw Exception();
    }
  }

  Future<Servico> incluir(Servico servico) async {
    try {
      Database db = await _getDatabase();
      int id = await db.rawInsert(ServicoSql.incluir(servico));
      servico.id = id;
      return servico;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

  Future<Servico> alterar(Servico servico) async {
    try {
      Database db = await _getDatabase();
      int id = await db.rawUpdate(ServicoSql.alterar(servico));
      servico.id = id;
      return servico;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

  Future<List<Servico>> listaPendente(idRegional,idTipoServico) async{
    int idUsuarioLogado = await LocalStorage.loadIdUsuario();
    try{
      Database db = await _getDatabase();
      List<Map> linhas = await db.rawQuery(ServicoSql.listaPendente(idUsuarioLogado,idRegional,idTipoServico));
      List<Servico> listaServico = Servico.fromSQLiteList(linhas);
      return listaServico;
    }catch(error){
      print(error);
      throw Exception();
    }
  }

  Future<List<ServicoDto>> listaRealizados(idRegional,idTipoServico) async{
    int idUsuarioLogado = await LocalStorage.loadIdUsuario();
    try{
      Database db = await _getDatabase();
      List<Map> linhas = await db.rawQuery(ServicoSql.listaRealizados(idUsuarioLogado,idRegional,idTipoServico));
      List<ServicoDto> listaServico = ServicoDto.fromSQLiteList(linhas);
      return listaServico;
    }catch(error){
      print(error);
      throw Exception();
    }
  }

  Future<List<RegionalDto>> listaRegional() async{
    int idUsuarioLogado = await LocalStorage.loadIdUsuario();
    try{
      Database db = await _getDatabase();
      List<Map> linhas = await db.rawQuery(ServicoSql.listaRegional(idUsuarioLogado));
      List<RegionalDto> listaRegional = RegionalDto.fromSQLiteList(linhas);
      return listaRegional;
    }catch(error){
      print(error);
      throw Exception();
    }
  }

  Future<List<TipoServicoDto>> listaTipoServico(idRegional) async{
    int idUsuarioLogado = await LocalStorage.loadIdUsuario();
    try{
      Database db = await _getDatabase();
      List<Map> linhas = await db.rawQuery(ServicoSql.listaTipoServico(idUsuarioLogado,idRegional));
      List<TipoServicoDto> lista = TipoServicoDto.fromSQLiteList(linhas);
      return lista;
    }catch(error){
      print(error);
      throw Exception();
    }
  }

  Future<void> deletarServico(idServico) async{
    try{
      Database db = await _getDatabase();
      await db.rawQuery(ServicoSql.deleteServico(idServico));
    }catch(error){
      print(error);
      throw Exception();
    }
  }
}