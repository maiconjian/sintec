import { TipoServico } from './tipo-servico';
import { Empresa } from './empresa';
import { Usuario } from './usuario';
export class Contrato{
    
    id:number;
	nome:string;
	vigenciaInicio:string;
	vigenciaFim:string;
	email:string;
	empresa:Empresa;
	nomeGerente:string;
	dataAtualizacao:string;
	ativo:number;
}