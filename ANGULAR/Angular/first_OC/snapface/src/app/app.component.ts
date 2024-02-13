import { Component, OnInit } from '@angular/core';
import { interval, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  interval$!: Observable<number>;

  ngOnInit() {  
    this.interval$ = interval(500).pipe(
    map(value => 2 * (value + 1)))
    }; 

         
    }
