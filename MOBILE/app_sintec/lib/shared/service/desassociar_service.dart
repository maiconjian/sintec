import 'dart:convert';

import 'package:app_sintec/shared/bd/dao/retorno_dao.dart';
import 'package:app_sintec/shared/bd/dao/retorno_foto_dao.dart';
import 'package:app_sintec/shared/bd/dao/retorno_resposta_dao.dart';
import 'package:app_sintec/shared/bd/dao/servico_dao.dart';
import 'package:app_sintec/shared/util/local_storage.dart';

import '../../core/toast.dart';
import '../../environments.dart';
import 'package:http/http.dart' as http;

class DesassociarService{

  Future<void> conferirDesassociados() async {
    int idUsuario = await LocalStorage.loadIdUsuario();
    List<int> listaIdServico = await _getDesassociados(idUsuario);
    ServicoDao servicoDao = ServicoDao();
    List<int> listaIdExecutado = [];
    for(var id in listaIdServico){
      await servicoDao.deletarServico(id);
      listaIdExecutado.add(id);
    }
    _enviarListaIdServicoDessasociado(listaIdExecutado);

  }

  Future<List<int>> _getDesassociados(idUsuario) async{
    try{
      List<int> lista=[];
      String url = '${Environments.url}/distribuicao/get-desassociado-mobile/${idUsuario}';
      http.Response response = await http.get(Uri.parse(url));
      dynamic dadosJson = jsonDecode(response.body);
      if(response.statusCode == 200){
        for(var res in dadosJson) {
          lista.add(res);
        }
      }
      return lista;
    }catch(erro){
      print(erro);
      throw Exception();
    }
  }

  Future<void> _enviarListaIdServicoDessasociado(List<int>listaIdServico) async {
    try{
      String url = '${Environments.url}/distribuicao/deletar-desassociado';
      String strBody = jsonEncode(listaIdServico);
      Map<String, String> headers = {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      };

      http.Response response = await http.delete(headers: headers,Uri.parse(url),body: strBody);
      if(response.statusCode != 200){
        ToastMesage.showToastError("Sem resposta do servidor");
      }
    }catch(error){
      print(error);
      throw Exception();
    }
  }





}
