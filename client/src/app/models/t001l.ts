import {T001lPk} from './t001l-pk';

export class T001l extends T001lPk {
  lgobe: string;

  get _id() {
    return `_class_:T001l[${this.werks}-${ this.lgort}]`;
  }

  static assign(...sources: any[]): T001l {
    const result = Object.assign(new T001l(), ...sources);
    return result;
  }

  static toEntities(os: T001l[], entities?: { [id: string]: T001l }): { [id: string]: T001l } {
    return (os || []).reduce((acc, cur) => {
      const t001l = T001l.assign(cur);
      acc[t001l._id] = t001l;
      return acc;
    }, {...(entities || {})});
  }
}
