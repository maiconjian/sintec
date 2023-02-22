import 'dart:io';
import 'package:app_sintec/shared/bd/dao/retorno_foto_dao.dart';
import 'package:app_sintec/shared/model/retorno_foto.dart';
import 'package:app_sintec/shared/util/gps.dart';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'package:intl/intl.dart';

import 'package:path/path.dart' as path;
import 'package:path_provider/path_provider.dart' as syspaths;

import '../core/spinner.dart';
import '../core/toast.dart';

class InputImage extends StatefulWidget {
  final double height;
  final double width;
  final int idServico;
  final int idQuestionario;

  const InputImage(this.height, this.width, this.idServico, this.idQuestionario,
      {Key? key})
      : super(key: key);

  @override
  State<InputImage> createState() => _InputImageState();
}

class _InputImageState extends State<InputImage> {
  Gps gps = Gps();
  final _loading = ValueNotifier(false);
  RetornoFotoDao dao = new RetornoFotoDao();
  TextEditingController _controllerObservacao = new TextEditingController();

  //Imagem
  final int _maxFoto = 5;
  int _indexFoto = 0;
  File? _storedImage;
  List<RetornoFoto> _listaRetornoFoto = [];

  Future<void> _getCarregarFoto() async {
    setState(() {
      _loading.value = true;
    });
    await dao.listarPorServico(widget.idServico, widget.idQuestionario)
        .then((value) => {
              _listaRetornoFoto = value,
              setState(() => {
                    _loading.value = false,
                  })
            })
        .catchError((onError) => {
              ToastMesage.showToastError("Erro ao carregar Fotos"),
              setState(() => {
                    _loading.value = false,
                  })
            });
    setState(() => {
          if(_listaRetornoFoto.length > 0){
            _storedImage = File(_listaRetornoFoto.first.path),
            _controllerObservacao.text = _listaRetornoFoto.first.observacao.toString(),
          },
          _indexFoto = 0,
        });
  }

  // Camera
  _takePicture() async {
    setState(() {
      _loading.value = true;
    });
    final ImagePicker _picker = ImagePicker();
    XFile imageFile = await _picker.pickImage(
      source: ImageSource.camera,
      maxWidth: 600,
    ) as XFile;

    if (imageFile != null) {
      setState(() {
        _controllerObservacao.text = '';
        RetornoFoto retorno =  RetornoFoto(
            nome: '${_indexFoto+1}${DateFormat("yMdHHmmsssss").format(DateTime.now())}',
            path: imageFile.path,
            idQuestionario: widget.idQuestionario,
            idServico: widget.idServico,
            longitude: gps.getLongitude(),
            latitude: gps.getLatitude(),
            flagAssinatura: 0,
            observacao: "",
            dataAtualizacao: DateFormat("y-MM-d HH:mm:ss").format(DateTime.now()),
            sincronizado: 0);
        _storedImage = File(imageFile.path);
        _listaRetornoFoto.add(retorno);
        _indexFoto = _listaRetornoFoto
            .indexWhere((element) => element.path == imageFile.path);
      });
    }
    setState(() {
      _loading.value = false;
    });
  }

  Future<void> _salvarRetornoFoto() async {
    setState(() => {
      _loading.value = true,
    });
    for (int i = 0; i < _listaRetornoFoto.length; i++) {
      final appDir = await syspaths.getApplicationSupportDirectory();
      final montar_imagem = File(_listaRetornoFoto[i].path);
      // String fileName = path.basename(montar_imagem.path);
      String fileName = '${_listaRetornoFoto[i].nome.replaceAll(".jpg", "")}.jpg';
      bool existFoto = await File('${appDir.path}/$fileName').exists();
      String pathFoto='';
      if (!existFoto) {
        final savedImage = await montar_imagem.copy('${appDir.path}/$fileName');
        montar_imagem.delete();
        pathFoto = savedImage.path;
      }else{
        pathFoto = _listaRetornoFoto[i].path;
      }
      RetornoFoto retorno =  RetornoFoto(
          id: _listaRetornoFoto[i].id,
          nome: fileName,
          longitude: _listaRetornoFoto[i].longitude,
          latitude: _listaRetornoFoto[i].latitude,
          path: pathFoto,
          idQuestionario: widget.idQuestionario,
          idServico: widget.idServico,
          flagAssinatura: 0,
          observacao: _listaRetornoFoto[i].observacao,
          dataAtualizacao: DateFormat("yyyy-MM-dd HH:mm:ss").format(DateTime.now()),
          sincronizado: 0);

      await dao.merge(retorno).then((value) => {}).catchError((onError) => {
            ToastMesage.showToastError("Erro ao salvar foto ${i + 1}"),
          });

      if (i == _listaRetornoFoto.length - 1) {
        Navigator.pop(context);
        Future.delayed(Duration(milliseconds: 200),()=>{
            _loading.value = false,

        });
      }
    }
  }

  void _deletarRetornoFoto() async{
    setState(() {
      _loading.value=true;
    });
    if(_listaRetornoFoto[_indexFoto].id == null){
      var image = File(_listaRetornoFoto[_indexFoto].path);
      image.delete();
      _listaRetornoFoto.removeAt(_indexFoto);
      setState(() {
        if(_listaRetornoFoto.length > 0){
          _storedImage = File(_listaRetornoFoto.last.path);
          _controllerObservacao.text = _listaRetornoFoto.last.observacao.toString();
          _indexFoto = _listaRetornoFoto.indexOf(_listaRetornoFoto.last);
        }else {
          _storedImage = null;
          _controllerObservacao.text = '';
        }
        Future.delayed(Duration(milliseconds: 200),()=>{
          setState(() => {
            _loading.value = false,
          }),
        });
    });
    }else if(_listaRetornoFoto[_indexFoto].id! > 0){
      File image;
      await dao.deleteRetornoFoto(_listaRetornoFoto[_indexFoto].id).then((value) => {
        image = File(_listaRetornoFoto[_indexFoto].path),
        image.delete(),
        _listaRetornoFoto.removeAt(_indexFoto),
        if(_listaRetornoFoto.length > 0){
          _storedImage = File(_listaRetornoFoto.last.path),
          _controllerObservacao.text = _listaRetornoFoto.last.observacao.toString(),
          _indexFoto = _listaRetornoFoto.indexOf(_listaRetornoFoto.last),
        }else {
          _storedImage = null,
          _controllerObservacao.text = '',
    }
      });
      setState(() {
        _loading.value=false;
      });
    }
  }

  void _fecharDialog(){
    if(_listaRetornoFoto.length > 0){
      for(int i=0;i <_listaRetornoFoto.length;i++){
        if(_listaRetornoFoto[i].id == null){
          var image = File(_listaRetornoFoto[i].path);
          image.delete();
        }
      }
    }
    Navigator.pop(context);

  }


  int _getNumFoto() {
    if (_listaRetornoFoto.length > 0) {
      return _indexFoto + 1;
    } else {
      return _indexFoto;
    }
  }

  void _avancarFoto() {
    if (_indexFoto < _listaRetornoFoto.length) {
      setState(() {
        _indexFoto++;
        _storedImage = new File(_listaRetornoFoto[_indexFoto].path);
        _controllerObservacao.text = _listaRetornoFoto[_indexFoto].observacao!;
      });
    }
  }

  void _voltarFoto() {
    if (_indexFoto > 0 && _indexFoto < _listaRetornoFoto.length) {
      setState(() {
        _indexFoto--;
        _storedImage = new File(_listaRetornoFoto[_indexFoto].path);
        _controllerObservacao.text = _listaRetornoFoto[_indexFoto].observacao!;
      });
    }
  }

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    _getCarregarFoto();
  }

  @override
  void didChangeDependencies() {
    // TODO: implement didChangeDependencies
    super.didChangeDependencies();
    gps.getVerificarPermissoes();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: StatefulBuilder(builder: (context, StateSetter setState) {
        return ValueListenableBuilder(
          valueListenable: _loading,
          builder: (context, value, child) {
            if (value == true) {
              return Spinner.showSpinner();
            } else {
              return Dialog(
                insetPadding: EdgeInsets.zero,
                child: SingleChildScrollView(
                  child: Container(
                    child: Column(
                      children: <Widget>[
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: <Widget>[
                            Padding(
                              padding:
                                  EdgeInsets.only(top: 5, bottom: 1, left: 11),
                              child: ElevatedButton(
                                style: ButtonStyle(
                                  shape: MaterialStateProperty.all(
                                    RoundedRectangleBorder(
                                      borderRadius: BorderRadius.circular(15),
                                    ),
                                  ),
                                ),
                                onPressed: _listaRetornoFoto.length == _maxFoto
                                    ? null
                                    : _takePicture,
                                child: Icon(
                                  Icons.photo_camera,
                                  size: 30,
                                ),
                              ),
                            ),
                            Padding(
                              padding: EdgeInsets.all(8),
                              child: Text(
                                "Foto",
                                style: TextStyle(fontSize: 20),
                              ),
                            ),
                            Padding(
                              padding:
                                  EdgeInsets.only(top: 5, bottom: 1, right: 11),
                              child: ElevatedButton(
                                style: ButtonStyle(
                                  shape: MaterialStateProperty.all(
                                    RoundedRectangleBorder(
                                      borderRadius: BorderRadius.circular(15),
                                    ),
                                  ),
                                ),
                                onPressed: _listaRetornoFoto.length == 0?null :()=> _deletarRetornoFoto(),
                                child: Icon(
                                  Icons.delete,
                                  size: 30,
                                ),
                              ),
                            )
                          ],
                        ),
                        Padding(
                          padding: EdgeInsets.only(top: 5, left: 10, right: 10),
                          child: Container(
                            height: widget.height * 0.5,
                            decoration: BoxDecoration(
                                border:
                                    Border.all(width: 1, color: Colors.grey)),
                            alignment: Alignment.center,
                            child: _storedImage != null
                                ? Image.file(
                                    _storedImage!,
                                    width: double.infinity,
                                    fit: BoxFit.cover,
                                  )
                                : Text("Nenhuma Imagem"),
                          ),
                        ),
                        Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                            children: <Widget>[
                              Padding(
                                padding: EdgeInsets.all(10),
                                child: ElevatedButton(
                                  onPressed: _indexFoto == 0
                                      ? null
                                      : () => _voltarFoto(),
                                  child: Icon(Icons.arrow_back_ios),
                                ),
                              ),
                              Padding(
                                  padding: EdgeInsets.all(10),
                                  child: Text(
                                      "${_getNumFoto()}/${_listaRetornoFoto.length}")),
                              Padding(
                                padding: EdgeInsets.all(10),
                                child: ElevatedButton(
                                  onPressed:
                                      _getNumFoto() == _listaRetornoFoto.length
                                          ? null
                                          : () => _avancarFoto(),
                                  child: Icon(Icons.arrow_forward_ios),
                                ),
                              )
                            ],
                          ),
                        ),
                        Padding(
                          padding: EdgeInsets.all(10),
                          child: TextField(
                            controller: _controllerObservacao,
                            onChanged: (_) => {
                              _listaRetornoFoto[_indexFoto].observacao =
                                  _controllerObservacao.text
                            },
                            decoration: InputDecoration(
                              contentPadding:
                                  EdgeInsets.fromLTRB(16, 16, 16, 16),
                              hintText: "Observação",
                              // icon: Icon(Icons.lock,color: Color.fromRGBO(63, 81, 191, 1)),
                              border: OutlineInputBorder(
                                  borderRadius: BorderRadius.circular(15)),
                            ),
                          ),
                        ),
                        Padding(
                          padding: EdgeInsets.all(10),
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.spaceAround,
                            children: <Widget>[
                              Padding(
                                padding: EdgeInsets.all(10),
                                child: ElevatedButton(
                                  style: ButtonStyle(
                                    shape: MaterialStateProperty.all(
                                      RoundedRectangleBorder(
                                        borderRadius: BorderRadius.circular(15),
                                      ),
                                    ),
                                  ),
                                  onPressed: _listaRetornoFoto.length == 0
                                      ? null
                                      : () => _salvarRetornoFoto(),
                                  child: Icon(Icons.done, size: 60),
                                ),
                              ),
                              Padding(
                                padding: EdgeInsets.all(10),
                                child: ElevatedButton(
                                  style: ButtonStyle(
                                    shape: MaterialStateProperty.all(
                                      RoundedRectangleBorder(
                                        borderRadius: BorderRadius.circular(15),
                                      ),
                                    ),
                                  ),
                                  onPressed: _fecharDialog,
                                  child: Icon(Icons.close, size: 60),
                                ),
                              )
                            ],
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
              );
            }
          },
        );
      }),
    );
  }
}
