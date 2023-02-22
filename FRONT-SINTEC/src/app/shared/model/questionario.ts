import { TipoServico } from './tipo-servico';
export class Questionario{
   
    id:number;
	titulo:string;
	ordemApresentacao:number;
	flagObrigatorio:number;
	tipoServico:TipoServico;
	dataAtualizacao:string;
	ativo:number;
}