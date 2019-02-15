import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRoom } from 'app/shared/model/room.model';
import { RoomService } from './room.service';
import { IAsset } from 'app/shared/model/asset.model';
import { AssetService } from 'app/entities/asset';

@Component({
    selector: 'jhi-room-update',
    templateUrl: './room-update.component.html'
})
export class RoomUpdateComponent implements OnInit {
    room: IRoom;
    isSaving: boolean;

    assets: IAsset[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected roomService: RoomService,
        protected assetService: AssetService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ room }) => {
            this.room = room;
        });
        this.assetService
            .query({ filter: 'room-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IAsset[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAsset[]>) => response.body)
            )
            .subscribe(
                (res: IAsset[]) => {
                    if (!this.room.assets || !this.room.assets.id) {
                        this.assets = res;
                    } else {
                        this.assetService
                            .find(this.room.assets.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IAsset>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IAsset>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IAsset) => (this.assets = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.room.id !== undefined) {
            this.subscribeToSaveResponse(this.roomService.update(this.room));
        } else {
            this.subscribeToSaveResponse(this.roomService.create(this.room));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoom>>) {
        result.subscribe((res: HttpResponse<IRoom>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAssetById(index: number, item: IAsset) {
        return item.id;
    }
}
