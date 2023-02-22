// ignore_for_file: unused_field, prefer_final_fields, unrelated_type_equality_checks, prefer_const_literals_to_create_immutables

import 'package:app_sintec/core/toast.dart';
import 'package:app_sintec/shared/bd/dao/retorno_resposta_dao.dart';
import 'package:app_sintec/shared/model/retorno_resposta.dart';
import 'package:app_sintec/widget/input-image.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import '../components/app-bar-custom.dart';
import '../core/spinner.dart';
import '../shared/bd/dao/alternativa_dao.dart';
import '../shared/bd/dao/pergunta_dao.dart';
import '../shared/dto/pergunta_dto.dart';
import '../shared/model/alternativa.dart';
import '../shared/model/servico.dart';
import '../shared/util/gps.dart';

class Questionario extends StatefulWidget {
  const Questionario({Key? key}) : super(key: key);

  @override
  State<Questionario> createState() => _QuestionarioState();
}

class _QuestionarioState extends State<Questionario> {

  final _loading = ValueNotifier(false);
  late Servico _servico;
  List<Alternativa> _listaAlternativa = [];
  List<PerguntaDto> _listaPergunta = [];
  List<int?> _listaIdAlternativaResposta = [];
  List<TextEditingController> _listaRespostaDescritiva = [];

  void _iniciarListaResposta(List<PerguntaDto> lista) {
    // print(lista[0].idAlternativa);
    for (int i = 0; i < lista.length; i++) {
      _listaIdAlternativaResposta.add(lista[i].idAlternativa);
      TextEditingController _controll = new TextEditingController();
      _controll.text = lista[i].resposta ?? "";
      _listaRespostaDescritiva.add(_controll);
    }
  }

  Future<List<PerguntaDto>> _getListaPergunta(idQuestionario, idServico) async {
    PerguntaDao perguntaDao = PerguntaDao();
    List<PerguntaDto> listaPergunta = [];
    await perguntaDao.listar(idQuestionario, idServico).then((response) => {
          setState(() {
            listaPergunta = PerguntaDto.fromSQLiteList(response);
          })
        });
    return listaPergunta;
  }

  Future _getListaAlternativa(idQuestionario) async {
    AlternativaDao alternativaDao = AlternativaDao();
    await alternativaDao
        .listarAlternativaPergunta(idQuestionario)
        .then((response) => {
              setState(() {
                _listaAlternativa = Alternativa.fromSQLiteList(response);
              }),
            });
  }

  void _salvarIdResposta(indice, val) {
    setState(() {
      _listaPergunta[indice].idAlternativa = val;
    });
  }

  Future<void> _salvarResposta(Servico servico) async {
    Gps gps = Gps();
    setState(() {
      _loading.value=true;
    });
    RetornoRespostaDao dao = new RetornoRespostaDao();
    for (int i = 0; i < _listaPergunta.length; i++) {
        if (_listaIdAlternativaResposta[i] != null ||
            _listaRespostaDescritiva[i].text != "") {
          RetornoResposta retorno =  RetornoResposta(
              id: _listaPergunta[i].idRetornoResposta,
              idServico: _listaPergunta[i].idServico,
              idPergunta: _listaPergunta[i].idPergunta,
              idAlternativa: _listaIdAlternativaResposta[i],
              latitude: "",
              longitude: "",
              resposta: _listaRespostaDescritiva[i].text == "" ? null : _listaRespostaDescritiva[i].text ,
              dataExecucao: DateFormat("yyyy-MM-dd HH:mm:ss").format(DateTime.now()),
              sincronizado: 0);
          await dao.merge(retorno).then((value) => {
          }).catchError((onError) => {
                ToastMesage.showToastError("Erro ao salvar resposta ${_listaPergunta[i].ordemApresentacao}"),
                  setState(() => {
                  _loading.value=false,
                  })
              });
        }
        if (i == _listaPergunta.length - 1) {
          Navigator.pushNamed(context, "/servico-detalhado", arguments: servico);
          setState(() => {
            _loading.value=false,
          });
        }

    }
  }

  @override
  Future<void> didChangeDependencies() async {
    super.didChangeDependencies();
    final args = ModalRoute.of(context)!.settings.arguments as Map;
    _getListaAlternativa(args["idQuestionario"]);
  }

  @override
  Widget build(BuildContext context) {
    final args = ModalRoute.of(context)!.settings.arguments as Map;
    _servico = args["servico"];
    final mediaQuery = MediaQuery.of(context);
    final PreferredSizeWidget appBar = AppBarCustom(
        title: Text(args["titulo"]),
        routePrevious: "/servico-detalhado",
        argument: args["servico"],
        context: context);

    final appBarHeight = appBar.preferredSize.height;
    final statusBarHeight = mediaQuery.padding.top;
    final avaibleHeight =
        MediaQuery.of(context).size.height - appBarHeight - statusBarHeight;
    final avaibleWidth = MediaQuery.of(context).size.width;

    return WillPopScope(
      onWillPop: () async {
        Navigator.pushNamed(context, "/servico-detalhado", arguments: args["servico"]);
        return true;
      },
      child: Scaffold(
        appBar: appBar,
        body: Center(
          child: ValueListenableBuilder(
              valueListenable: _loading,
              builder: (context, value, child) {
                if (value == true) {
                  return Spinner.showSpinner();
                } else {
                  return SizedBox(
                    height: double.infinity,
                    child: SingleChildScrollView(
                        child: Column(
                      crossAxisAlignment: CrossAxisAlignment.stretch,
                      children: [
                        FutureBuilder<List<PerguntaDto>>(
                            future: _getListaPergunta(
                                args["idQuestionario"], args["servico"].id),
                            builder: (context, AsyncSnapshot snapshot) {
                              if (snapshot.hasData && !snapshot.hasError) {
                                _listaPergunta = snapshot.data;
                                _iniciarListaResposta(_listaPergunta);
                                return Column(
                                  children: [
                                    SizedBox(
                                      height: avaibleHeight * 0.850,
                                      child: ListView.builder(
                                          itemCount: snapshot.data?.length,
                                          itemBuilder: (context, indice) {
                                            return Padding(
                                              padding: const EdgeInsets.only(top: 2,bottom: 1, left: 5,right: 5),
                                              child: Card(
                                                color: Color.fromRGBO(220,220,220,1),
                                                child: Column(
                                                  crossAxisAlignment:
                                                      CrossAxisAlignment.stretch,
                                                  children: <Widget>[
                                                    Padding(
                                                      padding: const EdgeInsets.only(left: 10, top: 15,right: 10),
                                                      child: Text(
                                                        _listaPergunta[indice].enunciadoPergunta.toString(),
                                                        textAlign: TextAlign.left,
                                                        style: TextStyle(
                                                          // color: Colors.white,
                                                          fontWeight: FontWeight.bold,
                                                          fontSize: 13
                                                        ),
                                                      ),
                                                    ),
                                                    Card(
                                                      color: Colors.white,
                                                      child: Padding(
                                                          padding: EdgeInsets.all(1),
                                                          child: _listaPergunta[indice].flagMultiplaEscolha == 1
                                                              ? Column(
                                                                  children: _listaAlternativa
                                                                      .map((alternativa) => Padding(
                                                                          padding: EdgeInsets.only(top: 1, bottom: 1),
                                                                          child: alternativa.idPergunta == _listaPergunta[indice].idPergunta
                                                                              ? RadioListTile(
                                                                                  // tileColor: Colors.white,
                                                                                  // contentPadding: const EdgeInsets.all(0.05),
                                                                                  value: alternativa.id,
                                                                                  title: Text(alternativa.descricao,style: TextStyle(fontSize: 13)),
                                                                                  groupValue: _listaIdAlternativaResposta[indice],
                                                                                  onChanged:
                                                                                      (val) => {
                                                                                    setState(() =>
                                                                                        {
                                                                                          _listaIdAlternativaResposta[indice] = val as int,
                                                                                          _salvarIdResposta(indice, val),
                                                                                        }),
                                                                                  },
                                                                                )
                                                                              : null),
                                                                  )
                                                                      .toList(),
                                                                )
                                                              : Container(
                                                                decoration: BoxDecoration(
                                                                  borderRadius: BorderRadius.circular(5),
                                                                  color: Colors.white,
                                                                ),
                                                                child: Padding(
                                                                  padding: const EdgeInsets.all(8.0),
                                                                  child: TextField(
                                                                      controller: _listaRespostaDescritiva[indice],
                                                                      maxLength: 100,
                                                                      maxLines: 1,
                                                                      decoration: InputDecoration(
                                                                          border: OutlineInputBorder(
                                                                              borderSide:
                                                                                  BorderSide(
                                                                                      width: 1),
                                                                          ),
                                                                      ),
                                                                    ),
                                                                ),
                                                              ),
                                                      ),
                                                    ),
                                                  ],
                                                ),
                                              ),
                                            );
                                          }),
                                    ),
                                    Container(
                                      margin: EdgeInsets.only(top: avaibleHeight*0.015),
                                      child: Padding(
                                        padding: const EdgeInsets.all(10),
                                        child: Row(
                                          mainAxisAlignment:
                                              MainAxisAlignment.spaceAround,
                                          crossAxisAlignment:
                                              CrossAxisAlignment.center,
                                          children: [
                                            SizedBox(
                                              width: avaibleWidth * 0.250,
                                              height: avaibleHeight * 0.080,
                                              child: ElevatedButton(
                                                onPressed: () => {
                                                  _salvarResposta(args["servico"])
                                                },
                                                style: ButtonStyle(
                                                  shape:
                                                      MaterialStateProperty.all(
                                                          RoundedRectangleBorder(
                                                    borderRadius:
                                                        BorderRadius.circular(15),
                                                  )),
                                                ),
                                                child: const Icon(
                                                  Icons.done,
                                                  size: 50,
                                                ),
                                              ),
                                            ),
                                            SizedBox(
                                              width: avaibleWidth * 0.250,
                                              height: avaibleHeight * 0.080,
                                              child: ElevatedButton(
                                                onPressed: () => {
                                                  showDialog(
                                                      context: context,
                                                      builder: (context) {
                                                        return InputImage(
                                                            avaibleHeight,
                                                            avaibleWidth,
                                                            args["servico"].id,
                                                            args["idQuestionario"]);
                                                      })
                                                },
                                                style: ButtonStyle(
                                                  shape:
                                                      MaterialStateProperty.all(
                                                    RoundedRectangleBorder(
                                                      borderRadius:
                                                          BorderRadius.circular(
                                                              15),
                                                    ),
                                                  ),
                                                ),
                                                child: const Icon(
                                                    Icons.photo_camera,
                                                    size: 50),
                                              ),
                                            ),
                                          ],
                                        ),
                                      ),
                                    )
                                  ],
                                );
                              } else {
                                return const Text("Carregando...");
                              }
                            }),
                      ],
                    )),
                  );
                }
              }),
        ),
      ),
    );

    // return Scaffold(
    //   appBar: appBar,
    //   body: Container(
    //     child: Center(
    //       child: ValueListenableBuilder(
    //           valueListenable: _loading,
    //           builder: (context, value, child) {
    //             if (value == true) {
    //               return Spinner.showSpinner();
    //             } else {
    //               return Container(
    //                 height: double.infinity,
    //                 child: SingleChildScrollView(
    //                     child: Column(
    //                   crossAxisAlignment: CrossAxisAlignment.stretch,
    //                   children: <Widget>[
    //                     Column(
    //                       children: _listaPergunta
    //                           .map((pergunta) => Column(
    //                                 crossAxisAlignment:
    //                                     CrossAxisAlignment.stretch,
    //                                 children: <Widget>[
    //                                   Padding(
    //                                     padding: EdgeInsets.only(
    //                                         left: 8,
    //                                         right: 5,
    //                                         top: 20,
    //                                         bottom: 5),
    //                                     child: Container(
    //                                       child: Text(
    //                                         pergunta.enunciadoPergunta,
    //                                         style: TextStyle(fontSize: 15),
    //                                       ),
    //                                     ),
    //                                   ),
    //                                   Padding(
    //                                       padding: EdgeInsets.all(2),
    //                                       child:
    //                                           pergunta.flagMultiplaEscolha == 1
    //                                               ? Column(
    //                                                   children:
    //                                                       _listaAlternativa
    //                                                           .map((e) =>
    //                                                               Container(
    //                                                                 child: Padding(
    //                                                                     padding: EdgeInsets.all(1),
    //                                                                     child: e.idPergunta == pergunta.idPergunta
    //                                                                         ? RadioListTile(
    //                                                                             contentPadding: EdgeInsets.symmetric(horizontal: 0.0),
    //                                                                             value: e.id,
    //                                                                             title: Text(e.descricao),
    //                                                                             groupValue: pergunta.idAlternativa,
    //                                                                             onChanged: (val) => {
    //                                                                               setState(() => {
    //                                                                                     pergunta.idAlternativa = val as int
    //                                                                                   })
    //                                                                             },
    //                                                                           )
    //                                                                         : null),
    //                                                               ))
    //                                                           .toList(),
    //                                                 )
    //                                               : Padding(
    //                                                   padding: EdgeInsets.only(
    //                                                       top: 0,
    //                                                       bottom: 5,
    //                                                       left: 10,
    //                                                       right: 10),
    //                                                   child: TextField(
    //                                                     controller:
    //                                                         _controllerRespostaDiscursiva,
    //                                                     // autofocus: false,
    //                                                     maxLength: 100,
    //                                                     decoration:
    //                                                         InputDecoration(
    //                                                       contentPadding:
    //                                                           EdgeInsets
    //                                                               .fromLTRB(16,
    //                                                                   5, 16, 5),
    //                                                       // icon: Icon(Icons.verified_user,color: Color.fromRGBO(63, 81, 191, 1)),bdfvfd
    //                                                     ),
    //                                                   ))),
    //                                 ],
    //                               ))
    //                           .toList(),
    //                     ),
    //                     Padding(
    //                       padding: EdgeInsets.all(15),
    //                       child: Row(
    //                         mainAxisAlignment: MainAxisAlignment.spaceAround,
    //                         crossAxisAlignment: CrossAxisAlignment.center,
    //                         children: [
    //                           Container(
    //                             width: avaibleWidth * 0.3,
    //                             height: avaibleHeight * 0.1,
    //                             child: ElevatedButton(
    //                                 onPressed: () => {
    //                                       showDialog(
    //                                           context: context,
    //                                           builder: (context) {
    //                                             return InputImage(avaibleHeight,
    //                                                 avaibleWidth);
    //                                           })
    //                                     },
    //                                 child: Icon(Icons.photo_camera, size: 50),
    //                                 style: ButtonStyle(
    //                                     shape: MaterialStateProperty.all(
    //                                         RoundedRectangleBorder(
    //                                             borderRadius:
    //                                                 BorderRadius.circular(
    //                                                     15))))),
    //                           ),
    //                           Container(
    //                             width: avaibleWidth * 0.3,
    //                             height: avaibleHeight * 0.1,
    //                             child: ElevatedButton(
    //                                 onPressed: () => {},
    //                                 child: Icon(
    //                                   Icons.done,
    //                                   size: 50,
    //                                 ),
    //                                 style: ButtonStyle(
    //                                     shape: MaterialStateProperty.all(
    //                                         RoundedRectangleBorder(
    //                                             borderRadius:
    //                                                 BorderRadius.circular(
    //                                                     15))))),
    //                           ),
    //                         ],
    //                       ),
    //                     ),
    //                   ],
    //                 )),
    //               );
    //             }
    //           }),
    //     ),
    //   ),
    //   // floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
    //   // floatingActionButton: Stack(
    //   //   fit: StackFit.expand,
    //   //   children:<Widget> [
    //   //     Positioned(
    //   //       left: avaibleWidth*0.750,
    //   //       bottom: avaibleHeight*0.001,
    //   //       child: FloatingActionButton(
    //   //         onPressed: () {},
    //   //         child: const Icon(
    //   //           Icons.save,
    //   //           size: 40,
    //   //         ),
    //   //       ),
    //   //     ),
    //   //   ],
    //   // ),
    // );
  }
}
