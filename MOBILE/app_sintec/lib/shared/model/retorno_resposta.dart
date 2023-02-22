class RetornoResposta{
  int? id;
  int? idServico;
  int? idPergunta;
  int? idAlternativa;
  String? longitude;
  String? latitude;
  String? resposta;
  String? dataExecucao;
  int? sincronizado;

  RetornoResposta({
    this.id,
    this.idServico,
    this.idPergunta,
    this.idAlternativa,
    this.longitude,
    this.latitude,
    this.resposta,
    this.dataExecucao,
    this.sincronizado
});

  factory RetornoResposta.fromSQLite(Map map) {
    return RetornoResposta(
      id: map["id"],
      idServico: map["id_servico"],
      idPergunta: map["id_pergunta"],
      idAlternativa: map["id_alternativa"],
      longitude: map["longitude"],
      latitude: map["latitude"],
      resposta: map["resposta"],
      dataExecucao: map['data_execucao'],
      sincronizado: map["sincronizado"]
    );
  }

  static List<RetornoResposta> fromSQLiteList(List<Map> listMap) {
    List<RetornoResposta> listaRegional = [];
    for (Map item in listMap) {
      listaRegional.add(RetornoResposta.fromSQLite(item));
    }
    return listaRegional;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['idServico'] = this.idServico;
    data['idPergunta'] = idPergunta ;
    data['idAlternativa'] = this.idAlternativa;
    data['longitude'] = this.longitude;
    data['latitude'] = this.latitude;
    data['resposta'] = this.resposta;
    data['dataExecucao'] = this.dataExecucao;
    return data;
  }
}