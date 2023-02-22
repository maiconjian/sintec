import 'package:app_sintec/core/spinner.dart';
import 'package:app_sintec/shared/bd/dao/usuario_dao.dart';
import 'package:app_sintec/shared/util/info_device.dart';
import 'package:app_sintec/shared/util/local_storage.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:app_sintec/core/toast.dart';
import 'package:intl/intl.dart';
import '../shared/util/gps.dart';

class Login extends StatefulWidget {
  const Login({Key? key}) : super(key: key);

  @override
  State<Login> createState() => _LoginState();
}

class _LoginState extends State<Login> {
  Gps gps = Gps();
  // final prefs = SharedPreferences.getInstance();
  //Controladores (capturar oque for digitado)
  TextEditingController _controllerMatricula = TextEditingController();
  TextEditingController _controllerSenha = TextEditingController();
  String _mensagemErro = "";
  final _loading = ValueNotifier(false);



  autenticar() {
    //  Recuperar os dados dos campos
    String matricula = _controllerMatricula.text;
    String senha = _controllerSenha.text;

    if (matricula.isNotEmpty && senha.isNotEmpty) {
      UsuarioDao usuarioDao = new UsuarioDao();
      usuarioDao.authenticar(matricula, senha).then((response) => {
            LocalStorage.clearLocalStorage(),
            _loading.value = true,
            if (response.length > 0)
              {
                if (response[0].ativo == 1)
                  {
                    LocalStorage.saveIdUsuario(response[0].id),
                    WidgetsBinding.instance.addPostFrameCallback((_) {
                      Navigator.pushNamed(context, "/carregamento-questionario");
                      _controllerMatricula = TextEditingController();
                      _controllerSenha = TextEditingController();
                      _loading.value = false;

                      //  pegar argumento na outra classe
                      //  Modaloute.of(context).settings.arguments
                    })
                  }
                else
                  {
                    ToastMesage.showToastError("Usuario desativado!"),
                    _loading.value = false
                  }
              }
            else
              {
                ToastMesage.showToastError("Usuario não encontrado!"),
                _loading.value = false
              }
          });
    }else{
      ToastMesage.showToastError("Matricula ou senha não informados!");
    }
  }

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    gps.getVerificarPermissoes();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        width: double.infinity,
        padding: EdgeInsets.all(16),
        child: Center(
          child: ValueListenableBuilder(
            valueListenable: _loading,
            builder: (context, value, child) {
              if (value == true) {
                return Spinner.showSpinner();
              } else {
                return Container(
                  width: double.infinity,
                  padding: EdgeInsets.all(16),
                  child: Center(
                    child: SingleChildScrollView(
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.stretch,
                        children: <Widget>[
                          Padding(
                            padding: EdgeInsets.only(bottom: 15),
                            child: Image.asset("image/logo.png"),
                          ),
                          Padding(
                              padding: EdgeInsets.only(bottom: 15),
                              child: TextField(
                                controller: _controllerMatricula,
                                // autofocus: false,
                                keyboardType: TextInputType.number,
                                maxLength: 10,
                                style: TextStyle(fontSize: 20),
                                decoration: InputDecoration(
                                  contentPadding:
                                      EdgeInsets.fromLTRB(16, 16, 16, 16),
                                  hintText: "Matricula",
                                  // icon: Icon(Icons.verified_user,color: Color.fromRGBO(63, 81, 191, 1)),
                                  border: OutlineInputBorder(
                                      borderRadius: BorderRadius.circular(15)),
                                ),
                                inputFormatters: [
                                  FilteringTextInputFormatter.digitsOnly
                                ],
                              )),
                          Padding(
                              padding: EdgeInsets.only(bottom: 15),
                              child: TextField(
                                  controller: _controllerSenha,
                                  keyboardType: TextInputType.number,
                                  maxLength: 10,
                                  style: TextStyle(fontSize: 20),
                                  decoration: InputDecoration(
                                    contentPadding:
                                        EdgeInsets.fromLTRB(16, 16, 16, 16),
                                    hintText: "Senha",
                                    // icon: Icon(Icons.lock,color: Color.fromRGBO(63, 81, 191, 1)),
                                    border: OutlineInputBorder(
                                        borderRadius:
                                            BorderRadius.circular(15)),
                                  ),
                                  inputFormatters: [
                                    FilteringTextInputFormatter.digitsOnly
                                  ],
                                  obscureText: true)),
                          Padding(
                            padding: EdgeInsets.only(top: 16, bottom: 50),
                            child: ElevatedButton(
                              onPressed: () => {autenticar()},
                              child: Text(
                                "ENTRAR",
                                style: TextStyle(
                                    color: Colors.white, fontSize: 20),
                              ),
                              style: ButtonStyle(
                                  backgroundColor: MaterialStateProperty.all(
                                      Color.fromRGBO(63, 81, 191, 1)),
                                  padding: MaterialStateProperty.all(
                                      EdgeInsets.only(top: 12, bottom: 12)),
                                  shape: MaterialStateProperty.all(
                                      RoundedRectangleBorder(
                                          borderRadius:
                                              BorderRadius.circular(15)))),
                            ),
                          ),
                          Padding(
                            padding: EdgeInsets.only(bottom: 10),
                            child: Text(
                              "SINTEC - Versão : 01",
                              textAlign: TextAlign.center,
                              style: TextStyle(
                                  fontSize: 20, fontWeight: FontWeight.bold),
                            ),
                          ),
                          Padding(
                            padding: EdgeInsets.only(bottom: 10),
                            child: Text(
                              "Copyright © 2022 - Grupo Floripark",
                              textAlign: TextAlign.center,
                              style: TextStyle(
                                  fontSize: 15, fontWeight: FontWeight.bold),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                );
              }
            },
          ),
        ),
      ),
    );
  }
}
