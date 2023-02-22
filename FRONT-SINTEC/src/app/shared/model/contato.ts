import { Regional } from 'src/app/shared/model/regional';
export class Contato{
    id;
	nome:string;
	email:string;
	funcao:string;
	regional:Regional;
	dataAtualizacao:string;
	ativo:number;
}