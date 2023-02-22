import { Component, OnInit } from '@angular/core';
import { UtilityService } from 'src/app/shared/utility.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  
  constructor(
    private utility: UtilityService
  ) { }

  ngOnInit(): void {
    this.utility.setTitlePage('Home');
  }
}
