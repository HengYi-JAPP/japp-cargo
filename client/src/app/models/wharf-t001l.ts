import {T001l} from './t001l';
import {T001lPk} from './t001l-pk';

export class WharfT001l extends T001lPk {
  t001l: T001l;

  get _id() {
    return `_class_:WharfT001l[${this.werks}-${ this.lgort}]`;
  }

  static assign(...sources: any[]): WharfT001l {
    const result = Object.assign(new WharfT001l(), ...sources);
    return result;
  }

  static toEntities(os: WharfT001l[], entities?: { [id: string]: WharfT001l }): { [id: string]: WharfT001l } {
    return (os || []).reduce((acc, cur) => {
      const o = WharfT001l.assign(cur);
      acc[o._id] = o;
      return acc;
    }, {...(entities || {})});
  }
}
