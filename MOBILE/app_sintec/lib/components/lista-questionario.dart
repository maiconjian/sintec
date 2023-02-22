import 'package:app_sintec/shared/bd/dao/alternativa_dao.dart';
import 'package:app_sintec/shared/bd/dao/pergunta_dao.dart';
import 'package:app_sintec/shared/dto/pergunta_dto.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import '../shared/bd/dao/questionario_dao.dart';
import '../shared/dto/questionario_dto.dart';
import '../shared/model/alternativa.dart';
import '../shared/model/servico.dart';

class ListaQuestionario extends StatefulWidget {
  final Servico servico;
  final Function verificarFinalizacao;
  const ListaQuestionario(this.servico, this.verificarFinalizacao, {Key? key})
      : super(key: key);

  @override
  State<ListaQuestionario> createState() => _ListaQuestionarioState();
}

class _ListaQuestionarioState extends State<ListaQuestionario> {
  List<QuestionarioDto> _listaQuestionario = [];

  void _getListQuestionario(idTipoServico, idServico) {
    _listaQuestionario = [];
    int numFinalizado = 0;
    // int numNConf =0;
    QuestionarioDao questionarioDao = new QuestionarioDao();
    questionarioDao.listar(idTipoServico, idServico).then((response) => {
          for (int i = 0; i < response.length; i++)
            {
              setState(() {
                _listaQuestionario.add(response[i]);
                if (response[i].qtdPergunta == response[i].qtdRespondida) {
                  numFinalizado++;
                }
                // numNConf += response[i].qtdNConf;
              })
            },
          _verificarAndamentoInspecao(response.length, numFinalizado),
        });
  }

  void _verificarAndamentoInspecao(numPergunta, numFinalizado) {
    if (numPergunta == numFinalizado && numPergunta > 0) {
      widget.verificarFinalizacao(true);
    } else {
      widget.verificarFinalizacao(false);
    }
  }

  void _selecionarQuestionario(idQuestionario, tituloQuestionario, servico) {
    Navigator.pushNamed(context, "/questionario", arguments: {
      'idQuestionario': idQuestionario,
      'titulo': tituloQuestionario,
      "servico": servico
    });
  }

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    _getListQuestionario(widget.servico.idTipoServico, widget.servico.id);
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
          border: Border.all(width: 1, color: Colors.black),
          borderRadius: const BorderRadius.all(Radius.circular(10))
          // color: Colors.black,
          // borderRadius: BorderRadius.all(Radius.circular(10)),
          ),
      child: Padding(
        padding: const EdgeInsets.all(8.0),
        child: _listaQuestionario.length == 0
            ? const Text(
                "Questionários não encontrados!",
                textAlign: TextAlign.center,
                style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold,color: Colors.red),
              )
            : Column(
                children: _listaQuestionario
                    .map((e) => Padding(
                          padding: const EdgeInsets.all(4),
                          child: InkWell(
                            onTap: () => {
                              _selecionarQuestionario(e.idQuestionario,
                                  e.tituloQuestionario, widget.servico),
                            },
                            borderRadius: BorderRadius.circular(6),
                            splashColor: Theme.of(context).primaryColor,
                            child: Container(
                              decoration: BoxDecoration(
                                  borderRadius: BorderRadius.circular(6),
                                  gradient: LinearGradient(
                                      colors: [
                                        e.qtdRespondida == e.qtdPergunta
                                            ? Colors.green.withOpacity(0.5)
                                            : Colors.orange.withOpacity(0.5),
                                        e.qtdRespondida == e.qtdPergunta
                                            ? Colors.green
                                            : Colors.orange,
                                      ],
                                      begin: Alignment.topLeft,
                                      end: Alignment.bottomRight)),
                              child: Padding(
                                padding: const EdgeInsets.only(
                                    top: 18, bottom: 18, left: 10, right: 10),
                                child: Row(
                                  mainAxisAlignment:
                                      MainAxisAlignment.spaceBetween,
                                  children: <Widget>[
                                    Text(
                                      e.tituloQuestionario,
                                      style: const TextStyle(
                                          fontSize: 18,
                                          fontWeight: FontWeight.bold),
                                    ),
                                    Text(
                                      "${e.qtdRespondida}/${e.qtdPergunta}",
                                      style: const TextStyle(
                                          fontSize: 18,
                                          fontWeight: FontWeight.bold),
                                    ),
                                  ],
                                ),
                              ),
                            ),
                            // onTap: ()=>{
                            //   print(e.idQuestionario),
                            //   _getListaPergunta(e.idQuestionario),
                            // },
                          ),
                        ))
                    .toList(),
              ),
      ),
    );
  }
}
