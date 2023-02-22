import { NavService } from './../shared/service/nav.service';
import { Directive, ElementRef, TemplateRef, ViewContainerRef, OnInit, Input } from '@angular/core';

@Directive({
  selector: '[hasPermission]'
})
export class HasPermissionDirective implements OnInit {

  private currentUser;
  private permissions =[];
  private logicalOp = 'AND'
  private isHidden = true;


  constructor(
    private element:ElementRef,
    private templateRef: TemplateRef<any>,
    private viewContainer:ViewContainerRef,
    private navBar:NavService
  ) { }

  ngOnInit(){
    this.navBar.currentUser.subscribe(user =>{
      this.currentUser = user;
      this.updateView();
    })
  }

  @Input()
  set hasPermission(val){
    this.permissions = val;
    this.updateView();
  }

  @Input()
  set hasPermissionOp(permop){
    this.logicalOp = permop;
    this.updateView();
  }

  private updateView(){
    if (this.checkPermission()) {
      if(this.isHidden) {
        this.viewContainer.createEmbeddedView(this.templateRef);
        this.isHidden = false;
      }
    } else {
      this.isHidden = true;
      this.viewContainer.clear();
    }
  }

  private checkPermission(){
    let hasPermission = false;

    if (this.currentUser && this.currentUser.permissions) {
      for (const checkPermission of this.permissions) {
        const permissionFound = this.currentUser.permissions.find(x => x.toUpperCase() === checkPermission.toUpperCase());

        if (permissionFound) {
          hasPermission = true;

          if (this.logicalOp === 'OR') {
            break;
          }
        } else {
          hasPermission = false;
          if (this.logicalOp === 'AND') {
            break;
          }
        }
      }
    }

    return hasPermission;
  }

}
