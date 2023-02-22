// import 'dart:convert';
// Usuario usuarioFromMap(String str) => Usuario.fromMap(json.decode(str));
// String usuarioToMap(Usuario data) => json.encode(data.toMap());

import 'dart:math';

class Usuario {
  Usuario({
    required this.id,
    required this.nome,
    required this.matricula,
    required this.pin,
    required this.ativo,
  });

  int id;
  String nome;
  int matricula;
  String pin;
  int ativo;

  factory Usuario.fromSQLite(Map map) {
    return Usuario(
      id: map["id"],
      nome: map["nome"],
      matricula: map["matricula"],
      pin: map["pin"],
      ativo: map["ativo"],
    );
  }

  static List<Usuario> fromSQLiteList(List<Map> listMap) {
    List<Usuario> listaUsuario = [];
    for (Map item in listMap) {
      listaUsuario.add(Usuario.fromSQLite(item));
    }
    return listaUsuario;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['nome'] = this.nome;
    data['matricula'] = this.matricula;
    data['pin'] = pin ;
    data['ativo'] = this.ativo;

    return data;
  }


  // factory Usuario.empty() {
  //   return Usuario(nome: '', contato: '');
  // }
}
