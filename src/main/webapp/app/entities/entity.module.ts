import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'room',
                loadChildren: './room/room.module#RoomBookingRoomModule'
            },
            {
                path: 'asset',
                loadChildren: './asset/asset.module#RoomBookingAssetModule'
            },
            {
                path: 'booking',
                loadChildren: './booking/booking.module#RoomBookingBookingModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RoomBookingEntityModule {}
