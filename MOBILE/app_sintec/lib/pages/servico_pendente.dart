import 'package:app_sintec/shared/bd/dao/servico_dao.dart';
import 'package:app_sintec/shared/dto/filtro_servico.dart';
import 'package:app_sintec/shared/dto/tipo_servico_dto.dart';
import 'package:app_sintec/shared/model/helper/categoria_tp_servico.dart';
import 'package:app_sintec/shared/util/gps.dart';
import 'package:app_sintec/shared/util/local_storage.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';


import '../components/app-bar-custom.dart';
import '../core/spinner.dart';
import '../shared/dto/regional-dto.dart';
import '../shared/model/servico.dart';


class ServicoPendente extends StatefulWidget {
  const ServicoPendente({Key? key}) : super(key: key);

  @override
  State<ServicoPendente> createState() => _ServicoPendenteState();
}

class _ServicoPendenteState extends State<ServicoPendente> {
  Gps gps = Gps();
  final _loading = ValueNotifier(false);
  ServicoDao servicoDao = new ServicoDao();
  List<Servico> _listaServico = [];
  List<RegionalDto> _listaRegional = [];
  List<TipoServicoDto> _listaTipoServico = [];
  bool _isPesquisando = false;
  var _selectedRegional;
  var _selectedTipoServico;

  void _getPesquisar() {
    _loading.value = true;
    _isPesquisando = true;
    Servico servico;
    _listaServico = [];
    setState(() {
      int idRegional = _selectedRegional == null ? 0 : _selectedRegional;
      int idTipoServico =
          _selectedTipoServico == null ? 0 : _selectedTipoServico;
      servicoDao.listaPendente(idRegional, idTipoServico).then((response) => {
            for (int i = 0; i < response.length; i++)
              {
                servico = Servico(
                    id: response[i].id,
                    codigo: response[i].codigo,
                    dataRef: response[i].dataRef,
                    idContrato: response[i].idContrato,
                    idRegional: response[i].idRegional,
                    nomeRegional: response[i].nomeRegional,
                    idCategoriaTipoServico: response[i].idCategoriaTipoServico,
                    nomeCategoriaTipoServico:
                        response[i].nomeCategoriaTipoServico,
                    idTipoServico: response[i].idTipoServico,
                    nomeTipoServico: response[i].nomeTipoServico,
                    idImovel: response[i].idImovel,
                    nomeImovel: response[i].nomeImovel,
                    bairro: response[i].bairro,
                    logradouro: response[i].logradouro,
                    numeroLogradouro: response[i].numeroLogradouro,
                    idVeiculo: response[i].idVeiculo,
                    placaVeiculo: response[i].placaVeiculo,
                    corVeiculo: response[i].corVeiculo,
                    modeloVeiculo: response[i].modeloVeiculo,
                    idColaborador: response[i].idColaborador,
                    nomeColaborador: response[i].nomeColaborador,
                    cnh: response[i].cnh,
                    validadeCnh: response[i].validadeCnh,
                    categoriaCnh: response[i].categoriaCnh,
                    dataProgramada: response[i].dataProgramada,
                    idUsuario: response[i].idUsuario),
                setState(() {
                  _listaServico.add(servico);
                })
              },
            _loading.value = false,
            _isPesquisando = false,
          });
    });
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

  String _getTituloLista(Servico servico) {

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

  void _selectServico(BuildContext context, Servico servico) {
    WidgetsBinding.instance.addPostFrameCallback((_) {
      if(_selectedRegional !=null && _selectedTipoServico != null){
        LocalStorage.saveFilterServico(new FiltroServico(idRegional: _selectedRegional, idTipoServico: _selectedTipoServico));
      }
      Navigator.pushReplacementNamed(context,"/servico-detalhado",arguments: servico);
    });
  }

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
  void didChangeDependencies() {
    // TODO: implement didChangeDependencies
    super.didChangeDependencies();
    gps.getVerificarPermissoes();
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () async {
        Navigator.pushNamed(context, "/home");
        return true;
      },
      child: Scaffold(
          appBar: AppBarCustom(title: Text("Serviço"), routePrevious: "/home", context: context),
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
                                          decoration: BoxDecoration(
                                              borderRadius: BorderRadius.circular(12),
                                              gradient: LinearGradient(colors: [
                                                Colors.orange.withOpacity(0.5),
                                                Colors.orange,
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
                                              "Programada : " +
                                                  DateFormat('dd/MM/y ').format(
                                                      DateTime.parse(servico
                                                          .dataProgramada)),
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
