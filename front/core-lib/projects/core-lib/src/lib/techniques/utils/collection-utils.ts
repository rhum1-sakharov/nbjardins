export class CollectionUtils {


  constructor() {
  }

  public static isNoe(collection: any[]): boolean {
    if (!collection || collection.length === 0) {
      return true;
    }

    return false;
  }

  public static preselectSingleElement(elements: any[], selectedElement: any): any {

    if (elements && selectedElement) {
      for (const elt of elements) {
        if (elt.id === selectedElement.id) {
          return elt;
        }
      }
    }
    return null;
  }

  public static addElement(element: any, elements: any[]): any[] {

    let arr = elements;
    if (this.isNoe(arr)) {
      arr = [];
    }
    arr.push(element);

    return arr;

  }

  public static updateElement(element: any, key:string, elements: any[]): any[] {

    let arr = [];
    for (const item of elements) {
      if (item[key] === element[key]) {
        arr.push(element);
      } else {
        arr.push(item);
      }
    }
    return arr;
  }

}
