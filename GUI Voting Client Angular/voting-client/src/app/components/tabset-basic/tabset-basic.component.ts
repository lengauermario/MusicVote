import { Component, OnInit } from '@angular/core';
import { NgbTabsetConfig } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-tabset-basic',
  templateUrl: './tabset-basic.component.html',
  styleUrls: ['./tabset-basic.component.css'],
  providers: [NgbTabsetConfig]
})
export class TabsetBasicComponent implements OnInit {

  constructor(config: NgbTabsetConfig) {
    config.justify = 'fill';
    config.type = 'tabs';
   }

  ngOnInit() {
  }

}
