import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UserRegisteredComponent } from './list/user-registered.component';
import { UserRegisteredDetailComponent } from './detail/user-registered-detail.component';
import { UserRegisteredUpdateComponent } from './update/user-registered-update.component';
import { UserRegisteredDeleteDialogComponent } from './delete/user-registered-delete-dialog.component';
import { UserRegisteredRoutingModule } from './route/user-registered-routing.module';

@NgModule({
  imports: [SharedModule, UserRegisteredRoutingModule],
  declarations: [
    UserRegisteredComponent,
    UserRegisteredDetailComponent,
    UserRegisteredUpdateComponent,
    UserRegisteredDeleteDialogComponent,
  ],
  entryComponents: [UserRegisteredDeleteDialogComponent],
})
export class UserRegisteredModule {}
