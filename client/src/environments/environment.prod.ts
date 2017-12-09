import {HttpClient} from '@angular/common/http';
import {MetaReducer} from '@ngrx/store';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';

export const environment = {
  production: true
};

export function TranslateLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, 'assets/i18n/');
}

export const metaReducers: MetaReducer<any>[] = [];

export const baseUrl = 'task.hengyi.com:8080/cargo';
export const baseApiUrl = `http://${baseUrl}/api`;
export const baseWsUrl = `ws://${baseUrl}`;
