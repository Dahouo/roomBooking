import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IAsset } from 'app/shared/model/asset.model';
import { AssetService } from './asset.service';

@Component({
    selector: 'jhi-asset-update',
    templateUrl: './asset-update.component.html'
})
export class AssetUpdateComponent implements OnInit {
    asset: IAsset;
    isSaving: boolean;

    constructor(protected assetService: AssetService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ asset }) => {
            this.asset = asset;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.asset.id !== undefined) {
            this.subscribeToSaveResponse(this.assetService.update(this.asset));
        } else {
            this.subscribeToSaveResponse(this.assetService.create(this.asset));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAsset>>) {
        result.subscribe((res: HttpResponse<IAsset>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
