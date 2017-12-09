// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

import {HttpClient} from '@angular/common/http';
import {ActionReducer, MetaReducer} from '@ngrx/store';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';

export const environment = {
  production: false
};

export function TranslateLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

export function logger(reducer: ActionReducer<any>): ActionReducer<any> {
  return function (state: any, action: any): any {
    console.log('state', state);
    console.log('action', action);
    return reducer(state, action);
  };
}

export const metaReducers: MetaReducer<any>[] = [logger];

export const baseUrl = '192.168.0.97:8080/cargo';
export const baseApiUrl = `http://${baseUrl}/api`;
export const baseWsUrl = `ws://${baseUrl}`;
