import { CategoriaVeiculo } from './categoria-veiculo';
import { Colaborador } from './colaborador';
import { Regional } from "./regional";

export class Veiculo{
    id:number;
	placa:string;
	cor:string;
	ano:number;
	modelo:string;
	regional:Regional;
	colaborador:Colaborador;
	categoriaVeiculo:CategoriaVeiculo;
	dataVencimentoDoc:string;
	dataAtualizacao:string;
	ativo:number;
}