import 'package:app_sintec/shared/model/usuario.dart';

class UsuarioSql {
  static String incluir(Usuario usuario) {
    return '''
        INSERT INTO USUARIO(id,nome,matricula,pin,ativo)
        VALUES(${usuario.id},'${usuario.nome}',${usuario.matricula},'${usuario.pin}',${usuario.ativo})
        ''';
  }

  static String alterar(Usuario usuario) {
    return '''
        UPDATE USUARIO SET id = ${usuario.id},nome = '${usuario.nome}',matricula = ${usuario.matricula},
        pin = '${usuario.pin}',ativo = ${usuario.ativo} WHERE id = ${usuario.id}
        ''';
  }

  static String listarUsuario() {
    return 'SELECT * FROM USUARIO;';
  }

  static String isCadastrado(id){
    return '''
        SELECT COUNT(*) AS qtd FROM USUARIO WHERE id = ${id};
    ''';
  }

  static String autenticar(matricula,senha){
    return '''
      SELECT * FROM USUARIO WHERE matricula = ${matricula} AND pin = ${senha}; 
    ''';
  }
}
