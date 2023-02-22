class RegionalDto{
  int id;
  String nome;
  int idContrato;

  RegionalDto({
    required this.id,
    required this.nome,
    required this.idContrato
  });

  factory RegionalDto.fromSQLite(Map map) {
    return RegionalDto(
      id: map["id"],
      nome: map["nome"],
      idContrato: map["id_contrato"]
    );
  }

  static List<RegionalDto> fromSQLiteList(List<Map> listMap) {
    List<RegionalDto> listaDto = [];
    for (Map item in listMap) {
      listaDto.add(RegionalDto.fromSQLite(item));
    }
    return listaDto;
  }
}