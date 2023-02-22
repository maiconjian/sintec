class RetornoFoto {
  int? id;
  String nome;
  String longitude;
  String latitude;
  String path;
  int? idQuestionario;
  int idServico;
  int flagAssinatura;
  String? observacao;
  String dataAtualizacao;
  int sincronizado;

  RetornoFoto(
      {this.id,
      required this.nome,
      required this.longitude,
      required this.latitude,
      required this.path,
      this.idQuestionario,
      required this.idServico,
      required this.flagAssinatura,
      this.observacao,
      required this.dataAtualizacao,
      required this.sincronizado});

  factory RetornoFoto.fromSQLite(Map map) {
    return RetornoFoto(
      id: map["id"],
      nome: map["nome"],
      longitude: map["longitude"],
      latitude: map["latitude"],
      path: map["path"],
      idQuestionario: map["id_questionario"],
      idServico: map["id_servico"],
      flagAssinatura: map["flag_assinatura"],
      observacao: map["observacao"],
      dataAtualizacao: map['data_atualizacao'],
      sincronizado: map["sincronizado"],
    );
  }

  static List<RetornoFoto> fromSQLiteList(List<Map> listMap) {
    List<RetornoFoto> lista = [];
    for (Map item in listMap) {
      lista.add(RetornoFoto.fromSQLite(item));
    }
    return lista;
  }
}
