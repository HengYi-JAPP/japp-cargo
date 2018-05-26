import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot} from '@angular/router';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs/Observable';
import {corePermission} from '../store/core';

@Injectable()
export class SendRoleGuard implements CanActivate {
  constructor(private store: Store<any>) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return this.store.select(corePermission)
      .map(it => !!(it && it.defaultReceiveT001));
  }
}
