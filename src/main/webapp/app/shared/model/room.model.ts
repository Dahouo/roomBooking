import { IAsset } from 'app/shared/model/asset.model';
import { IBooking } from 'app/shared/model/booking.model';

export const enum Status {
    FREE = 'FREE',
    BOOKED = 'BOOKED'
}

export interface IRoom {
    id?: number;
    name?: string;
    image?: string;
    floor?: number;
    capacity?: number;
    location?: string;
    status?: Status;
    assets?: IAsset;
    bookings?: IBooking[];
}

export class Room implements IRoom {
    constructor(
        public id?: number,
        public name?: string,
        public image?: string,
        public floor?: number,
        public capacity?: number,
        public location?: string,
        public status?: Status,
        public assets?: IAsset,
        public bookings?: IBooking[]
    ) {}
}
