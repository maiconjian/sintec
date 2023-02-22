import 'dart:convert';
import 'package:app_sintec/environments.dart';
import 'package:app_sintec/shared/model/usuario.dart';
import 'package:http/http.dart' as http;

import '../../core/toast.dart';

class DownloadService{

  confirmarDownlaodServico(List<int> listaIdServico,idUsuario) async{

    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['listaIdServico'] = listaIdServico;
    data['idUsuario'] = idUsuario;

    String strBody = jsonEncode(data);

    try{
      Map<String, String> headers = {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      };
      String url = '${Environments.url}/distribuicao/confirmar-download-servico';
      http.Response response = await http.post(headers: headers,Uri.parse(url),body: strBody);
      print(response.statusCode);
      if(response.statusCode != 200){
        ToastMesage.showToastError("Erro na requisição!");
      }
    }catch(error){
      print(error);
      throw Exception();
    }
  }

  Future<dynamic> getUsuariosMobile() async{
    // List<String> chckList = checkoutList.map((e) => json.encode(e.toJson())).toList();
    try{
      String url = '${Environments.url}/usuario/get-usuario-mobile';
      http.Response response = await http.get(Uri.parse(url));
      dynamic dadosJson = json.decode(utf8.decode(response.body.codeUnits));
      if(response.statusCode == 200){
        return dadosJson;
      }else{
        return null;
      }
    }catch(erro){
      print(erro);
      throw Exception();
    }
  }

  Future<dynamic> getOcorrenciaMobile(idUsuario) async{
    try{
      String url = '${Environments.url}/ocorrencia/get-ocorrencia-mobile/$idUsuario';
      http.Response response = await http.get(Uri.parse(url));
      dynamic dadosJson = json.decode(utf8.decode(response.body.codeUnits));
      return dadosJson;
    }catch(erro){
      print(erro);
      throw Exception();
    }
  }

  Future<dynamic> getQuestionarioMobile(idUsuario) async{
    try{
      String url = '${Environments.url}/questionario/get-questionario-mobile/$idUsuario';
      http.Response response = await http.get(Uri.parse(url));
      dynamic dadosJson = json.decode(utf8.decode(response.body.codeUnits));
      return dadosJson;
    }catch(erro){
      print(erro);
      throw Exception();
    }
  }

  Future<dynamic> getPerguntaMobile(idUsuario) async{
    try{
      String url = '${Environments.url}/pergunta/get-pergunta-mobile/$idUsuario';
      http.Response response = await http.get(Uri.parse(url));
      dynamic dadosJson = json.decode(utf8.decode(response.body.codeUnits));
      return dadosJson;
    }catch(erro){
      print(erro);
      throw Exception();
    }
  }

  Future<dynamic> getAlternativaMobile(idUsuario) async{
    try{
      String url = '${Environments.url}/alternativa/get-alternativa-mobile/$idUsuario';
      http.Response response = await http.get(Uri.parse(url));
      dynamic dadosJson = json.decode(utf8.decode(response.body.codeUnits));
      return dadosJson;
    }catch(erro){
      print(erro);
      throw Exception();
    }
  }

  Future<dynamic> getServicoLiberadoMobile(idUsuario) async{
    try{
      String url = '${Environments.url}/servico/get-servico-liberado/$idUsuario';
      http.Response response = await http.get(Uri.parse(url));
      dynamic dadosJson = json.decode(utf8.decode(response.body.codeUnits));
      return dadosJson;
    }catch(erro){
      print(erro);
      throw Exception();
    }
  }

}