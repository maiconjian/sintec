import { Regional } from './regional';

export class Colaborador{
    id:number;
	nome:string;
	matricula:number;
	cpf:string;
	dataNascimento:string;
	dataAdmissao:string;
	cnh:string;
	dataValidadeCnh:string;
	categoriaCnh:string;
	regional:Regional;
	dataAtualizacao:string;
	ativo:number;
}