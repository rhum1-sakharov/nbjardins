export class ObjectUtils {


  constructor() {
  }

  public static isNoe(object: any): boolean {
    if( object === null || object === undefined || object === '') {
      return true;
    }

    return false;
  }



}
