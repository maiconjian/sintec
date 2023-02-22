import 'dart:ui';
import 'package:flutter/material.dart';

const dummyMenus = [
  Menu(titulo: "INSPEÇÃO", cor: Colors.purple,icon: Icon(Icons.list_alt,color: Colors.white),route: "/servico"),
  // Menu(titulo: "HISTORICO", cor: Colors.red,icon: Icon(Icons.checklist,color: Colors.white),route: "/historico"),
  Menu(titulo: "BAIXAR SERVIÇO", cor: Colors.green,icon: Icon(Icons.system_update_alt_sharp,color: Colors.white),route: "/carregamento-servico"),
  Menu(titulo: "ENVIAR SERVIÇO", cor: Colors.orange,icon: Icon(Icons.publish,color: Colors.white),route: "/sinc-retorno"),
  Menu(titulo: "BACKUP", cor: Colors.blueAccent,icon: Icon(Icons.backup_table_outlined,color: Colors.white),route: "/backup"),
];

class Menu {
  final String titulo;
  final Color cor;
  final Icon icon;
  final String route;

  const Menu({
    required this.titulo,
    required this.cor,
    required this.icon,
    required this.route
  });

}