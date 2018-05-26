import {MegReceiveInfo} from './meg-receive-info';
import {SendInfo} from './send-info';
import {T001l} from './t001l';

export class MegSendInfo extends SendInfo {
  megType: number;
  wharf: T001l;
  receiveInfo = new MegReceiveInfo();

  static assign(...sources: any[]): MegSendInfo {
    const result = Object.assign(new MegSendInfo(), ...sources);
    return result;
  }

  static toEntities(os: MegSendInfo[], entities?: { [id: string]: MegSendInfo }): { [id: string]: MegSendInfo } {
    return (os || []).reduce((acc, cur) => {
      acc[cur.id] = MegSendInfo.assign(cur);
      return acc;
    }, {...(entities || {})});
  }
}
