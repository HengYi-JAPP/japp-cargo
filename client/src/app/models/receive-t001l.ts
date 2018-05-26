import {T001l} from './t001l';
import {T001lPk} from './t001l-pk';

export class ReceiveT001l extends T001lPk {
  t001l: T001l;

  get _id() {
    return `_class_:ReceiveT001l[${this.werks}-${ this.lgort}]`;
  }

  static assign(...sources: any[]): ReceiveT001l {
    const result = Object.assign(new ReceiveT001l(), ...sources);
    return result;
  }

  static toEntities(os: ReceiveT001l[], entities?: { [id: string]: ReceiveT001l }): { [id: string]: ReceiveT001l } {
    return (os || []).reduce((acc, cur) => {
      const o = ReceiveT001l.assign(cur);
      acc[o._id] = o;
      return acc;
    }, {...(entities || {})});
  }
}
