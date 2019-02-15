import { NgModule } from '@angular/core';

import { RoomBookingSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [RoomBookingSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [RoomBookingSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class RoomBookingSharedCommonModule {}
