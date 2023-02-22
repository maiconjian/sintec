
import 'package:app_sintec/pages/Assinatura.dart';
import 'package:app_sintec/pages/backup.dart';
import 'package:app_sintec/pages/carregamento_questionario.dart';
import 'package:app_sintec/pages/carregamento_servico.dart';
import 'package:app_sintec/pages/historico.dart';
import 'package:app_sintec/pages/home.dart';
import 'package:app_sintec/pages/login.dart';
import 'package:app_sintec/pages/questionario.dart';
import 'package:app_sintec/pages/servico_detalhado.dart';
import 'package:app_sintec/pages/servico_pendente.dart';
import 'package:app_sintec/pages/carregamento_usuario.dart';
import 'package:app_sintec/pages/sinc_retorno.dart';
import 'package:flutter/material.dart';

class RouteGenerator{

  static Route<dynamic> generateRoute(RouteSettings settings) {

    final args = settings.arguments;

    switch(settings.name){
      case "/" :
        return MaterialPageRoute(
            builder: (_) => CarregamentoUsuario()
        );
      case "/carregamento-questionario" :
        return MaterialPageRoute(
            builder: (_) => CarregamentoQuestionario()
        );
      case "/carregamento-servico" :
        return MaterialPageRoute(
            builder: (_) => CarregamentoServico()
        );
      case "/login" :
        return MaterialPageRoute(
            builder: (_) => Login()
        );
      case "/home" :
        return MaterialPageRoute(
            builder: (_) => Home()
        );
      case "/servico":
        return MaterialPageRoute(
            builder: (_) =>ServicoPendente()
        );
      case "/servico-detalhado":
        return MaterialPageRoute(
            builder: (_)=> ServicoDetalhado(),
            settings: settings,
        );
      case "/assinatura":
        return MaterialPageRoute(
            builder: (_) => Assinatura(),
            settings: settings,
        );
      case "/questionario":
        return MaterialPageRoute(
          builder: (_) => Questionario(),
          settings: settings,
        );
      case "/historico":
        return MaterialPageRoute(
            builder: (_)=> Historico(),
        );
      case "/sinc-retorno":
        return MaterialPageRoute(
            builder: (_)=> SincRetorno()
        );
      case "/backup":
        return MaterialPageRoute(
            builder: (_) => Backup()
        );

      default:
        return _erroRota();
    }
  }

  static Route<dynamic> _erroRota(){
    return MaterialPageRoute(
        builder: (_){
          return Scaffold(
            appBar: AppBar(title: Text("Tela não encontrada!"),),
            body: Center(
              child: Text("Tela não encontrada!"),
            ),
          );
        }
    );
  }
}