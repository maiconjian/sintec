class PerguntaDto {
  int? idPergunta;
  String? enunciadoPergunta;
  int? ordemApresentacao;
  int? flagFoto;
  int? flagObrigatorio;
  int? flagMultiplaEscolha;
  int? idQuestionario;
  int? idRetornoResposta;
  int? idServico;
  int? idAlternativa;
  String? resposta;
  int? respostaSincronizada;
  int? idRetornoFoto;
  String? nomeFoto;
  String? pathFoto;
  int? flagAssinatura;
  int? fotoSincronizada;

  PerguntaDto({
    required this.idPergunta,
    required this.enunciadoPergunta,
    required this.ordemApresentacao,
    required this.flagFoto,
    required this.flagObrigatorio,
    required this.flagMultiplaEscolha,
    required this.idQuestionario,
    required this.idRetornoResposta,
    required this.idServico,
    required this.idAlternativa,
    required this.resposta,
    required this.respostaSincronizada,
    required this.idRetornoFoto,
    required this.nomeFoto,
    required this.pathFoto,
    required this.flagAssinatura,
    required this.fotoSincronizada,
  });

  factory PerguntaDto.fromSQLite(Map map) {
    return PerguntaDto(
      idPergunta: map["id_pergunta"],
      enunciadoPergunta: map["enunciado_pergunta"],
      ordemApresentacao: map["ordem_apresentacao"],
      flagFoto: map["flag_foto"],
      flagObrigatorio: map["flag_obrigatorio"],
      flagMultiplaEscolha: map["flag_multipla_escolha"],
      idQuestionario: map["id_questionario"],
      idRetornoResposta: map["id_retorno_resposta"],
      idServico: map["id_servico"],
      idAlternativa: map["id_alternativa"],
      resposta: map["resposta"],
      respostaSincronizada: map["resposta_sincronizada"],
      idRetornoFoto: map['id_retorno_foto'],
      nomeFoto: map['nome_foto'],
      pathFoto: map['path_foto'],
      flagAssinatura: map['flag_assinatura'],
      fotoSincronizada: map['foto_sincronizada'],
    );
  }

  static List<PerguntaDto> fromSQLiteList(List<Map> listMap) {
    List<PerguntaDto> listaDto = [];
    for (Map item in listMap) {
      listaDto.add(PerguntaDto.fromSQLite(item));
    }
    return listaDto;
  }
}
