import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAeroclub, Aeroclub } from '../aeroclub.model';
import { AeroclubService } from '../service/aeroclub.service';
import { ITarif } from 'app/entities/tarif/tarif.model';
import { TarifService } from 'app/entities/tarif/service/tarif.service';

@Component({
  selector: 'jhi-aeroclub-update',
  templateUrl: './aeroclub-update.component.html',
})
export class AeroclubUpdateComponent implements OnInit {
  isSaving = false;

  tarifsCollection: ITarif[] = [];

  editForm = this.fb.group({
    id: [],
    oaci: [null, [Validators.required]],
    name: [null, [Validators.required]],
    type: [null, [Validators.required]],
    phoneNumber: [null, [Validators.required]],
    mail: [null, [Validators.required]],
    adresse: [null, [Validators.required]],
    codePostal: [null, [Validators.required]],
    commune: [null, [Validators.required]],
    tarif: [],
  });

  constructor(
    protected aeroclubService: AeroclubService,
    protected tarifService: TarifService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aeroclub }) => {
      this.updateForm(aeroclub);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const aeroclub = this.createFromForm();
    if (aeroclub.id !== undefined) {
      this.subscribeToSaveResponse(this.aeroclubService.update(aeroclub));
    } else {
      this.subscribeToSaveResponse(this.aeroclubService.create(aeroclub));
    }
  }

  trackTarifById(index: number, item: ITarif): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAeroclub>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(aeroclub: IAeroclub): void {
    this.editForm.patchValue({
      id: aeroclub.id,
      oaci: aeroclub.oaci,
      name: aeroclub.name,
      type: aeroclub.type,
      phoneNumber: aeroclub.phoneNumber,
      mail: aeroclub.mail,
      adresse: aeroclub.adresse,
      codePostal: aeroclub.codePostal,
      commune: aeroclub.commune,
      tarif: aeroclub.tarif,
    });

    this.tarifsCollection = this.tarifService.addTarifToCollectionIfMissing(this.tarifsCollection, aeroclub.tarif);
  }

  protected loadRelationshipsOptions(): void {
    this.tarifService
      .query({ filter: 'aeroclub-is-null' })
      .pipe(map((res: HttpResponse<ITarif[]>) => res.body ?? []))
      .pipe(map((tarifs: ITarif[]) => this.tarifService.addTarifToCollectionIfMissing(tarifs, this.editForm.get('tarif')!.value)))
      .subscribe((tarifs: ITarif[]) => (this.tarifsCollection = tarifs));
  }

  protected createFromForm(): IAeroclub {
    return {
      ...new Aeroclub(),
      id: this.editForm.get(['id'])!.value,
      oaci: this.editForm.get(['oaci'])!.value,
      name: this.editForm.get(['name'])!.value,
      type: this.editForm.get(['type'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      mail: this.editForm.get(['mail'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      codePostal: this.editForm.get(['codePostal'])!.value,
      commune: this.editForm.get(['commune'])!.value,
      tarif: this.editForm.get(['tarif'])!.value,
    };
  }
}
