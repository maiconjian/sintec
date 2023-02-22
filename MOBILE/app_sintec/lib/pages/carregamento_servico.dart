import 'package:app_sintec/shared/bd/dao/servico_dao.dart';
import 'package:app_sintec/shared/dto/confirmacao_download_servico.dart';
import 'package:app_sintec/shared/service/retorno_service.dart';
import 'package:connectivity/connectivity.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import '../core/toast.dart';
import '../shared/model/servico.dart';
import '../shared/service/download_service.dart';
import '../shared/util/local_storage.dart';

class CarregamentoServico extends StatefulWidget {
  const CarregamentoServico({Key? key}) : super(key: key);

  @override
  State<CarregamentoServico> createState() => _CarregamentoServicoState();
}

class _CarregamentoServicoState extends State<CarregamentoServico> {
  int idUsuarioLogado = 0;
  String _mensagem = "";
  final Connectivity _connectivity = Connectivity() ;

  Future<void> getServicoMobile() async{
    setState(() {
      _mensagem="Carregando Servico Liberado...";
    });
    DownloadService service = DownloadService();
    List<int> listaIdServico=[];
    ServicoDao dao = ServicoDao();
    Servico servico;
    service.getServicoLiberadoMobile(idUsuarioLogado).then((response) async => {
      for(var value in response){
        servico =Servico(
            id: value['id'],
            codigo: value['codigo'].toString(),
            dataRef: value['dataRef'],
            idContrato: value['idContrato'],
            idRegional: value['idRegional'],
            nomeRegional: value['nomeRegional'],
            idCategoriaTipoServico: value['idCategoriaTipoServico'],
            nomeCategoriaTipoServico: value['nomeCategoriaTipoServico'],
            idTipoServico: value['idTipoServico'],
            nomeTipoServico: value['nomeTipoServico'],
            idImovel: value['idImovel'],
            bairro: value['bairro'],
            logradouro: value['logradouro'],
            numeroLogradouro: value['numeroLogradouro'],
            idVeiculo: value['idVeiculo'],
            placaVeiculo: value['placa'],
            corVeiculo: value['cor'],
            modeloVeiculo: value['modole'],
            idColaborador: value['idColaborador'],
            nomeColaborador: value['nomeColaborador'],
            cnh: value['cnh'],
            categoriaCnh: value['categoriaCnh'],
            validadeCnh: value['validadeCnh'],
            dataProgramada: value['dataProgramada'],
            idUsuario: value['idUsuario']),
        listaIdServico.add(servico.id),
        dao.merge(servico)
      },
      await confirmarDownloadServico(listaIdServico),
    }).catchError((onError)=>{
      ToastMesage.showToastError("Erro ao fazer download!"),
      Future.delayed(Duration(milliseconds: 250),(){
        Navigator.pushReplacementNamed(context,"/home");
      })
    });
  }

  Future<void> confirmarDownloadServico(listaIdServico) async{
    DownloadService service =DownloadService();
    int idUsuarioLogado = await LocalStorage.loadIdUsuario();
    await service.confirmarDownlaodServico(listaIdServico, idUsuarioLogado);
  }

  void checkConnection() async{
    idUsuarioLogado = await LocalStorage.loadIdUsuario();
    var connectivityResult = await (Connectivity().checkConnectivity());
    if (connectivityResult == ConnectivityResult.none) {
      ToastMesage.showToastError("Sem internet");
      Navigator.pushReplacementNamed(context,"/home");
    }else{
      await getServicoMobile();
      Navigator.pushReplacementNamed(context,"/home");
    }
  }

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
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
