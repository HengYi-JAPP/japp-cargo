import {Lfa1} from './lfa1';
import {TransCorp} from './trans-corp';

export class SendInfo {
  id: string;
  lfa1: Lfa1;
  sendDate: Date;
  carNo: string;
  carDriver: string;
  transCorp: TransCorp;
  lfimg1: number;
  lfimg2: number;
  lfimg: number;
  note: string;
}
