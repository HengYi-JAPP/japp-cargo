import {T001} from './t001';

export class HeadInfo {
  id: string;
  name: string;
  allT001s: boolean;
  t001s: T001[];

  static assign(...sources: any[]): HeadInfo {
    const result = Object.assign(new HeadInfo(), ...sources);
    return result;
  }

  static toEntities(os: HeadInfo[], entities?: { [id: string]: HeadInfo }): { [id: string]: HeadInfo } {
    return (os || []).reduce((acc, cur) => {
      acc[cur.id] = HeadInfo.assign(cur);
      return acc;
    }, {...(entities || {})});
  }
}
