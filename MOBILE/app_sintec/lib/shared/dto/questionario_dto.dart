class QuestionarioDto {
  int idQuestionario;
  String tituloQuestionario;
  int qtdPergunta;
  int qtdRespondida;
  int qtdNConf;

  QuestionarioDto(
      {required this.idQuestionario,
      required this.tituloQuestionario,
      required this.qtdPergunta,
      required this.qtdRespondida,
      required this.qtdNConf
      }
  );

  factory QuestionarioDto.fromSQLite(Map map) {
    return QuestionarioDto(
        idQuestionario: map["id_questionario"],
        tituloQuestionario: map["titulo_questionario"],
        qtdPergunta:  map["qtd_pergunta"],
        qtdRespondida: map["qtd_respondida"],
        qtdNConf: map["qtd_nconf"]
    );
  }

  static List<QuestionarioDto> fromSQLiteList(List<Map> listMap) {
    List<QuestionarioDto> listaDto = [];
    for (Map item in listMap) {
      listaDto.add(QuestionarioDto.fromSQLite(item));
    }
    return listaDto;
  }
}
