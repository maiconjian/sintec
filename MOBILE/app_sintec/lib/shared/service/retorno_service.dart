import 'dart:convert';

import 'package:app_sintec/core/toast.dart';
import 'package:app_sintec/environments.dart';
import 'package:app_sintec/shared/dto/sinc_foto_dto.dart';
import 'package:app_sintec/shared/model/retorno_resposta.dart';
import 'package:http/http.dart' as http;

import '../model/retorno.dart';

class RetornoService{

  Future<List<int>> sincronizarRetorno(List<Retorno>listaRetorno) async {
     try{
       List<int> lista =[];
       String url = '${Environments.url}/retorno/incluir';
       String strBody = jsonEncode(listaRetorno.map((e) => e.toJson()).toList()).toString();
       Map<String, String> headers = {
         'Accept': 'application/json',
         'Content-Type': 'application/json'
       };

       http.Response response = await http.post(headers: headers,Uri.parse(url),body: strBody);
       dynamic dadosJson = json.decode(utf8.decode(response.body.codeUnits));
       if(response.statusCode != 200){
         ToastMesage.showToastError("Sem resposta do servidor");
       }else{
         for(var idServico in dadosJson){
           lista.add(idServico);
         }
         return lista;
       }
       return lista;
     }catch(error){
       print(error);
       throw Exception();
     }
  }

  Future<List<int>>  sincronizarRetornoResposta(List<RetornoResposta>listaRetornoResposta) async {
     try{
       List<int> lista =[];
       String url = '${Environments.url}/retorno-resposta/incluir';
       String strBody = jsonEncode(listaRetornoResposta.map((e) => e.toJson()).toList()).toString();
       Map<String, String> headers = {
         'Accept': 'application/json',
         'Content-Type': 'application/json'
       };

       http.Response response = await http.post(headers: headers,Uri.parse(url),body: strBody);
       dynamic dadosJson = json.decode(utf8.decode(response.body.codeUnits));
       if(response.statusCode != 200){
         ToastMesage.showToastError("Sem resposta do servidor");
       }else{
         for(var idServico in dadosJson){
           lista.add(idServico);
         }
         return lista;
       }
       return lista;
     }catch(error){
       print(error);
       throw Exception();
     }
   }

  Future<List<int>>  sincronizarRetornoFoto(List<SincFotoDto>listaSincFoto) async {
     try{
       List<int> lista =[];
       String url = '${Environments.url}/retorno-foto/incluir';
       String strBody = jsonEncode(listaSincFoto.map((e) => e.toJson()).toList()).toString();
       Map<String, String> headers = {
         'Accept': 'application/json',
         'Content-Type': 'application/json'
       };
       http.Response response = await http.post(headers: headers,Uri.parse(url),body: strBody);
       dynamic dadosJson = json.decode(utf8.decode(response.body.codeUnits));
       if(response.statusCode != 200){
         ToastMesage.showToastError("Sem resposta do servidor");
       }else{
         for(var idServico in dadosJson){
           lista.add(idServico);
         }
         return lista;
       }
       return lista;
     }catch(error){
       print(error);
       throw Exception();
     }
   }
}