import 'package:app_sintec/core/toast.dart';
import 'package:app_sintec/shared/bd/dao/alternativa_dao.dart';
import 'package:app_sintec/shared/bd/dao/ocorrencia_dao.dart';
import 'package:app_sintec/shared/bd/dao/pergunta_dao.dart';
import 'package:app_sintec/shared/bd/dao/questionario_dao.dart';
import 'package:app_sintec/shared/model/ocorrencia.dart';
import 'package:app_sintec/shared/model/questionario.dart';
import 'package:app_sintec/shared/service/download_service.dart';
import 'package:app_sintec/shared/util/local_storage.dart';
import 'package:connectivity/connectivity.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import '../shared/model/alternativa.dart';
import '../shared/model/pergunta.dart';

class CarregamentoQuestionario extends StatefulWidget {
  const CarregamentoQuestionario({Key? key}) : super(key: key);

  @override
  State<CarregamentoQuestionario> createState() => _CarregamentoQuestionarioState();
}

class _CarregamentoQuestionarioState extends State<CarregamentoQuestionario> {

  int idUsuarioLogado = 0;
  String _mensagem = "";
  final Connectivity _connectivity = Connectivity() ;



  Future<void> getOcorrenciaMobile() async{
    setState(() {
      _mensagem="Carregando ocorrência...";
    });
    DownloadService service = DownloadService();
    OcorrenciaDao dao = OcorrenciaDao();
    Ocorrencia ocorrencia;
    service.getOcorrenciaMobile(idUsuarioLogado).then((response) => {
      for(var value in response){
         ocorrencia = Ocorrencia(
            id: value['id'],
            descricao: value['descricao'],
            idContrato: value['idContrato'],
            ativo: value['ativo']),
        dao.merge(ocorrencia)
      }
    }).catchError((onError)=>{
        ToastMesage.showToastError("Erro ao fazer download!"),
        Future.delayed(Duration(milliseconds: 350),(){
          Navigator.pushReplacementNamed(context,"/home");
        })
    });
  }

  Future<void> getQuestionarioMobile() async{
    setState(() {
      _mensagem="Carregando Questionario...";
    });
    DownloadService service = DownloadService();
    QuestionarioDao dao = QuestionarioDao();
    Questionario questionario;
    service.getQuestionarioMobile(idUsuarioLogado).then((response) => {
      for(var value in response){
        questionario = Questionario(
            id: value['id'],
            titulo: value['titulo'],
            idTipoServico: value['idTipoServico'],
            ordemApresentacao: value['ordemApresentacao'],
            ativo: value['ativo']),
        dao.merge(questionario)
      }
    }).catchError((onError)=>{
      ToastMesage.showToastError("Erro ao fazer download!"),
      Future.delayed(Duration(milliseconds: 350),(){
        Navigator.pushReplacementNamed(context,"/home");
      })
    });
  }

  Future<void> getPerguntaMobile() async{
    setState(() {
      _mensagem="Carregando Pergunta...";
    });
    DownloadService service = DownloadService();
    PerguntaDao dao = PerguntaDao();
    Pergunta pergunta;
    service.getPerguntaMobile(idUsuarioLogado).then((response) => {
      for(var value in response){
        pergunta = Pergunta(
            id: value['id'],
            enunciado: value['enunciado'],
            flagFoto: value['flagFoto'],
            flagObrigatorio: value['flagObrigatorio'],
            flagMultiplaEscolha: value['flagMultiplaEscolha'],
            idQuestionario: value['idQuestionario'],
            ordemApresentacao: value['ordemApresentacao'],
            ativo: value['ativo']),
        dao.merge(pergunta)
      }
    }).catchError((onError)=>{
      ToastMesage.showToastError("Erro ao fazer download!"),
      Future.delayed(Duration(milliseconds: 350),(){
        Navigator.pushReplacementNamed(context,"/home");
      })
    });
  }

  Future<void> getAlternativaMobile() async{
    setState(() {
      _mensagem="Carregando Alternativas...";
    });
    DownloadService service = DownloadService();
    AlternativaDao dao = AlternativaDao();
    Alternativa alternativa;
    service.getAlternativaMobile(idUsuarioLogado).then((response) => {
      for(var value in response){
        alternativa = Alternativa(
            id: value['id'],
            idPergunta: value['idPergunta'],
            descricao: value['descricao'],
            flagNconf: value['flagNConf'],
            ordemApresentacao: value['ordemApresentacao'],
            ativo: value['ativo']),
        dao.merge(alternativa)
      }
    }).catchError((onError)=>{
      ToastMesage.showToastError("Erro ao fazer download!"),
      Future.delayed(Duration(milliseconds: 350),(){
        Navigator.pushReplacementNamed(context,"/home");
      })
    });
  }

  void checkConnection() async{
    idUsuarioLogado = await LocalStorage.loadIdUsuario();
    var connectivityResult = await (Connectivity().checkConnectivity());
    if (connectivityResult == ConnectivityResult.none) {
      ToastMesage.showToastError("Sem internet");
      Navigator.pushReplacementNamed(context,"/home");
    }else{
      await getOcorrenciaMobile();
      await getQuestionarioMobile();
      await getPerguntaMobile();
      await getAlternativaMobile();
      Navigator.pushReplacementNamed(context,"/home");
    }
  }

  @override
  void initState() {
    // TODO: implement initState
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
