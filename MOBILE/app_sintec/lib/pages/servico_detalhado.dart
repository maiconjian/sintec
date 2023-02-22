// ignore_for_file: prefer_typing_uninitialized_variables, prefer_const_constructors

import 'package:app_sintec/components/lista-questionario.dart'
    as listaQuestionario;
import 'package:app_sintec/shared/bd/dao/ocorrencia_dao.dart';
import 'package:app_sintec/shared/bd/dao/retorno_dao.dart';
import 'package:app_sintec/shared/bd/dao/retorno_resposta_dao.dart';
import 'package:app_sintec/shared/model/retorno.dart';
import 'package:app_sintec/shared/model/retorno_resposta.dart';
import 'package:app_sintec/shared/model/servico.dart';
import 'package:app_sintec/shared/model/helper/categoria_tp_servico.dart';
import 'package:app_sintec/shared/service/retorno_service.dart';
import 'package:app_sintec/shared/util/gps.dart';
import 'package:app_sintec/shared/util/info_device.dart';
import 'package:connectivity/connectivity.dart';
import 'package:app_sintec/shared/util/local_storage.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import '../components/app-bar-custom.dart';
import '../shared/model/ocorrencia.dart';

class ServicoDetalhado extends StatefulWidget {
  const ServicoDetalhado({Key? key}) : super(key: key);

  @override
  State<ServicoDetalhado> createState() => _ServicoDetalhadoState();
}

class _ServicoDetalhadoState extends State<ServicoDetalhado> {
  Gps gps = Gps();
  final Connectivity _connectivity = Connectivity() ;
  RetornoService service = RetornoService();
  OcorrenciaDao ocorrenciaDao = OcorrenciaDao();
  RetornoDao retornoDao = RetornoDao();
  List<Ocorrencia> _listaOcorrencia = [];
  var _selectedOcorrencia;
  bool _isFinalizado = false;

  void _verificarFinalizacao(bool isFinalizado){
    setState(() {
      _isFinalizado = isFinalizado;
    });
  }

  Future<void> _getListaOcorrencia() async {
    _listaOcorrencia = [];
    Ocorrencia ocorrencia;
    await ocorrenciaDao.listar().then((response) => {
          for (int i = 0; i < response.length; i++)
            {
              ocorrencia = Ocorrencia(
                  id: response[i].id,
                  descricao: response[i].descricao,
                  idContrato: response[i].idContrato,
                  ativo: response[i].ativo),
              setState(() {
                _listaOcorrencia.add(ocorrencia);
              })
            }
        });
  }

  void _filtrarListaOcorrencia(idContrato) {
    List<Ocorrencia> lista = _listaOcorrencia;
    _listaOcorrencia = [];
    for (int i = 0; i < lista.length; i++) {
      if (lista[i].idContrato == idContrato) {
        _listaOcorrencia.add(lista[i]);
      }
    }
  }

  onChangeDropdownOcorrencia(selectedTest) {
    setState(() {
      _selectedOcorrencia = selectedTest;
    });
  }

  Future<void> _salvarRetorno(servico) async {
    Map device = await InfoDevice.getInfo();
    Retorno retorno;
    LocalStorage.loadIdUsuario().then((value) => {
          retorno = Retorno(
              idUsuario: value,
              idServico: servico.id,
              idOcorrencia: _selectedOcorrencia,
              dataExecucao: DateFormat("yyyy-MM-dd HH:mm:ss").format(DateTime.now()),
              longitude: gps.getLongitude(),
              latitude: gps.getLatitude(),
              flagRacp: 0,
              modeloAparelho: device['modelo'],
              marcaAparelho: device['marca'],
              sincronizado: 0,
              ativo: 1),
          retornoDao.incluir(retorno).then((value) => {
            checkConnection(servico),
          })
        });
  }

  Future<void> sincronizarRetorno(servico) async {
      RetornoDao dao = RetornoDao();
      List<Retorno> lista = [];
      Retorno retorno  = await dao.buscarPorId(servico.id);
      lista.add(retorno);
      await service.sincronizarRetorno(lista);
      await dao.updateSincronizado(retorno.id);
  }

  void checkConnection(servico) async{
    var connectivityResult = await (Connectivity().checkConnectivity());
    if (connectivityResult == ConnectivityResult.none) {
      Navigator.pushNamed(context, "/servico");
    }else{
      sincronizarRetorno(servico);
      Navigator.pushNamed(context, "/servico");
    }
  }

  @override
  void initState() {
    // TODO: implement initState
    _getListaOcorrencia();
    super.initState();
  }

  @override
  void didChangeDependencies() {
    // TODO: implement didChangeDependencies
    gps.getVerificarPermissoes();
  }

  @override
  Widget build(BuildContext context) {
    final servico = ModalRoute.of(context)!.settings.arguments as Servico;
    final mediaQuery = MediaQuery.of(context);
    final PreferredSizeWidget appBar = AppBarCustom(
        title: Text("Detalhado"),
        routePrevious: "/servico",
        context: context);

    final appBarHeight = appBar.preferredSize.height;
    final statusBarHeight = mediaQuery.padding.top;
    final avaibleHeight =
        MediaQuery.of(context).size.height - appBarHeight - statusBarHeight;
    final avaibleWidth = MediaQuery.of(context).size.width;

    return WillPopScope(
      onWillPop: () async {
        Navigator.pushNamed(context, "/servico");
        return true;
      },
      child: Scaffold(
        appBar: appBar,
        // ignore: avoid_unnecessary_containers
        body: Container(
          child: SingleChildScrollView(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: <Widget>[
                Padding(
                  padding: const EdgeInsets.all(10),
                  child: Card(
                    elevation: 5,
                    color: Color.fromRGBO(63, 81, 191, 1),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: <Widget>[
                        Visibility(
                          visible: servico.idCategoriaTipoServico ==
                                      CategoriaTipoServico
                                          .CATEGORIA_FUNCIONARIO ||
                                  servico.idCategoriaTipoServico ==
                                      CategoriaTipoServico.CATEGORIA_VEICULO
                              ? true
                              : false,
                          child: Padding(
                            padding: const EdgeInsets.only(
                                left: 10, top: 5, bottom: 5),
                            child: Text(
                              "Nome: ${servico.nomeColaborador}",
                              textAlign: TextAlign.right,
                              style: TextStyle(
                                  color: Colors.white,
                                  fontSize: 17,
                                  fontWeight: FontWeight.bold),
                            ),
                          ),
                        ),
                        Visibility(
                          visible: servico.idCategoriaTipoServico ==
                                  CategoriaTipoServico.CATEGORIA_VEICULO
                              ? true
                              : false,
                          child: Padding(
                            padding: const EdgeInsets.only(
                                left: 10, top: 5, bottom: 5),
                            child: Text("Placa: ${servico.placaVeiculo}",
                                style: TextStyle(
                                    color: Colors.white,
                                    fontSize: 17,
                                    fontWeight: FontWeight.bold)),
                          ),
                        ),
                        Visibility(
                          visible: servico.idCategoriaTipoServico ==
                                  CategoriaTipoServico.CATEGORIA_VEICULO
                              ? true
                              : false,
                          child: Padding(
                            padding: const EdgeInsets.only(
                                left: 10, top: 5, bottom: 5),
                            child: Text("Modelo: ${servico.modeloVeiculo}",
                                style: TextStyle(
                                    color: Colors.white,
                                    fontSize: 17,
                                    fontWeight: FontWeight.bold)),
                          ),
                        ),
                        Visibility(
                          visible: servico.idCategoriaTipoServico ==
                                  CategoriaTipoServico.CATEGORIA_VEICULO
                              ? true
                              : false,
                          child: Padding(
                            padding: const EdgeInsets.only(
                                left: 10, top: 5, bottom: 5),
                            child: Text("Cor: ${servico.corVeiculo}",
                                style: TextStyle(
                                    color: Colors.white,
                                    fontSize: 17,
                                    fontWeight: FontWeight.bold)),
                          ),
                        ),
                        Visibility(
                            visible: servico.idCategoriaTipoServico ==
                                    CategoriaTipoServico.CATEGORIA_LOCAL
                                ? true
                                : false,
                            child: Padding(
                              padding: const EdgeInsets.only(
                                  left: 10, top: 5, bottom: 5),
                              child: Text("Imovel: ${servico.nomeImovel}",
                                  style: TextStyle(
                                      color: Colors.white,
                                      fontSize: 17,
                                      fontWeight: FontWeight.bold)),
                            )),
                        Visibility(
                            visible: servico.idCategoriaTipoServico ==
                                    CategoriaTipoServico.CATEGORIA_LOCAL
                                ? true
                                : false,
                            child: Padding(
                              padding: const EdgeInsets.only(
                                  left: 10, top: 5, bottom: 5),
                              child: Text("Bairro: ${servico.bairro}",
                                  style: TextStyle(
                                      color: Colors.white,
                                      fontSize: 17,
                                      fontWeight: FontWeight.bold)),
                            )),
                        Visibility(
                            visible: servico.idCategoriaTipoServico ==
                                    CategoriaTipoServico.CATEGORIA_LOCAL
                                ? true
                                : false,
                            child: Padding(
                              padding: const EdgeInsets.only(
                                  left: 10, top: 5, bottom: 5),
                              child: Text("Logradouro: ${servico.logradouro}",
                                  style: TextStyle(
                                      color: Colors.white,
                                      fontSize: 17,
                                      fontWeight: FontWeight.bold)),
                            )),
                        Visibility(
                            visible: servico.idCategoriaTipoServico ==
                                    CategoriaTipoServico.CATEGORIA_LOCAL
                                ? true
                                : false,
                            child: Padding(
                              padding: const EdgeInsets.only(
                                  left: 10, top: 5, bottom: 5),
                              child: Text("N°: ${servico.numeroLogradouro}",
                                  style: TextStyle(
                                      color: Colors.white,
                                      fontSize: 17,
                                      fontWeight: FontWeight.bold)),
                            )),
                        Visibility(
                            visible: true,
                            child: Padding(
                              padding: const EdgeInsets.only(
                                  left: 10, top: 5, bottom: 5),
                              child: Text(
                                  "Data Programada: ${DateFormat("dd/MM/yyyy").format(DateTime.parse(servico.dataProgramada))}",
                                  style: TextStyle(
                                      color: Colors.white,
                                      fontSize: 17,
                                      fontWeight: FontWeight.bold)),
                            )),
                      ],
                    ),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.all(5),
                  child: Text(
                    "Questionario",
                    textAlign: TextAlign.center,
                    style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                  ),
                ),
                Padding(
                    padding: EdgeInsets.all(12),
                    child: listaQuestionario.ListaQuestionario(servico,_verificarFinalizacao)),
                Padding(
                    padding: EdgeInsets.all(12),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.stretch,
                      children: <Widget>[
                        Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: ElevatedButton(
                              onPressed: _isFinalizado == true ? ()=> Navigator.pushNamed(context, "/assinatura",arguments: servico)
                               : null,
                              child: Padding(
                                padding: const EdgeInsets.all(8.0),
                                child: Padding(
                                  padding: const EdgeInsets.only(top: 10,bottom: 10,left: 10,right: 10),
                                  child: Text("Finalizar Inspeção",
                                    style: TextStyle(
                                        fontSize: 20
                                    ),),
                                ),
                              )
                          ),
                        ),
                      ],
                    )),
              ],
            ),
          ),
        ),
        floatingActionButtonLocation: FloatingActionButtonLocation.endTop,
        floatingActionButton:FloatingActionButton(
            backgroundColor: Colors.amber,
            child: Icon(Icons.warning, color: Colors.white,size: 35),
            onPressed: () =>{
              gps.getVerificarPermissoes(),
              _filtrarListaOcorrencia(servico.idContrato),
              showDialog(
                  context: context,
                  builder: (context) {
                    return AlertDialog(
                      title: Text("Ocorrência"),
                      content: DropdownButtonFormField(
                        items: _listaOcorrencia
                            .map((ocorrencia) {
                          return DropdownMenuItem(
                              value: ocorrencia.id,
                              child: Text(ocorrencia.descricao
                                  .toString()));
                        }).toList(),
                        onChanged: onChangeDropdownOcorrencia,
                        value: _selectedOcorrencia,
                        hint: Text("Selecione"),
                        isExpanded: true,
                        decoration: InputDecoration(
                            contentPadding: EdgeInsets.only(
                                top: 10,
                                bottom: 10,
                                left: 10),
                            label: Text("Ocorrência"),
                            border: OutlineInputBorder(
                                borderRadius:
                                BorderRadius.circular(
                                    6))),
                      ),
                      actions: <Widget>[
                        Row(
                          mainAxisAlignment:
                          MainAxisAlignment.center,
                          children: [
                            Padding(
                              padding:
                              const EdgeInsets.all(8.0),
                              child: ElevatedButton(
                                  onPressed: () {
                                     _salvarRetorno(servico);
                                  },
                                  child: Text("Confirmar")),
                            ),
                            Padding(
                              padding:
                              const EdgeInsets.all(8.0),
                              child: ElevatedButton(
                                  onPressed: () {
                                    Navigator.pop(context);
                                  },
                                  child: Text("Cancelar")),
                            ),
                          ],
                        )
                      ],
                    );
                  })
            }
        ),
      ),
    );
  }
}
