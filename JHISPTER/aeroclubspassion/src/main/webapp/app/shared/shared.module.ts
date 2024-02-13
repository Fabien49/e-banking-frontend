import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AeroclubspassionSharedLibsModule, AeroclubspassionSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [AeroclubspassionSharedLibsModule, AeroclubspassionSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [AeroclubspassionSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AeroclubspassionSharedModule {
  static forRoot() {
    return {
      ngModule: AeroclubspassionSharedModule
    };
  }
}
