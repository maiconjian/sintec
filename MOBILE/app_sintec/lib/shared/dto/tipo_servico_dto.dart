class TipoServicoDto{
  int id;
  String nome;

  TipoServicoDto({
    required this.id,
    required this.nome
  });

  factory TipoServicoDto.fromSQLite(Map map) {
    return TipoServicoDto(
        id: map["id"],
        nome: map["nome"],
    );
  }

  static List<TipoServicoDto> fromSQLiteList(List<Map> listMap) {
    List<TipoServicoDto> listaDto = [];
    for (Map item in listMap) {
      listaDto.add(TipoServicoDto.fromSQLite(item));
    }
    return listaDto;
  }
}