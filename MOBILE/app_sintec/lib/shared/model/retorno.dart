class Retorno{
  int? id;
  int idUsuario;
  int idServico;
  int? idOcorrencia;
  String dataExecucao;
  String longitude;
  String latitude;
  int flagRacp;
  String modeloAparelho;
  String marcaAparelho;
  int sincronizado;
  int ativo;

  Retorno({
    this.id,
    required this.idUsuario,
    required this.idServico,
     this.idOcorrencia,
    required this.dataExecucao,
    required this.longitude,
    required this.latitude,
    required this.flagRacp,
    required this.modeloAparelho,
    required this.marcaAparelho,
    required this.sincronizado,
    required this.ativo
  });
  factory Retorno.fromSQLite(Map map) {
    return Retorno(
      id: map["id"],
      idUsuario: map["id_usuario"],
      idServico: map["id_servico"],
      idOcorrencia: map["id_ocorrencia"],
      dataExecucao: map["data_execucao"],
      longitude: map["longitude"],
      latitude: map["latitude"],
      flagRacp: map["flag_racp"],
      modeloAparelho: map["modelo_aparelho"],
      marcaAparelho: map["marca_aparelho"],
      sincronizado: map["sincronizado"],
      ativo: map["ativo"]
    );
  }

  static List<Retorno> fromSQLiteList(List<Map> listMap) {
    List<Retorno> lista = [];
    for (Map item in listMap) {
      lista.add(Retorno.fromSQLite(item));
    }
    return lista;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['idUsuario'] = this.idUsuario;
    data['idServico'] = this.idServico;
    data['idOcorrencia'] = idOcorrencia ;
    data['dataExecucao'] = this.dataExecucao;
    data['longitude'] = this.longitude;
    data['latitude'] = this.latitude;
    data['flagRacp'] = this.flagRacp;
    data['modeloAparelho'] = this.modeloAparelho;
    data['marcaAparelho'] = this.marcaAparelho;
    data['sincronizado'] = this.sincronizado;
    data['ativo'] = this.ativo;
    return data;
  }


}