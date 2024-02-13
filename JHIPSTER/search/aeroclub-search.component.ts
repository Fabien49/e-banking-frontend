import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAeroclub } from '../aeroclub.model';

@Component({
  selector: 'jhi-search-detail',
  templateUrl: './aeroclub-search.component.html',
})
export class AeroclubSearchComponent implements OnInit {
  aeroclub: IAeroclub | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aeroclub }) => {
      this.aeroclub = aeroclub;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
