import { ModalContatoComponent } from './../modal-contato/modal-contato.component';
import { ContatoFilter } from './../../../shared/filter/contato-filter';
import { UtilityService } from './../../../shared/utility.service';
import { ContatoService } from './../../../shared/service/contato.service';
import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { TipoMensagem } from 'src/app/shared/enum/tipo-mensagem.enum';
import { Contato } from 'src/app/shared/model/contato';
import { Regional } from 'src/app/shared/model/regional';
import { ConfirmDialogComponent } from 'src/app/core/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-modal-lista-contato',
  templateUrl: './modal-lista-contato.component.html',
  styleUrls: ['./modal-lista-contato.component.scss']
})
export class ModalListaContatoComponent implements OnInit {

  filter = new ContatoFilter();
  contato = new Contato();
  regional = new Regional();
  displayedColumns: string[] = ['nome', 'funcao', 'email','opcao'];
  dataSource = new MatTableDataSource<any>([]);
  msgAlteracao:string;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(
    private utility:UtilityService,
    private contatoService:ContatoService,
    private dialog:MatDialog,
    private dialogRef:MatDialogRef<ModalListaContatoComponent>,
    @Inject(MAT_DIALOG_DATA) public data:any 
  ) { }

  ngOnInit(): void {
    this.dataSource.paginator = this.utility.getTraducaoTabela(this.paginator);
    this.pesquisar();
  }

  pesquisar(){
    this.filter = new ContatoFilter();
    this.filter.idRegional = this.data.idRegional;
    this.filter.ativo = 1;
    this.contatoService.pesquisar(this.filter)
    .then(response=>{
      this.dataSource = new MatTableDataSource<any>(response);
      this.dataSource.paginator = this.paginator;
    })
  }

  openDialog(entity){
    const dialogRef = this.dialog.open(ModalContatoComponent,{
      width:'480px',
      data:{
        id:entity.id,
        nome:entity.nome,
        funcao:entity.funcao,
        email:entity.email,
        dataAtualizacao:entity.dataAtualizacao,
        ativo:entity.ativo,
        regional:entity.regional
      }
    })
    dialogRef.afterClosed().subscribe(result=>{
      if (result != undefined) {
        if (result.id) {
          this.msgAlteracao = 'Alterado com sucesso!';
          this.alterar(result);
        } else {
          this.incluir(result)
        }
      }
    })
  }

  novo(){
    this.contato = new Contato();
    this.regional = new Regional();
    this.regional.id = this.data.idRegional;
    this.contato.regional = this.regional;
    this.openDialog(this.contato);
  }

  incluir(entity:any){
    this.contatoService.incluir(entity)
    .then(response =>{
      this.utility.openSnackBar('Incluido com sucesso!',TipoMensagem.sucesso);
      this.pesquisar();
    }).catch(errorCustom => {
      this.utility.openSnackBar(errorCustom.error[0].mensagemUsuario, TipoMensagem.erro);
    })
  }

  editar(row:any){
    this.contato = new Contato();
    this.contato = row;
    this.openDialog(this.contato);
  }

  alterar(entity:any){
    this.contatoService.alterar(entity)
    .then(response=>{
      this.utility.openSnackBar(this.msgAlteracao,TipoMensagem.sucesso);
      this.msgAlteracao = '';
      this.pesquisar();
    }).catch(errorCustom => {
      this.utility.openSnackBar(errorCustom.error[0].mensagemUsuario, TipoMensagem.erro);
    })
  }

  confirmDialog(row:any){
    let data = {
      'acao': 0
    };
    const confirmDialog = this.dialog.open(ConfirmDialogComponent,{
      width: '350px',
      data:data
    })
    confirmDialog.afterClosed().subscribe(result => {
      if(result.acao == 1){
        this.contatoService.deletar(row.id)
        .then(response=>{
          this.pesquisar();
        })
        .catch(errorCustom => {
          this.utility.openSnackBar(errorCustom.error[0].mensagemUsuario, TipoMensagem.erro);
        })
      }
    })
  }


}
