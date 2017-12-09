import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {of} from 'rxjs/observable/of';
import {baseApiUrl} from '../../../environments/environment';
import {CarNoInfoLog} from '../../shared/models/car-no-info-log';
import {Kna1} from '../../shared/models/kna1';
import {Lfa1} from '../../shared/models/lfa1';
import {MegSendInfo} from '../../shared/models/meg-send-info';
import {Operator} from '../../shared/models/operator';
import {OperatorPermission} from '../../shared/models/operator-permission';
import {PtaSendInfo} from '../../shared/models/pta-send-info';
import {ReceiveT001} from '../../shared/models/receive-t001';
import {ReceiveT001l} from '../../shared/models/receive-t001l';
import {T001} from '../../shared/models/t001';
import {T001l} from '../../shared/models/t001l';
import {T001lPk} from '../../shared/models/t001l-pk';
import {TransCorp} from '../../shared/models/trans-corp';
import {WharfT001l} from '../../shared/models/wharf-t001l';
import {Ylips} from '../../shared/models/ylips';

@Injectable()
export class ApiService {
  constructor(private http: HttpClient) {
  }

  getAuth(): Observable<{ operator: Operator, permission: OperatorPermission }> {
    return this.http.get<{ operator: Operator, permission: OperatorPermission }>(`${baseApiUrl}/auth`);
  }

  saveReceiveT001(receiveT001: ReceiveT001): Observable<ReceiveT001> {
    return this.http.post<ReceiveT001>(`${baseApiUrl}/receiveT001s`, receiveT001);
  }

  listReceiveT001(): Observable<ReceiveT001[]> {
    return this.http.get<ReceiveT001[]>(`${baseApiUrl}/receiveT001s`);
  }

  deleteReceiveT001(bukrs: string): Observable<any> {
    return this.http.delete(`${baseApiUrl}/receiveT001s/${bukrs}`);
  }

  saveReceiveT001l(receiveT001l: ReceiveT001l): Observable<ReceiveT001l> {
    return this.http.post<ReceiveT001l>(`${baseApiUrl}/receiveT001ls`, receiveT001l);
  }

  deleteReceiveT001l(pk: T001lPk): Observable<any> {
    const params = new HttpParams().set('werks', pk.werks).set('lgort', pk.lgort);
    return this.http.delete(`${baseApiUrl}/receiveT001ls`, {params});
  }

  listReceiveT001l(bukrs?: string): Observable<ReceiveT001l[]> {
    if (bukrs) {
      const params = new HttpParams().set('bukrs', bukrs);
      return this.http.get<WharfT001l[]>(`${baseApiUrl}/receiveT001ls`, {params});
    }
    return this.http.get<ReceiveT001l[]>(`${baseApiUrl}/receiveT001ls`);
  }

  saveWharfT001l(wharfT001l: WharfT001l): Observable<WharfT001l> {
    return this.http.post<WharfT001l>(`${baseApiUrl}/wharfT001ls`, wharfT001l);
  }

  deleteWharfT001l(pk: T001lPk): Observable<any> {
    const params = new HttpParams().set('werks', pk.werks).set('lgort', pk.lgort);
    return this.http.delete(`${baseApiUrl}/wharfT001ls`, {params});
  }

  listWharfT001l(bukrs?: string): Observable<WharfT001l[]> {
    if (bukrs) {
      const params = new HttpParams().set('bukrs', bukrs);
      return this.http.get<WharfT001l[]>(`${baseApiUrl}/wharfT001ls`, {params});
    }
    return this.http.get<WharfT001l[]>(`${baseApiUrl}/wharfT001ls`);
  }

  listTransCorp(): Observable<TransCorp[]> {
    return this.http.get<TransCorp[]>(`${baseApiUrl}/transCorps`);
  }

  getTransCorp_T001s(id: string): Observable<T001[]> {
    return this.http.get<T001[]>(`${baseApiUrl}/transCorps/${id}/t001s`);
  }

  saveTransCorp(transCorp: TransCorp): Observable<TransCorp> {
    return transCorp.id ? this.updateTransCorp(transCorp) : this.createTransCorp(transCorp);
  }

  deleteTransCorp(id: string): Observable<any> {
    return this.http.delete(`${baseApiUrl}/transCorps/${id}`);
  }

  listOperator(params: HttpParams): Observable<{ count: number, operators: Operator[] }> {
    return this.http.get<{ count: number, operators: Operator[] }>(`${baseApiUrl}/operators`, {params});
  }

  saveOperatorPermission(operatorId: string, permission: OperatorPermission): Observable<OperatorPermission> {
    return this.http.put<OperatorPermission>(`${baseApiUrl}/operators/${operatorId}/permission`, permission);
  }

  getOperator_Permission(operatorId: string): Observable<OperatorPermission> {
    return this.http.get<OperatorPermission>(`${baseApiUrl}/operators/${operatorId}/permission`);
  }

  listT001(): Observable<T001[]> {
    return this.http.get<T001[]>(`${baseApiUrl}/t001s`);
  }

  listT001l(): Observable<T001l[]> {
    return this.http.get<T001l[]>(`${baseApiUrl}/t001ls`);
  }

  listSapYlips(params: HttpParams): Observable<Ylips[]> {
    return this.http.get<Ylips[]>(`${baseApiUrl}/sapYlipses`, {params});
  }

  listSendInfo(params: HttpParams): Observable<{ ptas: PtaSendInfo[], megs: MegSendInfo[] }> {
    return this.http.get<{ ptas: PtaSendInfo[], megs: MegSendInfo[] }>(`${baseApiUrl}/reports/sendInfos`, {params});
  }

  listYlips(params: HttpParams): Observable<Ylips[]> {
    return this.http.get<Ylips[]>(`${baseApiUrl}/ylipses`, {params});
  }

  savePtaSendInfo(ylipsId: string, sendInfo: PtaSendInfo): Observable<PtaSendInfo> {
    return sendInfo.id ? this.updatePtaSendInfo(ylipsId, sendInfo) : this.createPtaSendInfo(ylipsId, sendInfo);
  }

  getPtaSendInfo(sendInfoId: string): Observable<PtaSendInfo> {
    return this.http.get<PtaSendInfo>(`${baseApiUrl}/ptaSendInfos/${sendInfoId}`);
  }

  deletePtaSendInfo(id: string): Observable<any> {
    return this.http.delete(`${baseApiUrl}/ptaSendInfos/${id}`);
  }

  getYlips(ylipsId: string): Observable<Ylips> {
    return this.http.get<Ylips>(`${baseApiUrl}/ylipses/${ylipsId}`);
  }

  saveMegSendInfo(sendInfo: MegSendInfo): Observable<MegSendInfo> {
    return sendInfo.id ? this.updateMegSendInfo(sendInfo) : this.createMegSendInfo(sendInfo);
  }

  getMegSendInfo(sendInfoId: string): Observable<MegSendInfo> {
    return this.http.get<MegSendInfo>(`${baseApiUrl}/megSendInfos/${sendInfoId}`);
  }

  deleteMegSendInfo(id: string): Observable<any> {
    return this.http.delete(`${baseApiUrl}/megSendInfos/${id}`);
  }

  listGpsYlips(params: HttpParams): Observable<Ylips[]> {
    return this.http.get<Ylips[]>(`${baseApiUrl}/reports/gpsYlipses`, {params});
  }

  getCarNoInfoLog(carNo: string): Observable<CarNoInfoLog> {
    return this.http.get<CarNoInfoLog>(`${baseApiUrl}/carNoInfoLogs/${carNo}`);
  }

  autocompleteCarNoInfoLog(q: string, bukrs?: string): Observable<CarNoInfoLog[]> {
    if (!q) {
      return of([]);
    }
    const params = new HttpParams().set('q', q);
    return this.http.get<CarNoInfoLog[]>(`${baseApiUrl}/autocompletes/carNoInfoLog`, {params});
  }

  autocompleteLfa1(q: string, bukrs?: string): Observable<Lfa1[]> {
    const params = new HttpParams().set('q', q).set('bukrs', bukrs);
    return this.http.get<Lfa1[]>(`${baseApiUrl}/autocompletes/lfa1`, {params});
  }

  autocompleteKna1(q: string, bukrs?: string): Observable<Kna1[]> {
    const params = new HttpParams().set('q', q).set('bukrs', bukrs);
    return this.http.get<Kna1[]>(`${baseApiUrl}/autocompletes/kna1`, {params});
  }

  private updateTransCorp(transCorp: TransCorp): Observable<TransCorp> {
    return this.http.put<TransCorp>(`${baseApiUrl}/transCorps/${transCorp.id}`, transCorp);
  }

  private createTransCorp(transCorp: TransCorp): Observable<TransCorp> {
    return this.http.post<TransCorp>(`${baseApiUrl}/transCorps`, transCorp);
  }

  private updatePtaSendInfo(ylipsId: string, sendInfo: PtaSendInfo): Observable<PtaSendInfo> {
    const params = new HttpParams().set('ylipsId', ylipsId || '');
    return this.http.put<PtaSendInfo>(`${baseApiUrl}/ptaSendInfos/${sendInfo.id}`, sendInfo, {params});
  }

  private createPtaSendInfo(ylipsId: string, sendInfo: PtaSendInfo): Observable<PtaSendInfo> {
    return this.http.post<PtaSendInfo>(`${baseApiUrl}/ptaSendInfos?ylipsId=${ylipsId || ''}`, sendInfo);
  }

  private updateMegSendInfo(sendInfo: MegSendInfo): Observable<MegSendInfo> {
    return this.http.put<MegSendInfo>(`${baseApiUrl}/megSendInfos/${sendInfo.id}`, sendInfo);
  }

  private createMegSendInfo(sendInfo: MegSendInfo): Observable<MegSendInfo> {
    return this.http.post<MegSendInfo>(`${baseApiUrl}/megSendInfos`, sendInfo);
  }
}
