class Pergunta{
  int id;
  String enunciado;
  int flagFoto;
  int flagObrigatorio;
  int flagMultiplaEscolha;
  int idQuestionario;
  int ordemApresentacao;
  int ativo;
  Pergunta({
    required this.id,
    required this.enunciado,
    required this.flagFoto,
    required this.flagObrigatorio,
    required this.flagMultiplaEscolha,
    required this.idQuestionario,
    required this.ordemApresentacao,
    required this.ativo
});

  factory Pergunta.fromSQLite(Map map) {
    return Pergunta(
      id: map["id"],
      enunciado: map["enunciado"],
      flagFoto: map["flag_foto"],
      flagObrigatorio: map["flag_obrigatorio"],
      flagMultiplaEscolha: map["flag_multipla_escolha"],
      idQuestionario: map["id_questionario"],
      ordemApresentacao: map["ordem_apresentacao"],
      ativo: map["ativo"],
    );
  }

  static List<Pergunta> fromSQLiteList(List<Map> listMap) {
    List<Pergunta> lista = [];
    for (Map item in listMap) {
      lista.add(Pergunta.fromSQLite(item));
    }
    return lista;
  }
}