export class Lfa1 {
  lifnr: string;
  land1: string;
  name1: string;

  static assign(...sources: any[]): Lfa1 {
    const result = Object.assign(new Lfa1(), ...sources);
    return result;
  }
}
