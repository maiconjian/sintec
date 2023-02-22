
import 'package:app_sintec/RouteGenerator.dart';
import 'package:app_sintec/pages/carregamento_usuario.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

final ThemeData temaPadrao = ThemeData(
  primaryColor: Color.fromRGBO(63, 81, 191, 1),
  accentColor: Color.fromRGBO(63, 81, 191, 1),
  elevatedButtonTheme:ElevatedButtonThemeData(
      style: ElevatedButton.styleFrom(
        primary: Color.fromRGBO(63, 81, 191, 1),
      )
  )
);

void main(){
  WidgetsFlutterBinding.ensureInitialized();
  SystemChrome.setPreferredOrientations([DeviceOrientation.portraitUp]);
  runApp(MaterialApp(
    home:CarregamentoUsuario(),
    theme: temaPadrao,
    initialRoute: "/",
    onGenerateRoute: RouteGenerator.generateRoute,
    debugShowCheckedModeBanner: false,
  ));
}