import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot} from '@angular/router';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs/Observable';
import {coreAuthOperator} from '../store/core';

@Injectable()
export class AdminGuard implements CanActivate {
  constructor(private store: Store<any>) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return this.store.select(coreAuthOperator)
      .map(it => it && it.admin);
  }
}
