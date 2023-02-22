class Questionario{
  int id;
  String titulo;
  int ordemApresentacao;
  int idTipoServico;
  int ativo;

  Questionario({
    required this.id,
    required this.titulo,
    required this.idTipoServico,
    required this.ordemApresentacao,
    required this.ativo
});

  factory Questionario.fromSQLite(Map map) {
    return Questionario(
      id: map["id"],
      titulo: map["titulo"],
      ordemApresentacao: map['ordem_apresentacao'],
      idTipoServico:map['id_tipo_servico'],
      ativo: map["ativo"],
    );
  }

  static List<Questionario> fromSQLiteList(List<Map> listMap) {
    List<Questionario> lista = [];
    for (Map item in listMap) {
      lista.add(Questionario.fromSQLite(item));
    }
    return lista;
  }
}
