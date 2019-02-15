import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAsset } from 'app/shared/model/asset.model';
import { AccountService } from 'app/core';
import { AssetService } from './asset.service';

@Component({
    selector: 'jhi-asset',
    templateUrl: './asset.component.html'
})
export class AssetComponent implements OnInit, OnDestroy {
    assets: IAsset[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected assetService: AssetService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.assetService
            .query()
            .pipe(
                filter((res: HttpResponse<IAsset[]>) => res.ok),
                map((res: HttpResponse<IAsset[]>) => res.body)
            )
            .subscribe(
                (res: IAsset[]) => {
                    this.assets = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAssets();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAsset) {
        return item.id;
    }

    registerChangeInAssets() {
        this.eventSubscriber = this.eventManager.subscribe('assetListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
