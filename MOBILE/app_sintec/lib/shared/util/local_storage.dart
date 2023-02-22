import 'dart:convert';

import 'package:app_sintec/shared/dto/filtro_servico.dart';
import 'package:shared_preferences/shared_preferences.dart';

class LocalStorage {
  static void saveIdUsuario(idUsuario) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.setInt("idUsuarioLogado", idUsuario);
  }

  static Future<int> loadIdUsuario() async {
    try {
      SharedPreferences prefs = await SharedPreferences.getInstance();
      return int.parse(await prefs.get("idUsuarioLogado").toString());
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

  static void saveFilterServico(FiltroServico filtro) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.setInt("idRegional", filtro.idRegional);
    await prefs.setInt("idTipoServico", filtro.idTipoServico);
  }

  static Future<FiltroServico?> loadFiltroServico() async {
    try {
      FiltroServico filtro;
      SharedPreferences prefs = await SharedPreferences.getInstance();
      if(await prefs.containsKey("idRegional") && await prefs.containsKey("idTipoServico")){
        var idRegional = int.parse(await prefs.get("idRegional").toString());
        var idTipoServico =
        int.parse(await prefs.get("idTipoServico").toString());
        filtro = new FiltroServico(
            idRegional: idRegional, idTipoServico: idTipoServico);
        return filtro;
      }
      return null;
    } catch (error) {
      print(error);
      throw Exception();
    }
  }

  static void clearLocalStorage() async{
    SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.clear();
  }
}
