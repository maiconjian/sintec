class Servico {
  int id;
  String codigo;
  String dataRef;
  int idContrato;
  int idRegional;
  String nomeRegional;
  int idCategoriaTipoServico;
  String nomeCategoriaTipoServico;
  int idTipoServico;
  String nomeTipoServico;
  int? idImovel;
  String? nomeImovel;
  String? bairro;
  String? logradouro;
  int? numeroLogradouro;
  int? idVeiculo;
  String? placaVeiculo;
  String? corVeiculo;
  String? modeloVeiculo;
  int? idColaborador;
  String? nomeColaborador;
  String? cnh;
  String? validadeCnh;
  String? categoriaCnh;
  String dataProgramada;
  int idUsuario;

  Servico({
    required this.id,
    required this.codigo,
    required this.dataRef,
    required this.idContrato,
    required this.idRegional,
    required this.nomeRegional,
    required this.idCategoriaTipoServico,
    required this.nomeCategoriaTipoServico,
    required this.idTipoServico,
    required this.nomeTipoServico,
    this.idImovel,
    this.nomeImovel,
    this.bairro,
    this.logradouro,
    this.numeroLogradouro,
    this.idVeiculo,
    this.placaVeiculo,
    this.corVeiculo,
    this.modeloVeiculo,
    this.idColaborador,
    this.nomeColaborador,
    this.cnh,
    this.validadeCnh,
    this.categoriaCnh,
    required this.dataProgramada,
    required this.idUsuario
  });

  factory Servico.fromSQLite(Map map) {
    return Servico(
        id: map["id"],
        codigo: map["codigo"],
        dataRef: map["data_ref"],
        idContrato: map["id_contrato"],
        idRegional: map["id_regional"],
        nomeRegional: map["nome_regional"],
        idCategoriaTipoServico: map["id_categoria_tipo_servico"],
        nomeCategoriaTipoServico: map["nome_categoria_tipo_servico"],
        idTipoServico: map["id_tipo_servico"],
        nomeTipoServico: map["nome_tipo_servico"],
        idImovel: map["id_imovel"],
        nomeImovel: map["nome_imovel"],
        bairro: map["bairro_imovel"],
        logradouro: map["logradouro_imovel"],
        numeroLogradouro: map["numero_logradouro"],
        idVeiculo: map["id_veiculo"],
        placaVeiculo: map["placa_veiculo"],
        corVeiculo: map["cor_veiculo"],
        modeloVeiculo: map["modelo_veiculo"],
        idColaborador: map["id_colaborador"],
        nomeColaborador: map["nome_colaborador"],
        cnh: map["cnh"],
        validadeCnh: map["validade_cnh"],
        categoriaCnh: map["categoria_cnh"],
        dataProgramada: map["data_programada"],
        idUsuario: map["id_usuario"]
        );
  }

  static List<Servico> fromSQLiteList(List<Map> listMap) {
    List<Servico> listaServico = [];
    for (Map item in listMap) {
      listaServico.add(Servico.fromSQLite(item));
    }
    return listaServico;
  }

}
