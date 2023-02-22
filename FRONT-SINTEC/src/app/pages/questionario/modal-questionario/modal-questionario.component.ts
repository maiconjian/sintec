import { TipoServico } from './../../../shared/model/tipo-servico';
import { Contrato } from './../../../shared/model/contrato';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TipoServicoFilter } from 'src/app/shared/filter/tipo-servico-filter';
import { TipoServicoService } from 'src/app/shared/service/tipo-servico.service';
import { UtilityService } from 'src/app/shared/utility.service';
import { ModalEmpresaComponent } from '../../empresa/modal-empresa/modal-empresa.component';

@Component({
  selector: 'app-modal-questionario',
  templateUrl: './modal-questionario.component.html',
  styleUrls: ['./modal-questionario.component.scss']
})
export class ModalQuestionarioComponent implements OnInit {

  contrato = new Contrato();
  tipoServico = new TipoServico();

  titulo: string;
  listaContratoCarregado: any[] = [];
  listaTipoServicoCarregado: any[] = [];

  constructor(
    private utility: UtilityService,
    private tipoServicoService: TipoServicoService,
    private dialogRef: MatDialogRef<ModalEmpresaComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }


  ngOnInit(): void {
    this.listaContratoCarregado = this.utility.carregarComboContratoUsuario();
    if (this.data.id) {
      this.titulo = 'Alterar';
      this.contrato = this.data.tipoServico.contrato;
      this.carregarTipoServico();
      this.tipoServico = this.data.tipoServico;
    } else {
      this.titulo = 'Incluir';
    }
  }

  carregarTipoServico() {
    this.listaTipoServicoCarregado = [];
    let filterTipoServico = new TipoServicoFilter();
    filterTipoServico.idContrato = this.contrato.id;
    filterTipoServico.ativo = 1;
    this.tipoServicoService.pesquisar(filterTipoServico)
      .then(response => {
        response.forEach(element => {
          this.listaTipoServicoCarregado.push({
            label: element.nome,
            value: element.id
          });
        });
      })
  }

  onNoClick(): void {
    this.tipoServico = new TipoServico();
    this.dialogRef.close();
  }

  montarObj() {
    this.data.tipoServico = this.tipoServico;
  }

  formatarTexto(event){
    let str:string = event;
    // str.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
    str.toUpperCase();
    return str;
  }
}
