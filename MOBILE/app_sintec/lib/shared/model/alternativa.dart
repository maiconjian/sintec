class Alternativa {
  int id;
  int idPergunta;
  String descricao;
  int flagNconf;
  int ordemApresentacao;
  int ativo;
  Alternativa(
      {required this.id,
      required this.idPergunta,
      required this.descricao,
      required this.flagNconf,
      required this.ordemApresentacao,
      required this.ativo});

  factory Alternativa.fromSQLite(Map map) {
    return Alternativa(
      id: map["id"],
      idPergunta: map["id_pergunta"],
      descricao: map["descricao"],
      flagNconf: map["flag_nconf"],
      ordemApresentacao: map['ordem_apresentacao'],
      ativo: map["ativo"],
    );
  }

  static List<Alternativa> fromSQLiteList(List<Map> listMap) {
    List<Alternativa> lista = [];
    for (Map item in listMap) {
      lista.add(Alternativa.fromSQLite(item));
    }
    return lista;
  }
}
