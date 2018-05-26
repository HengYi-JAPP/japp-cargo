import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'transType'
})
export class TransTypePipe implements PipeTransform {
  transform(packType: string): string {
    switch (packType) {
      case '1': {
        return '槽车';
      }
      case '2': {
        return '平板车';
      }
      case '3': {
        return '集装箱';
      }
      case '-1': {
        return '船运';
      }
    }
    return '非正常';
  }

}
