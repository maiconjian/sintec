import { PerfilAcesso } from './perfil-acesso';
import { Regional } from './regional';
export class Usuario{
	id:number;	
	nome:string;
	matricula:number;	
	login:string;	
	senha:string;
	email:string;	
	cpf:string;	
	pin:number;	
	perfil:PerfilAcesso;
	listaRegional:Regional[];
	dataAtualizacao:string;
	ativo:number;
}