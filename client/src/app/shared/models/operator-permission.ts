import {T001} from './t001';

export class OperatorPermission {
  id: string;
  defaultReceiveT001: T001;
  allT001s: boolean;
  t001s: T001[];

  static assign(...sources: any[]): OperatorPermission {
    const result = Object.assign(new OperatorPermission(), ...sources);
    return result;
  }
}
