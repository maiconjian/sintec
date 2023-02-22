class ServicoDto {
  int id;
  String codigo;
  String dataExecucao;
  String placaVeiculo;
  String nomeImovel;
  String nomeColaborador;
  int idCategoriaTipoServico;
  int sincronizado;

  ServicoDto(
      {required this.id,
      required this.codigo,
      required this.dataExecucao,
      required this.placaVeiculo,
      required this.nomeImovel,
      required this.nomeColaborador,
      required this.idCategoriaTipoServico,
      required this.sincronizado});


  factory ServicoDto.fromSQLite(Map map) {
    return ServicoDto(
      id: map["id"],
      codigo: map["codigo"],
      dataExecucao: map["dataExecucao"],
      placaVeiculo: map["placaVeiculo"],
      nomeImovel: map["nomeImovel"],
      nomeColaborador: map["nomeColaborador"],
      idCategoriaTipoServico: map['idCategoriaTipoServico'],
      sincronizado: map["sincronizado"],
    );
  }

  static List<ServicoDto> fromSQLiteList(List<Map> listMap) {
    List<ServicoDto> listaServico = [];
    for (Map item in listMap) {
      listaServico.add(ServicoDto.fromSQLite(item));
    }
    return listaServico;
  }
}
