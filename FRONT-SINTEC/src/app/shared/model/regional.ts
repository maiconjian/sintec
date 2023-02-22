import { Contato } from './contato';
import { Contrato } from './contrato';
export class Regional{

	id:number;
	codigo:string;
	nome:string;
	dataAtualizacao:string;
	ativo:number;
	contrato:Contrato;
}