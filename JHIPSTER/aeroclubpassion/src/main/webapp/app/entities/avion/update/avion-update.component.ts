import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAvion, Avion } from '../avion.model';
import { AvionService } from '../service/avion.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IAtelier } from 'app/entities/atelier/atelier.model';
import { AtelierService } from 'app/entities/atelier/service/atelier.service';
import { IRevision } from 'app/entities/revision/revision.model';
import { RevisionService } from 'app/entities/revision/service/revision.service';

@Component({
  selector: 'jhi-avion-update',
  templateUrl: './avion-update.component.html',
})
export class AvionUpdateComponent implements OnInit {
  isSaving = false;

  ateliersSharedCollection: IAtelier[] = [];
  revisionsSharedCollection: IRevision[] = [];

  editForm = this.fb.group({
    id: [],
    marque: [null, [Validators.required, Validators.maxLength(80)]],
    type: [],
    moteur: [],
    puissance: [],
    place: [null, [Validators.required]],
    autonomie: [],
    usage: [],
    heures: [null, [Validators.required]],
    image: [],
    imageContentType: [],
    atelier: [],
    revision: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected avionService: AvionService,
    protected atelierService: AtelierService,
    protected revisionService: RevisionService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avion }) => {
      this.updateForm(avion);

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('aeroclubpassionApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const avion = this.createFromForm();
    if (avion.id !== undefined) {
      this.subscribeToSaveResponse(this.avionService.update(avion));
    } else {
      this.subscribeToSaveResponse(this.avionService.create(avion));
    }
  }

  trackAtelierById(index: number, item: IAtelier): number {
    return item.id!;
  }

  trackRevisionById(index: number, item: IRevision): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAvion>>): void {
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

  protected updateForm(avion: IAvion): void {
    this.editForm.patchValue({
      id: avion.id,
      marque: avion.marque,
      type: avion.type,
      moteur: avion.moteur,
      puissance: avion.puissance,
      place: avion.place,
      autonomie: avion.autonomie,
      usage: avion.usage,
      heures: avion.heures,
      image: avion.image,
      imageContentType: avion.imageContentType,
      atelier: avion.atelier,
      revision: avion.revision,
    });

    this.ateliersSharedCollection = this.atelierService.addAtelierToCollectionIfMissing(this.ateliersSharedCollection, avion.atelier);
    this.revisionsSharedCollection = this.revisionService.addRevisionToCollectionIfMissing(this.revisionsSharedCollection, avion.revision);
  }

  protected loadRelationshipsOptions(): void {
    this.atelierService
      .query()
      .pipe(map((res: HttpResponse<IAtelier[]>) => res.body ?? []))
      .pipe(
        map((ateliers: IAtelier[]) => this.atelierService.addAtelierToCollectionIfMissing(ateliers, this.editForm.get('atelier')!.value))
      )
      .subscribe((ateliers: IAtelier[]) => (this.ateliersSharedCollection = ateliers));

    this.revisionService
      .query()
      .pipe(map((res: HttpResponse<IRevision[]>) => res.body ?? []))
      .pipe(
        map((revisions: IRevision[]) =>
          this.revisionService.addRevisionToCollectionIfMissing(revisions, this.editForm.get('revision')!.value)
        )
      )
      .subscribe((revisions: IRevision[]) => (this.revisionsSharedCollection = revisions));
  }

  protected createFromForm(): IAvion {
    return {
      ...new Avion(),
      id: this.editForm.get(['id'])!.value,
      marque: this.editForm.get(['marque'])!.value,
      type: this.editForm.get(['type'])!.value,
      moteur: this.editForm.get(['moteur'])!.value,
      puissance: this.editForm.get(['puissance'])!.value,
      place: this.editForm.get(['place'])!.value,
      autonomie: this.editForm.get(['autonomie'])!.value,
      usage: this.editForm.get(['usage'])!.value,
      heures: this.editForm.get(['heures'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      atelier: this.editForm.get(['atelier'])!.value,
      revision: this.editForm.get(['revision'])!.value,
    };
  }
}
