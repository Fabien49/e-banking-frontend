import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IRevision, Revision } from '../revision.model';
import { RevisionService } from '../service/revision.service';

@Component({
  selector: 'jhi-revision-update',
  templateUrl: './revision-update.component.html',
})
export class RevisionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    niveaux: [],
    pression: [],
    carroserie: [],
    date: [],
  });

  constructor(protected revisionService: RevisionService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ revision }) => {
      if (revision.id === undefined) {
        const today = dayjs().startOf('day');
        revision.date = today;
      }

      this.updateForm(revision);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const revision = this.createFromForm();
    if (revision.id !== undefined) {
      this.subscribeToSaveResponse(this.revisionService.update(revision));
    } else {
      this.subscribeToSaveResponse(this.revisionService.create(revision));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRevision>>): void {
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

  protected updateForm(revision: IRevision): void {
    this.editForm.patchValue({
      id: revision.id,
      niveaux: revision.niveaux,
      pression: revision.pression,
      carroserie: revision.carroserie,
      date: revision.date ? revision.date.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IRevision {
    return {
      ...new Revision(),
      id: this.editForm.get(['id'])!.value,
      niveaux: this.editForm.get(['niveaux'])!.value,
      pression: this.editForm.get(['pression'])!.value,
      carroserie: this.editForm.get(['carroserie'])!.value,
      date: this.editForm.get(['date'])!.value ? dayjs(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
