class Ocorrencia {
  int id;
  String? descricao;
  int? idContrato;
  int? ativo;

  Ocorrencia(
      {required this.id,
      required this.descricao,
      required this.idContrato,
      required this.ativo});

  factory Ocorrencia.fromSQLite(Map map) {
    return Ocorrencia(
      id: map["id"],
      descricao: map['descricao'],
      idContrato: map['id_contrato'],
      ativo: map["atibo"],
    );
  }

  static List<Ocorrencia> fromSQLiteList(List<Map> listMap) {
    List<Ocorrencia> lista = [];
    for (Map item in listMap) {
      lista.add(Ocorrencia.fromSQLite(item));
    }
    return lista;
  }
}
