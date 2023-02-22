import { Regional } from "./regional";

export class Imovel {
    id:number;
	nome:string;
	bairro:string;
	logradouro:string;
	complemento:string;
	regional:Regional;
	dataAtualizacao:string;
	ativo:number;
}