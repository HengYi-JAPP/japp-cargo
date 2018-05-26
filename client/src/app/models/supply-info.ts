import {T001} from './t001';

export class SupplyInfo {
  id: string;
  allT001s: boolean;
  name: string;
  t001s: T001[];

  static assign(...sources: any[]): SupplyInfo {
    const result = Object.assign(new SupplyInfo(), ...sources);
    return result;
  }

  static toEntities(os: SupplyInfo[], entities?: { [id: string]: SupplyInfo }): { [id: string]: SupplyInfo } {
    return (os || []).reduce((acc, cur) => {
      acc[cur.id] = SupplyInfo.assign(cur);
      return acc;
    }, {...(entities || {})});
  }
}
