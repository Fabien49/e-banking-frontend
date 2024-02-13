import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITarif, Tarif } from '../tarif.model';
import { TarifService } from '../service/tarif.service';

@Component({
  selector: 'jhi-tarif-update',
  templateUrl: './tarif-update.component.html',
})
export class TarifUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    taxeAtterrissage: [],
    taxeParking: [],
    carburant: [],
  });

  constructor(protected tarifService: TarifService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tarif }) => {
      this.updateForm(tarif);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tarif = this.createFromForm();
    if (tarif.id !== undefined) {
      this.subscribeToSaveResponse(this.tarifService.update(tarif));
    } else {
      this.subscribeToSaveResponse(this.tarifService.create(tarif));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarif>>): void {
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

  protected updateForm(tarif: ITarif): void {
    this.editForm.patchValue({
      id: tarif.id,
      taxeAtterrissage: tarif.taxeAtterrissage,
      taxeParking: tarif.taxeParking,
      carburant: tarif.carburant,
    });
  }

  protected createFromForm(): ITarif {
    return {
      ...new Tarif(),
      id: this.editForm.get(['id'])!.value,
      taxeAtterrissage: this.editForm.get(['taxeAtterrissage'])!.value,
      taxeParking: this.editForm.get(['taxeParking'])!.value,
      carburant: this.editForm.get(['carburant'])!.value,
    };
  }
}
