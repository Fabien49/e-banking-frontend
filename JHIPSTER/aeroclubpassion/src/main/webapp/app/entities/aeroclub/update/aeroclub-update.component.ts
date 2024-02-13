import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAeroclub, Aeroclub } from '../aeroclub.model';
import { AeroclubService } from '../service/aeroclub.service';

@Component({
  selector: 'jhi-aeroclub-update',
  templateUrl: './aeroclub-update.component.html',
})
export class AeroclubUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    oaci: [null, [Validators.required, Validators.maxLength(80)]],
    nom: [null, [Validators.required, Validators.maxLength(80)]],
    type: [null, [Validators.required, Validators.maxLength(80)]],
    telephone: [null, [Validators.required, Validators.maxLength(80)]],
    mail: [null, [Validators.required, Validators.maxLength(80)]],
    adresse: [null, [Validators.required, Validators.maxLength(150)]],
    codePostal: [null, [Validators.required, Validators.maxLength(80)]],
    commune: [null, [Validators.required, Validators.maxLength(80)]],
  });

  constructor(protected aeroclubService: AeroclubService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aeroclub }) => {
      this.updateForm(aeroclub);
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
      nom: aeroclub.nom,
      type: aeroclub.type,
      telephone: aeroclub.telephone,
      mail: aeroclub.mail,
      adresse: aeroclub.adresse,
      codePostal: aeroclub.codePostal,
      commune: aeroclub.commune,
    });
  }

  protected createFromForm(): IAeroclub {
    return {
      ...new Aeroclub(),
      id: this.editForm.get(['id'])!.value,
      oaci: this.editForm.get(['oaci'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      type: this.editForm.get(['type'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      mail: this.editForm.get(['mail'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      codePostal: this.editForm.get(['codePostal'])!.value,
      commune: this.editForm.get(['commune'])!.value,
    };
  }
}
