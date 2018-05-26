import {PtaReceiveInfo} from './pta-receive-info';
import {SendInfo} from './send-info';
import {Ylips} from './ylips';

export class PtaSendInfo extends SendInfo {
  packType: string;
  transType: string;
  batchNo: string;
  packNo: number;
  ylips: Ylips;
  receiveInfo = new PtaReceiveInfo();

  static assign(...sources: any[]): PtaSendInfo {
    const result = Object.assign(new PtaSendInfo(), ...sources);
    return result;
  }

  static toEntities(os: PtaSendInfo[], entities?: { [id: string]: PtaSendInfo }): { [id: string]: PtaSendInfo } {
    return (os || []).reduce((acc, cur) => {
      acc[cur.id] = PtaSendInfo.assign(cur);
      return acc;
    }, {...(entities || {})});
  }
}
