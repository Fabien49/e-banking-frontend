import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IUserRegistered, UserRegistered } from '../user-registered.model';
import { UserRegisteredService } from '../service/user-registered.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

@Component({
  selector: 'jhi-user-registered-update',
  templateUrl: './user-registered-update.component.html',
})
export class UserRegisteredUpdateComponent implements OnInit {
  isSaving = false;

  usersSharedCollection: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.maxLength(80)]],
    prenom: [null, [Validators.maxLength(80)]],
    telephone: [null, [Validators.maxLength(10)]],
    mail: [null, [Validators.maxLength(80)]],
    adresse: [null, [Validators.maxLength(150)]],
    codePostal: [null, [Validators.maxLength(5)]],
    commune: [null, [Validators.maxLength(80)]],
    heureVol: [],
    user: [],
  });

  constructor(
    protected userRegisteredService: UserRegisteredService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userRegistered }) => {
      this.updateForm(userRegistered);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userRegistered = this.createFromForm();
    if (userRegistered.id !== undefined) {
      this.subscribeToSaveResponse(this.userRegisteredService.update(userRegistered));
    } else {
      this.subscribeToSaveResponse(this.userRegisteredService.create(userRegistered));
    }
  }

  trackUserById(index: number, item: IUser): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserRegistered>>): void {
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

  protected updateForm(userRegistered: IUserRegistered): void {
    this.editForm.patchValue({
      id: userRegistered.id,
      nom: userRegistered.nom,
      prenom: userRegistered.prenom,
      telephone: userRegistered.telephone,
      mail: userRegistered.mail,
      adresse: userRegistered.adresse,
      codePostal: userRegistered.codePostal,
      commune: userRegistered.commune,
      heureVol: userRegistered.heureVol,
      user: userRegistered.user,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, userRegistered.user);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('user')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }

  protected createFromForm(): IUserRegistered {
    return {
      ...new UserRegistered(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      mail: this.editForm.get(['mail'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      codePostal: this.editForm.get(['codePostal'])!.value,
      commune: this.editForm.get(['commune'])!.value,
      heureVol: this.editForm.get(['heureVol'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }
}
