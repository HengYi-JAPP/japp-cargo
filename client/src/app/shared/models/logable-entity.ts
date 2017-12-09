import {Operator} from './operator';

export abstract class LogableEntity {
  creator: Operator;
  createDateTime: Date;
  modifier: Operator;
  modifyDateTime: Date;
}
