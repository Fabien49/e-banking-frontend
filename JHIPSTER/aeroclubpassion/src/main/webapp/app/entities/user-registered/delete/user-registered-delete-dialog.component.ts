import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserRegistered } from '../user-registered.model';
import { UserRegisteredService } from '../service/user-registered.service';

@Component({
  templateUrl: './user-registered-delete-dialog.component.html',
})
export class UserRegisteredDeleteDialogComponent {
  userRegistered?: IUserRegistered;

  constructor(protected userRegisteredService: UserRegisteredService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userRegisteredService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
