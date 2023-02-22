import 'dart:convert';
import 'dart:core';
import 'dart:io';
import 'dart:typed_data';

import 'package:app_sintec/core/toast.dart';
import 'package:app_sintec/shared/bd/dao/retorno_dao.dart';
import 'package:app_sintec/shared/bd/dao/retorno_foto_dao.dart';
import 'package:app_sintec/shared/bd/dao/retorno_resposta_dao.dart';
import 'package:app_sintec/shared/model/retorno.dart';
import 'package:app_sintec/shared/util/file_manager.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

import '../components/app-bar-custom.dart';
import '../core/spinner.dart';
import '../shared/bd/dao/servico_dao.dart';
import '../shared/dto/filtro_servico.dart';
import '../shared/dto/regional-dto.dart';
import '../shared/dto/servico_dto.dart';
import '../shared/dto/sinc_foto_dto.dart';
import '../shared/dto/tipo_servico_dto.dart';
import '../shared/model/helper/categoria_tp_servico.dart';
import '../shared/model/retorno_foto.dart';
import '../shared/model/retorno_resposta.dart';
import '../shared/model/servico.dart';
import '../shared/util/local_storage.dart';

class Backup extends StatefulWidget {
  const Backup({Key? key}) : super(key: key);

  @override
  State<Backup> createState() => _BackupState();
}

class _BackupState extends State<Backup> {
  final _loading = ValueNotifier(false);
  RetornoDao _retornoDao = RetornoDao();
  RetornoRespostaDao _retornoRespostaDao = RetornoRespostaDao();
  RetornoFotoDao _retornoFotoDao = RetornoFotoDao();
  ServicoDao servicoDao = new ServicoDao();
  List<ServicoDto> _listaServico = [];
  List<RegionalDto> _listaRegional = [];
  List<TipoServicoDto> _listaTipoServico = [];
  bool _isPesquisando = false;
  var _selectedRegional;
  var _selectedTipoServico;

  Future<void> _getPesquisar() async {
    _loading.value = true;
    _isPesquisando = true;
    Servico servico;
    _listaServico = [];
    int idRegional = _selectedRegional == null ? 0 : _selectedRegional;
    int idTipoServico = _selectedTipoServico == null ? 0 : _selectedTipoServico;
    _listaServico = await servicoDao.listaRealizados(idRegional, idTipoServico);
    _loading.value = false;
    _isPesquisando = false;

  }

  _getListaRegional() {
    _listaRegional = [];
    RegionalDto regional;
    servicoDao.listaRegional().then((response) => {
      for (int i = 0; i < response.length; i++)
        {
          regional = RegionalDto(
              id: response[i].id,
              nome: response[i].nome,
              idContrato: response[i].idContrato),
          setState(() {
            _listaRegional.add(regional);
          })
        }
    });
  }

  _getListaTipoServico(idRegional) {
    _listaTipoServico = [];
    TipoServicoDto tipoServico;
    servicoDao.listaTipoServico(idRegional).then((response) => {
      for (int i = 0; i < response.length; i++)
        {
          tipoServico = TipoServicoDto(
              id: response[i].id, nome: response[i].nome),
          setState(() {
            _listaTipoServico.add(tipoServico);
          })
        }
    });
  }

  onChangeDropdownRegional(selectedTest) {
    setState(() {
      _selectedRegional = selectedTest;
      _selectedTipoServico = null;
    });
    _getListaTipoServico(_selectedRegional);
  }

  onChangeDropdownTipoServico(selectedTest) {
    setState(() {
      _selectedTipoServico = selectedTest;
    });
  }

  String _getTituloLista(ServicoDto servico) {

    if (servico.idCategoriaTipoServico == CategoriaTipoServico.CATEGORIA_VEICULO) {
      return servico.placaVeiculo.toString();
    }
    if (servico.idCategoriaTipoServico == CategoriaTipoServico.CATEGORIA_LOCAL) {
      return servico.nomeImovel.toString();
    }
    if (servico.idCategoriaTipoServico == CategoriaTipoServico.CATEGORIA_FUNCIONARIO) {
      return servico.nomeColaborador.toString();
    }
    return "";
  }

  bool _desabilitarBotaoPesquisar() {
    if (_isPesquisando ||
        _selectedRegional == null ||
        _selectedTipoServico == null) {
      return true;
    } else {
      return false;
    }
  }

  Future<List<SincFotoDto>> converterDto(List<RetornoFoto> lista) async {
    List<SincFotoDto> listaDto = [];
    for(var retorno in lista){
      File imagem = File(retorno.path);
      Uint8List imagebytes = await imagem.readAsBytes();
      // print(base64.encode(imagebytes));
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


  Future<void> _selectServico(BuildContext context, ServicoDto servico) async {
    setState(() {
      _loading.value = true;
    });
    Retorno retorno = await _retornoDao.buscarPorId(servico.id);
    List<RetornoResposta> listaRetornoResposta = await _retornoRespostaDao.buscarRespostas(servico.id);
    List<RetornoFoto> listaRetornoFoto = await _retornoFotoDao.buscarFotos(servico.id);
    List<SincFotoDto> listaSincFoto = await converterDto(listaRetornoFoto);

    String retornoJson = jsonEncode(retorno.toJson());
    String retornoRespostaJson =  jsonEncode(listaRetornoResposta.map((e) => e.toJson()).toList()).toString();
    String retornoFotoJson = jsonEncode(listaSincFoto.map((e) => e.toJson()).toList()).toString();

    await FileManager().writeJsonFile(retornoJson, "${servico.codigo}_retorno");
    await FileManager().writeJsonFile(retornoRespostaJson, "${servico.codigo}_resposta");
    await FileManager().writeJsonFile(retornoFotoJson, "${servico.codigo}_foto");

    setState(() {
      _loading.value = false;
      ToastMesage.showToastSucess("Backup salvo em Downloads!");
    });


  }

  // await FileManager().writeJsonFile(strBody, nomeArquivo)

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    _getListaRegional();
    LocalStorage.loadFiltroServico().then((value) => {
      if(value?.idRegional !=null && value?.idTipoServico != null){
        _selectedRegional = value?.idRegional,
        _getListaTipoServico(_selectedRegional),
        _selectedTipoServico = value?.idTipoServico,
        _getPesquisar(),
      }
    });
  }


  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () async {
        Navigator.pushNamed(context, "/home");
        return true;
      },
      child: Scaffold(
          appBar: AppBarCustom(title: Text("Backup"), routePrevious: "/home", context: context),
          body: Container(
            width: double.infinity,
            padding: EdgeInsets.only(left: 15, right: 15),
            child: Center(
                child: ValueListenableBuilder(
                    valueListenable: _loading,
                    builder: (context, value, child) {
                      if (value == true) {
                        return Spinner.showSpinner();
                      } else {
                        return Column(
                          crossAxisAlignment: CrossAxisAlignment.stretch,
                          children: <Widget>[
                            Padding(
                              padding: EdgeInsets.only(top: 15),
                              child: DropdownButtonFormField(
                                items: _listaRegional.map((regional) {
                                  return DropdownMenuItem(
                                      value: regional.id,
                                      child: Text(regional.nome));
                                }).toList(),
                                onChanged: onChangeDropdownRegional,
                                value: _selectedRegional,
                                hint: const Text("Selecione"),
                                isExpanded: true,
                                decoration: InputDecoration(
                                    contentPadding: EdgeInsets.only(
                                        top: 10, bottom: 10, left: 10),
                                    label: const Text("Regional"),
                                    border: OutlineInputBorder(
                                        borderRadius: BorderRadius.circular(6))),
                              ),
                            ),
                            Padding(
                              padding: EdgeInsets.only(top: 10),
                              child: DropdownButtonFormField(
                                items: _listaTipoServico.map((tp) {
                                  return DropdownMenuItem(
                                      value: tp.id, child: Text(tp.nome));
                                }).toList(),
                                onChanged: onChangeDropdownTipoServico,
                                value: _selectedTipoServico != null ? _selectedTipoServico : null,
                                hint: const Text("Selecione"),
                                isExpanded: true,
                                decoration: InputDecoration(
                                    contentPadding: const EdgeInsets.only(
                                        top: 10, bottom: 10, left: 10),
                                    label: Text("Tipo Serviço"),
                                    border: OutlineInputBorder(
                                        borderRadius: BorderRadius.circular(6))),
                              ),
                            ),
                            Padding(
                              padding: EdgeInsets.only(top: 10),
                              child: ElevatedButton(
                                onPressed: _desabilitarBotaoPesquisar()
                                    ? null
                                    : () => _getPesquisar(),
                                child: Padding(
                                  padding: EdgeInsets.only(top: 13,bottom: 13),
                                  child: Text(
                                    "Pesquisar",
                                    style: TextStyle(
                                        color: Colors.white, fontSize: 20),
                                  ),
                                ),
                                style: ButtonStyle(
                                    shape: MaterialStateProperty.all(
                                        RoundedRectangleBorder(
                                            borderRadius:
                                            BorderRadius.circular(15)))),
                              ),
                            ),
                            Expanded(
                              child: Padding(
                                padding:
                                EdgeInsets.only(left: 1, right: 1, top: 10),
                                child: ListView.builder(
                                    itemCount: _listaServico.length,
                                    itemBuilder: (context, index) {
                                      final servico = _listaServico[index];
                                      return InkWell(
                                        borderRadius: BorderRadius.circular(6),
                                        splashColor: Theme.of(context).primaryColor,
                                        onTap: () => _selectServico(context, servico),
                                        child: Container(
                                          decoration: servico.sincronizado == 0 ? BoxDecoration(
                                              borderRadius: BorderRadius.circular(12),
                                              gradient: LinearGradient(colors: [
                                                Colors.blue.withOpacity(0.5),
                                                Colors.blue,
                                              ], begin: Alignment.topLeft, end: Alignment.bottomRight)
                                          ):BoxDecoration(
                                              borderRadius: BorderRadius.circular(12),
                                              gradient: LinearGradient(colors: [
                                                Colors.green.withOpacity(0.5),
                                                Colors.green,
                                              ], begin: Alignment.topLeft, end: Alignment.bottomRight)
                                          ),
                                          margin: EdgeInsets.symmetric(
                                              vertical: 6, horizontal: 5),
                                          // elevation: 5,
                                          child: ListTile(
                                            leading: Padding(
                                              padding: const EdgeInsets.only(
                                                  top: 10, right: 25),
                                              child: Text(
                                                servico.codigo,
                                                style: TextStyle(
                                                    fontWeight: FontWeight.bold,
                                                    fontSize: 17),
                                              ),
                                            ),
                                            title: Text(
                                              _getTituloLista(servico),
                                              style: TextStyle(
                                                  fontSize: 18,
                                                  fontWeight: FontWeight.bold),
                                            ),
                                            subtitle: Text(
                                              "Executada : " +
                                                  DateFormat('dd/MM/y ').format(
                                                      DateTime.parse(servico
                                                          .dataExecucao)),
                                              style: TextStyle(
                                                  fontSize: 16,
                                                  fontWeight: FontWeight.w900
                                              ),
                                            ),
                                          ),
                                        ),
                                      );
                                    }),
                              ),
                            )
                          ],
                        );
                      }
                    })
              // child:
            ),
          )),
    );
  }
}
