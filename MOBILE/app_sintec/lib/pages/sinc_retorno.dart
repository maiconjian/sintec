import 'dart:convert';
import 'dart:core';
import 'dart:io';
import 'dart:typed_data';

import 'package:connectivity/connectivity.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import 'package:path/path.dart' as path;
import 'package:path_provider/path_provider.dart' as syspaths;

import '../core/toast.dart';
import '../shared/bd/dao/retorno_dao.dart';
import '../shared/bd/dao/retorno_foto_dao.dart';
import '../shared/bd/dao/retorno_resposta_dao.dart';
import '../shared/dto/sinc_foto_dto.dart';
import '../shared/model/retorno.dart';
import '../shared/model/retorno_foto.dart';
import '../shared/model/retorno_resposta.dart';
import '../shared/service/retorno_service.dart';

class SincRetorno extends StatefulWidget {
  const SincRetorno({Key? key}) : super(key: key);

  @override
  State<SincRetorno> createState() => _SincRetornoState();
}

class _SincRetornoState extends State<SincRetorno> {
  int idUsuarioLogado = 0;
  String _mensagem = "";
  final Connectivity _connectivity = Connectivity() ;
  RetornoService service = RetornoService();

  Future<void> _sincronizarRetorno() async {
    setState(() {
      _mensagem="Enviando Respostas...";
    });
    RetornoDao dao = RetornoDao();
    List<Retorno> lista = [];
    lista = await  dao.retornoSincronismoPendente();
    List<int> listaIdServico=[];
    listaIdServico = await service.sincronizarRetorno(lista);
    await dao.updateSincronizado(listaIdServico);
  }

  Future<void> _sincronizarRetornoResposta() async {
    RetornoRespostaDao dao = RetornoRespostaDao();
    List<RetornoResposta> lista = await dao.retornoSincronismoPendente();
    List<int> listaIdServico=[];
    listaIdServico = await service.sincronizarRetornoResposta(lista);
    await dao.updateSincronizado(listaIdServico);
  }

  Future<void> _sincronizarRetornoFoto() async {
    setState(() {
      _mensagem="Enviando Fotos...";
    });
    RetornoFotoDao dao = RetornoFotoDao();
    List<RetornoFoto> lista = await dao.retornoSincronismoPendente();
    List<SincFotoDto> listaDto = await converterDto(lista);
    List<int> listaIdServico=[];
    listaIdServico = await service.sincronizarRetornoFoto(listaDto);
    await dao.updateSincronizado(listaIdServico);

  }

  Future<List<SincFotoDto>> converterDto(List<RetornoFoto> lista) async {
    List<SincFotoDto> listaDto = [];
    for(var retorno in lista){
      File imagem = File(retorno.path);
      Uint8List imagebytes = await imagem.readAsBytes();
      SincFotoDto dto = SincFotoDto(
          nome: retorno.nome,
          latitude: retorno.latitude,
          longitude: retorno.longitude,
          image: base64.encode(imagebytes),
          idServico: retorno.idServico,
          idQuestionario: retorno.idQuestionario,
          flagAssinatura: retorno.flagAssinatura,
          dataAtualizacao: retorno.dataAtualizacao,
          observacao: retorno.observacao
      );

      listaDto.add(dto);
    }
    return listaDto;
  }

  void checkConnection() async{
    var connectivityResult = await (Connectivity().checkConnectivity());
    if (connectivityResult == ConnectivityResult.none) {
      Navigator.pushNamed(context, "/home");
      ToastMesage.showToastError("Sem Internet!");
    }else{
      await _sincronizarRetorno().catchError((onError)=>{
        ToastMesage.showToastError("Erro ao sincronizar retorno"),
        Navigator.pushNamed(context, "/servico"),
      });
      await _sincronizarRetornoResposta().catchError((onError)=>{
        ToastMesage.showToastError("Erro ao sincronizar respostas"),
        Navigator.pushNamed(context, "/servico"),
      });
      await _sincronizarRetornoFoto().catchError((onError)=>{
        ToastMesage.showToastError("Erro ao sincronizar fotos"),
        Navigator.pushNamed(context, "/servico"),
      });
      Navigator.pushNamed(context, "/home");
    }
  }

  @override
  void didChangeDependencies() {
    // TODO: implement didChangeDependencies
    super.didChangeDependencies();
    checkConnection();
  }



  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
            title: Text("Carregamento"),
            backgroundColor:Color.fromRGBO(63, 81, 191, 1)
        ),
        body: Container(
            width: double.infinity,
            padding: EdgeInsets.only(top:200),
            // decoration: BoxDecoration(
            //   border: Border.all(width: 1,color:Colors.red)
            // ),
            child:Center(
              child: Column(
                // mainAxisAlignment: MainAxisAlignment.center,
                children:<Widget> [
                  Text(_mensagem,
                    style: TextStyle(
                      fontSize: 20,
                      fontStyle: FontStyle.normal,
                      fontWeight: FontWeight.bold,
                    ),
                    // textAlign: TextAlign.center,
                  ),
                  Padding(
                    padding: const EdgeInsets.only(top: 50),
                    child: CircularProgressIndicator(),
                  )
                ],
              ),
            )
        )
    );
  }
}
