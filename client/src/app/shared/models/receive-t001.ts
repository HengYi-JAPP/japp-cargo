import {Kna1} from './kna1';
import {Lfa1} from './lfa1';
import {T001} from './t001';

export class ReceiveT001 {
  bukrs: string;
  t001: T001;
  kna1: Kna1;
  lfa1: Lfa1;

  static assign(...sources: any[]): ReceiveT001 {
    const result = Object.assign(new ReceiveT001(), ...sources);
    return result;
  }

  static toEntities(os: ReceiveT001[], entities?: { [id: string]: ReceiveT001 }): { [id: string]: ReceiveT001 } {
    return (os || []).reduce((acc, cur) => {
      acc[cur.bukrs] = ReceiveT001.assign(cur);
      return acc;
    }, {...(entities || {})});
  }
}
