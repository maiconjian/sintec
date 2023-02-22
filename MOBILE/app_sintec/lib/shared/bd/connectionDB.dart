// ignore_for_file: depend_on_referenced_packages, unused_import

import 'dart:convert';
import 'dart:io';
import 'package:flutter/widgets.dart';
import 'package:path/path.dart';
import 'package:path_provider/path_provider.dart';
import 'package:sqflite/sqflite.dart';

class ConnectionDB {
  final _databaseName = "sintec.db";
  final _databaseVersion = 1;

  // CONSTRUTOR COM ACESSO PRIVADO
  ConnectionDB._();
  // CRIAR INSTANCIA DO DB
  static final ConnectionDB instance = ConnectionDB._();
  // INSTANCIA DO SQLITE
  static Database? _database;

  get database async {
    if (_database != null) return _database;
    return await _initDataBase();
  }

  _initDataBase() async {
    Directory documentsDirectory = await getApplicationDocumentsDirectory();
    return await openDatabase(
      join(documentsDirectory.path, _databaseName),
      version: _databaseVersion,
      onCreate: _onCreate,
    );
  }

  _onCreate(db, versao) async {
    await db.execute(_usuario);
    await db.execute(_servico);
    await db.execute(_ocorrencia);
    await db.execute(_questionario);
    await db.execute(_pergunta);
    await db.execute(_alternativa);
    await db.execute(_retorno);
    await db.execute(_retornoFoto);
    await db.execute(_retornoResposta);
  }

  String get _usuario => '''
    CREATE TABLE IF NOT EXISTS usuario (
      id INTEGER,
      nome varchar(50),
      matricula INTEGER,
      pin varchar(50),
      ativo INTEGER
    );
  ''';

  String get _servico => '''
   CREATE TABLE IF NOT EXISTS servico(
      id INTEGER,
      codigo VARCHAR(50),
      data_ref DATE,
      id_contrato INTEGER,
      id_regional INTEGER,
      nome_regional VARCHAR(100),
      id_categoria_tipo_servico INTEGER,
      nome_categoria_tipo_servico VARCHAR(50),
      id_tipo_servico INTEGER,
      nome_tipo_servico VARCHAR(50),
      id_imovel INTEGER,
      nome_imovel VARCHAR(50),
      bairro_imovel VARCHAR(50),
      logradouro_imovel VARCHAR(50),
      numero_logradouro INTEGER,
      id_veiculo INTEGER,
      placa_veiculo VARCHAR(50),
      cor_veiculo VARCHAR(50),
      modelo_veiculo VARCHAR(50),
      id_colaborador INTEGER,
      nome_colaborador VARCHAR(100),
      cnh VARCHAR(50),
      validade_cnh VARCHAR(50),
      categoria_cnh VARCHAR(50),
      data_programada DATE,
      id_usuario INTEGER
   );
  ''';

  String get _ocorrencia => '''
    CREATE TABLE IF NOT EXISTS ocorrencia(
      id INTEGER,
      descricao VARCHAR(50),
      id_contrato INTEGER,
      ativo INTEGER
    );
  ''';

  String get _questionario => '''
    CREATE TABLE IF NOT EXISTS questionario(
      id INTEGER,
      titulo VARCHAR(100),
      ordem_apresentacao INTEGER,
      id_tipo_servico INTEGER,
      ativo INTEGER
    );
  ''';

  String get _pergunta => '''
    CREATE TABLE IF NOT EXISTS pergunta(
      id INTEGER,
      enunciado VARCHAR(100),
      ordem_apresentacao INTEGER,
      flag_foto INTEGER,
      flag_obrigatorio INTEGER,
      id_questionario INTEGER,
      flag_multipla_escolha INTEGER,
      ativo INTEGER
    )
  ''';

  String get _alternativa =>'''
    CREATE TABLE IF NOT EXISTS alternativa(
      id INTEGER,
      id_pergunta INTEGER,
      descricao VARCHAR(100),
      flag_nconf INTEGER,
      ordem_apresentacao INTEGER,
      ativo INTEGER
    )
  ''';



  String get _retorno => '''
    CREATE TABLE IF NOT EXISTS retorno(
      id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
      id_usuario INTEGER,
      id_servico INTEGER,
      id_ocorrencia INTEGER,
      data_execucao DATE,
      longitude VARCHAR(100),
      latitude VARCHAR(100),
      flag_racp INTEGER,
      modelo_aparelho VARCHAR(50),
      marca_aparelho VARCHAR(50),
      sincronizado INTEGER,
      ativo INTEGER
    );
  ''';

  String get _retornoFoto => '''
    CREATE TABLE IF NOT EXISTS retorno_foto(
      id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
      nome VARCHAR(100),
      longitude VARCHAR(100),
      latitude VARCHAR(100),
      path VARCHAR(250),
      id_servico INTEGER,
      id_questionario INTEGER,
      flag_assinatura INTEGER,
      observacao VARCHAR(100),
      data_atualizacao DATE,
      sincronizado INTEGER
    );
  ''';

  String get _retornoResposta => '''
    CREATE TABLE IF NOT EXISTS retorno_resposta(
      id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
      id_servico INTEGER,
      id_pergunta INTEGER,
      id_alternativa INTEGER,
      longitude VARCHAR(100),
      latitude VARCHAR(100),
      resposta VARCHAR(100),
      observacao VARCHAR(100),
      data_execucao DATE,
      sincronizado INTEGER
    );
  ''';


}
