import {Pipe, PipeTransform} from '@angular/core';

export const delPreZeroFn = (s: string): string => {
  if (!s || s.charAt(0) !== '0') {
    return s;
  }
  return delPreZeroFn(s.substr(1));
};

@Pipe({
  name: 'delPreZero'
})
export class DelPreZeroPipe implements PipeTransform {
  transform(s: string): string {
    return delPreZeroFn(s);
  }
}
