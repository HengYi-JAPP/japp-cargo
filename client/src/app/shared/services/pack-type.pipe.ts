import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'packType'
})
export class PackTypePipe implements PipeTransform {
  transform(packType: string): string {
    switch (packType) {
      case '1': {
        return '槽车';
      }
      case '2': {
        return '1.1吨新袋';
      }
      case '5': {
        return '1.2吨新袋';
      }
      case '3': {
        return '1.1吨旧袋';
      }
    }
    return '非正常';
  }

}
