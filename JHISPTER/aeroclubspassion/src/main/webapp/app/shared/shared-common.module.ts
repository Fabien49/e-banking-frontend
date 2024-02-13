import { NgModule } from '@angular/core';

import { AeroclubspassionSharedLibsModule, FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [AeroclubspassionSharedLibsModule],
  declarations: [FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent],
  exports: [AeroclubspassionSharedLibsModule, FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent]
})
export class AeroclubspassionSharedCommonModule {}
