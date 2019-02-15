export interface IAsset {
    id?: number;
    teleconference?: boolean;
    projector?: boolean;
    whiteboard?: boolean;
    aC?: boolean;
    internet?: boolean;
}

export class Asset implements IAsset {
    constructor(
        public id?: number,
        public teleconference?: boolean,
        public projector?: boolean,
        public whiteboard?: boolean,
        public aC?: boolean,
        public internet?: boolean
    ) {
        this.teleconference = this.teleconference || false;
        this.projector = this.projector || false;
        this.whiteboard = this.whiteboard || false;
        this.aC = this.aC || false;
        this.internet = this.internet || false;
    }
}
