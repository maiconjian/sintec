import 'dart:convert';
import 'dart:core';
import 'dart:io';
import 'dart:typed_data';

import 'package:app_sintec/core/spinner.dart';
import 'package:app_sintec/core/toast.dart';
import 'package:app_sintec/shared/bd/dao/questionario_dao.dart';
import 'package:app_sintec/shared/bd/dao/retorno_dao.dart';
import 'package:app_sintec/shared/bd/dao/retorno_foto_dao.dart';
import 'package:app_sintec/shared/dto/sinc_foto_dto.dart';
import 'package:app_sintec/shared/model/retorno.dart';
import 'package:app_sintec/shared/model/retorno_foto.dart';
import 'package:connectivity/connectivity.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:signature/signature.dart';
import '../components/app-bar-custom.dart';
import '../shared/bd/dao/retorno_resposta_dao.dart';
import '../shared/model/retorno_resposta.dart';
import '../shared/model/servico.dart';
import 'package:path/path.dart' as path;
import 'package:path_provider/path_provider.dart' as syspaths;

import '../shared/service/retorno_service.dart';
import '../shared/util/gps.dart';
import '../shared/util/info_device.dart';
import '../shared/util/local_storage.dart';

class Assinatura extends StatefulWidget {
  const Assinatura({Key? key}) : super(key: key);

  @override
  State<Assinatura> createState() => _AssinaturaState();
}

class _AssinaturaState extends State<Assinatura> {
  Gps gps = Gps();
  final Connectivity _connectivity = Connectivity() ;
  RetornoService service = RetornoService();
  QuestionarioDao questionarioDao = QuestionarioDao();
  final _loading = ValueNotifier(false);
  final SignatureController _controller = SignatureController(
    penStrokeWidth: 4,
    penColor: Colors.black,
    exportBackgroundColor: Colors.transparent,
  );

  void _apagarAssinatura() {
    _controller.points = [];
  }

  Future<String> _salvarFotoAssinatura() async {
    final appDir = await syspaths.getApplicationSupportDirectory();
    var data = await _controller.toPngBytes();
    String nome = DateFormat("yMdHHmmsssss").format(DateTime.now());
    var savedImage = await File('${appDir.path}/${nome}').writeAsBytes(data!.buffer.asInt8List());
    return savedImage.path;
  }

  Future<void> _salvarRetornoAssinatura(Servico servico) async {
    setState(() {
      _loading.value = true;
    });
    RetornoFotoDao retornoFotoDao = RetornoFotoDao();
    RetornoDao retornoDao = RetornoDao();
    Map device = await InfoDevice.getInfo();
    String pathAssinatura = await _salvarFotoAssinatura();
    Map racp = await questionarioDao.numNConfQuestionario(servico.id);

    RetornoFoto retornoFoto = RetornoFoto(
        nome: path.basename(pathAssinatura),
        longitude: gps.getLongitude(),
        latitude: gps.getLatitude(),
        path: pathAssinatura,
        idServico: servico.id,
        flagAssinatura: 1,
        dataAtualizacao: DateFormat("yyyy-MM-dd HH:mm:ss").format(DateTime.now()),
        observacao: "",
        sincronizado: 0
    );
    await retornoFotoDao.merge(retornoFoto).catchError((onError) => {
      setState(() {
        _loading.value = false;
      }),
      ToastMesage.showToastError("Erro ao salvar assinatura!")
    });

    Retorno retorno;
    int idUsuarioLogado = await LocalStorage.loadIdUsuario();

    retorno = Retorno(
        idUsuario: idUsuarioLogado,
        idServico: servico.id,
        idOcorrencia: 0,
        dataExecucao: DateFormat("yyyy-MM-dd HH:mm:ss").format(DateTime.now()),
        longitude: gps.getLongitude(),
        latitude: gps.getLatitude(),
        flagRacp: racp['qtd_nconf'] > 0 ? 1 : 0,
        modeloAparelho: device['modelo'],
        marcaAparelho: device['marca'],
        sincronizado: 0,
        ativo: 1);

        print(retorno.flagRacp);

    await retornoDao.incluir(retorno).then((value) => {
          checkConnection(servico),
    }).catchError((onError) => {
          ToastMesage.showToastError("Erro ao salvar retorno!"),
          setState(() {
          _loading.value = false;
          })
    });

  }


  Future<void> _sincronizarRetorno(servico) async {
    RetornoDao dao = RetornoDao();
    List<Retorno> lista = [];
    Retorno retorno  = await dao.buscarPorId(servico.id);
    lista.add(retorno);

    List<int> listaIdServico = [];
    listaIdServico =  await service.sincronizarRetorno(lista);
    await dao.updateSincronizado(listaIdServico);
  }

  Future<void> _sincronizarRetornoResposta(servico) async {
    RetornoRespostaDao dao = RetornoRespostaDao();
    List<RetornoResposta> lista = await dao.buscarRespostas(servico.id);
    List<int> listaIdServico = [];
    listaIdServico = await service.sincronizarRetornoResposta(lista);
    await dao.updateSincronizado(listaIdServico);


  }

  Future<void> _sincronizarRetornoFoto(servico) async {
    RetornoFotoDao dao = RetornoFotoDao();
    List<RetornoFoto> lista = await dao.buscarFotos(servico.id);
    List<SincFotoDto> listaDto = await converterDto(lista);
    List<int> listaIdServico = [];
    listaIdServico = await service.sincronizarRetornoFoto(listaDto);
    await dao.updateSincronizado(listaIdServico);


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

  void checkConnection(servico) async{
    var connectivityResult = await (Connectivity().checkConnectivity());
    if (connectivityResult == ConnectivityResult.none) {
      Navigator.pushNamed(context, "/servico");
    }else{
      await _sincronizarRetorno(servico).catchError((onError)=>{
        _loading.value = false,
        ToastMesage.showToastError("Erro ao sincronizar retorno"),
        Navigator.pushNamed(context, "/servico"),
      });
      await _sincronizarRetornoResposta(servico).catchError((onError)=>{
        _loading.value = false,
        ToastMesage.showToastError("Erro ao sincronizar respostas"),
        Navigator.pushNamed(context, "/servico"),
      });
      await _sincronizarRetornoFoto(servico).catchError((onError)=>{
        _loading.value = false,
        ToastMesage.showToastError("Erro ao sincronizar fotos"),
        Navigator.pushNamed(context, "/servico"),
      });
      _loading.value = false;
      Navigator.pushNamed(context, "/servico");
    }
  }

  @override
  void didChangeDependencies() {
    // TODO: implement didChangeDependencies
    super.didChangeDependencies();
    gps.getVerificarPermissoes();
  }

  @override
  Widget build(BuildContext context) {
    final servico = ModalRoute.of(context)!.settings.arguments as Servico;
    final PreferredSizeWidget appBar = AppBarCustom(
        title: Text("Assinatura"),
        routePrevious: "/servico-detalhado",
        context: context,
        argument: servico);

    final mediaQuery = MediaQuery.of(context);
    final appBarHeight = appBar.preferredSize.height;
    final statusBarHeight = mediaQuery.padding.top;
    final avaibleHeight =
        MediaQuery.of(context).size.height - appBarHeight - statusBarHeight;
    final avaibleWidth = MediaQuery.of(context).size.width;

    return WillPopScope(
      onWillPop: () async {
        Navigator.pushNamed(context, "/servico-detalhado");
        return true;
      },
      child: Scaffold(
        appBar: appBar,
        body: ValueListenableBuilder(
          valueListenable: _loading,
          builder: (context, value, child) {
            if (value == true) {
              return Spinner.showSpinner();
            } else {
              return Column(
                children: [
                  SizedBox(
                    width: double.infinity,
                    child: Padding(
                      padding:
                          const EdgeInsets.only(top: 15, left: 15, right: 15),
                      child: Container(
                          height: avaibleHeight * 0.850,
                          decoration: BoxDecoration(
                              border: Border.all(width: 1, color: Colors.black)),
                          child: ClipRRect(
                            child: Signature(
                              controller: _controller,
                              height: 700,
                              backgroundColor: Colors.transparent,
                            ),
                          )),
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.all(10),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        SizedBox(
                          width: avaibleWidth * 0.250,
                          height: avaibleHeight * 0.080,
                          child: ElevatedButton(
                              onPressed: () => _salvarRetornoAssinatura(servico),
                              child: Icon(
                                Icons.check,
                                size: 50,
                              )),
                        ),
                        SizedBox(
                          width: avaibleWidth * 0.250,
                          height: avaibleHeight * 0.080,
                          child: ElevatedButton(
                              onPressed: _apagarAssinatura,
                              child: Icon(
                                Icons.delete,
                                size: 50,
                              )),
                        )
                      ],
                    ),
                  )
                ],
              );
            }
          },
        ),
      ),
    );
  }
}
