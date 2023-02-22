import 'dart:async';

import 'package:app_sintec/core/toast.dart';
import 'package:app_sintec/shared/bd/dao/usuario_dao.dart';
import 'package:app_sintec/shared/model/usuario.dart';
import 'package:app_sintec/shared/service/desassociar_service.dart';
import 'package:connectivity/connectivity.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import '../shared/service/download_service.dart';


class CarregamentoUsuario extends StatefulWidget {
  const CarregamentoUsuario({Key? key}) : super(key: key);

  @override
  State<CarregamentoUsuario> createState() => _SincronismoState();
}

class _SincronismoState extends State<CarregamentoUsuario> {

  String _mensagem = "";
  final Connectivity _connectivity = Connectivity() ;

  void checkConnection() async{
    var connectivityResult = await (Connectivity().checkConnectivity());
    if (connectivityResult == ConnectivityResult.none) {
      ToastMesage.showToastError("Sem internet");
      Navigator.pushReplacementNamed(context,"/login");
    }else{
      getUsuarioMobile();
      DesassociarService desassociarService = DesassociarService();
      desassociarService.conferirDesassociados();
    }
  }

  void getUsuarioMobile() {
    setState(() {
      _mensagem="Carregando usuarios...";
    });
    DownloadService service = DownloadService();
    UsuarioDao dao = UsuarioDao();
    Usuario usuario;
    service.getUsuariosMobile().then((value) => {
      if(value != null){
        for(var user in value){
          usuario = Usuario(
              id: user['id'],
              nome: user['nome'],
              matricula: user['matricula'],
              pin: user['pin'],
              ativo: user['ativo']),
          dao.merge(usuario),
        },
      }else{
        ToastMesage.showToastError("Erro!")
      },
    Navigator.pushReplacementNamed(context,"/login")
    }).catchError((onError)=>{
    Navigator.pushReplacementNamed(context,"/login")
    });
  }

  @override
  void initState() {
    super.initState();
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
