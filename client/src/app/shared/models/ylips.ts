import {Lips} from './lips';

export class Ylips {
  id: string;
  packType: string;
  transType: string;
  carNo: string;
  batchNo: string;
  lfimg1: number;
  lfimg2: number;
  lfimg: number;
  note: number;
  erdat: Date;
  lips: Lips;

  static assign(...sources: any[]): Ylips {
    const result = Object.assign(new Ylips(), ...sources);
    return result;
  }

  static toEntities(os: Ylips[], entities?: { [id: string]: Ylips }): { [id: string]: Ylips } {
    return (os || []).reduce((acc, cur) => {
      acc[cur.id] = Ylips.assign(cur);
      return acc;
    }, {...(entities || {})});
  }
}
