import {HeadInfo} from './head-info';
import {Lfa1} from './lfa1';
import {SupplyInfo} from './supply-info';
import {TransCorp} from './trans-corp';

export class SendInfo {
  id: string;
  supplyInfo: SupplyInfo;
  headInfo: HeadInfo;
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
