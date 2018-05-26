import {T001} from './t001';

export class TransCorp {
  id: string;
  allT001s: boolean;
  name: string;
  t001s: T001[];

  static assign(...sources: any[]): TransCorp {
    const result = Object.assign(new TransCorp(), ...sources);
    return result;
  }

  static toEntities(os: TransCorp[], entities?: { [id: string]: TransCorp }): { [id: string]: TransCorp } {
    return (os || []).reduce((acc, cur) => {
      acc[cur.id] = TransCorp.assign(cur);
      return acc;
    }, {...(entities || {})});
  }
}
