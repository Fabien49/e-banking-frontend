import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAvion } from '../avion.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-avion-detail',
  templateUrl: './avion-detail.component.html',
})
export class AvionDetailComponent implements OnInit {
  avion: IAvion | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avion }) => {
      this.avion = avion;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
