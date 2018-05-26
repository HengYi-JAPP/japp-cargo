import {OperatorPermission} from './operator-permission';

export class Operator {
  id: string;
  name: string;
  avatar: string;
  admin: boolean;
  permission: OperatorPermission;

  static assign(...sources: any[]): Operator {
    const result = Object.assign(new Operator(), ...sources);
    return result;
  }

  static toEntities(os: Operator[], entities?: { [id: string]: Operator }): { [id: string]: Operator } {
    return (os || []).reduce((acc, cur) => {
      acc[cur.id] = Operator.assign(cur);
      return acc;
    }, {...(entities || {})});
  }
}
