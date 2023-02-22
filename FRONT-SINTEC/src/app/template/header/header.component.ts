import { Component, OnInit } from '@angular/core';
import { UtilityService } from 'src/app/shared/utility.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  
  constructor(
    private utility: UtilityService
  ) { }

  ngOnInit(): void {
  }

  getTitle(): string {
    return this.utility.getTitlePage();
  }


}
