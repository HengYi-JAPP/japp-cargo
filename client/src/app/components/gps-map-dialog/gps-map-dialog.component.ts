import {ChangeDetectionStrategy, Component, HostBinding, Inject, OnDestroy} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material';
import {Store} from '@ngrx/store';
import {TranslateService} from '@ngx-translate/core';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/operator/switchMap';
import {Observable} from 'rxjs/Observable';
import {Observer} from 'rxjs/Observer';
import {Subject} from 'rxjs/Subject';
import {baseWsUrl} from '../../../environments/environment';
import {CarGpsData} from '../../models/car-gps-data';
import {Ylips} from '../../models/ylips';

const carIconUrl = 'http://developer.baidu.com/map/jsdemo/img/car.png';
const carIcon = new BMap.Icon(carIconUrl, new BMap.Size(52, 26));
const ptStart = new BMap.Point(121.767432, 29.972508); // 宁波
const ptEnd = new BMap.Point(120.409662, 30.168825); // 衙前
const bmapStyle = 'googlelite';
const zoomLevel = 14;

@Component({
  selector: 'jcargo-gps-map-dialog',
  templateUrl: './gps-map-dialog.component.html',
  styleUrls: ['./gps-map-dialog.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class GpsMapDialogComponent implements OnDestroy {
  @HostBinding('class.jcargo-dialog-comp') b1 = true;
  @HostBinding('class.jcargo-gps-map-dialog') b2 = true;
  readonly dialogTitle: string;
  readonly mapDomId = 'gpsCarMap-bmap';
  readonly ylips: Ylips;
  private ws: Subject<MessageEvent>;
  private map: any;
  private carMarker: any;
  private carMarkerInfo: any;

  constructor(private store: Store<any>,
              private translate: TranslateService,
              private dialog: MatDialog,
              private dialogRef: MatDialogRef<GpsMapDialogComponent>,
              @Inject(MAT_DIALOG_DATA)  data: { ylips: Ylips }) {
    this.ylips = data.ylips;
    this.dialogTitle = this.ylips.carNo;
    this.ensureWs();
    this.ws.subscribe((ev: MessageEvent) => {
      this.ensureMap();
      const gpsData: CarGpsData = JSON.parse(ev.data);
      this.display(gpsData);
    });
  }

  static open(dialog: MatDialog, data: { ylips: Ylips }): MatDialogRef<GpsMapDialogComponent> {
    return dialog.open(GpsMapDialogComponent, {disableClose: true, panelClass: 'my-dialog', data});
  }

  ngOnDestroy(): void {
    this.ws.complete();
  }

  private display(gpsData: CarGpsData) {
    const pt = new BMap.Point(gpsData.longitude, gpsData.latitude);
    if (this.carMarker) {
      this.map.panTo(pt);
      this.carMarker.setPosition(pt);
      this.getCarMarkerContent(gpsData).subscribe(content => {
        this.carMarkerInfo.setContent(content);
        if (!this.carMarkerInfo.isOpen()) {
          this.carMarker.openInfoWindow(this.carMarkerInfo);
        }
      });
    } else {
      this.map.centerAndZoom(pt, zoomLevel);
      this.carMarker = new BMap.Marker(pt, {icon: carIcon, offset: new BMap.Size(0, 0)});
      this.map.addOverlay(this.carMarker);
      this.getCarMarkerContent(gpsData).subscribe(content => {
        this.carMarkerInfo = new BMap.InfoWindow(content);
        this.carMarkerInfo.disableCloseOnClick();
        this.carMarker.openInfoWindow(this.carMarkerInfo);
        this.carMarker.addEventListener('click', function () {
          this.carMarker.openInfoWindow(this.carMarkerInfo);
        });
      });
    }
  }

  private getCarMarkerContent(gpsData: CarGpsData): Observable<string> {
    return this.translate.get(['time', 'carMarkLabel.province', 'carMarkLabel.road', 'carMarkLabel.near', 'speed', 'carMarkLabel.speedUnit', 'direction', 'carMarkLabel.km', 'carMarkLabel.kmUnit'])
      .map((s: any) => {
        const {time, speed, direction} = s;
        const [province, road, near, speedUnit, km, kmUnit] = [s['carMarkLabel.province'], s['carMarkLabel.road'], s['carMarkLabel.near'], s['carMarkLabel.speedUnit'], s['carMarkLabel.km'], s['carMarkLabel.kmUnit']];
        const content = [`${time}：${gpsData.dateTimeString}`];
        if (gpsData.province) {
          content.push(`${province}：${gpsData.province}`);
        }
        if (gpsData.road) {
          content.push(`${road}：${gpsData.road}`);
        }
        if (gpsData.near) {
          content.push(`${near}：${gpsData.near}`);
        }
        content.push(`${speed}：${gpsData.speed}${speedUnit}`);
        content.push(`${direction}：${gpsData.direction}`);
        content.push(`${km}：${gpsData.km}${kmUnit}`);
        return '<font color="blue" size="2pt">' + content.join('<br/>') + '</font>';
      });
  }

  private ensureWs() {
    if (this.ws) {
      return;
    }
    const url = `${baseWsUrl}/gps/${this.ylips.carNo}`;
    const ws = new WebSocket(url);
    const observable = Observable.create(
      (obs: Observer<MessageEvent>) => {
        ws.onmessage = obs.next.bind(obs);
        ws.onerror = obs.error.bind(obs);
        ws.onclose = obs.complete.bind(obs);
        return ws.close.bind(ws);
      }
    );
    const observer = {
      next: (data: string) => {
        if (ws.readyState === WebSocket.OPEN) {
          ws.send(data);
        }
      },
    };
    this.ws = Subject.create(observer, observable);
  }

  private ensureMap() {
    if (this.map) {
      return;
    }
    this.map = new BMap.Map(this.mapDomId, {enableMapClick: false});
    this.map.enableScrollWheelZoom();
    this.map.setMapStyle({style: bmapStyle});
    this.map.addOverlay(new BMap.Marker(ptStart));
    this.map.addOverlay(new BMap.Marker(ptEnd));
  }

}
