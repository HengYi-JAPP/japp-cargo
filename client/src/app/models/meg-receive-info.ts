import {SapReceiveInfo} from './sap-receive-info';
import {T001} from './t001';
import {T001l} from './t001l';
import {T001w} from './t001w';

export class MegReceiveInfo {
  id: string;
  t001: T001;
  t001w: T001w;
  t001l: T001l;
  receiveDate = new Date();
  lfimg1: number;
  lfimg2: number;
  lfimg: number;
  diffLfimg1: number;
  diffLfimg2: number;
  note: string;
  pickPoundNo: string;
  sapReceiveInfos: SapReceiveInfo[];
}
