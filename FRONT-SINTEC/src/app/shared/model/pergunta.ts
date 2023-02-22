import { Questionario } from './questionario';
export class Pergunta{
    id:number;
	enunciado:string;
	ordemApresentacao:number;
	flagFoto:number;
	flagObrigatorio:number;
	questionario:Questionario;
	flagMultiplaEscolha:number;
	dataAtualizacao:string;
	ativo:number;
}