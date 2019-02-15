import { Moment } from 'moment';
import { IRoom } from 'app/shared/model/room.model';
import { IUser } from 'app/core/user/user.model';

export interface IBooking {
    id?: number;
    start?: Moment;
    end?: Moment;
    purpose?: string;
    room?: IRoom;
    user?: IUser;
}

export class Booking implements IBooking {
    constructor(
        public id?: number,
        public start?: Moment,
        public end?: Moment,
        public purpose?: string,
        public room?: IRoom,
        public user?: IUser
    ) {}
}
