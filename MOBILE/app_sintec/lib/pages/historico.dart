import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import '../components/app-bar-custom.dart';

class Historico extends StatefulWidget {
  const Historico({Key? key}) : super(key: key);

  @override
  State<Historico> createState() => _HistoricoState();
}

class _HistoricoState extends State<Historico> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar:AppBarCustom(title: Text("Historico"), routePrevious: "/home", context: context),
    );
  }
}
