import { Pergunta } from "./pergunta";

export class Alternativa{
    id:number;
	pergunta:Pergunta;
	descricao:string;
	flagNconf:number;
	ordemApresentacao:number;
	prioridadeNconf:number;
	recomendacaoNconf:string;
	dataAtualizacao:string;
	ativo:number;
}