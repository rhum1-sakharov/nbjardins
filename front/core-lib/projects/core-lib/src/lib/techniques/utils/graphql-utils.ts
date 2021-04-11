import {GRAPHQL_TYPE} from '../../enums/e-graphql-type';


export class GraphqlUtils {


  public static setInput(obj: any, property: string, type: GRAPHQL_TYPE, path?: string): string {

    let input = '';
    const value = obj[property];

    switch (type) {
      case GRAPHQL_TYPE.DATE:
        input = `${property} : ${this.handleValue(value, GRAPHQL_TYPE.DATE)}`;
        break;
      case GRAPHQL_TYPE.NUMBER:
        input = `${property} : ${this.handleValue(value, GRAPHQL_TYPE.NUMBER)}`;
        break;
      case GRAPHQL_TYPE.STRING:
        input = `${property} : ${this.handleValue(value, GRAPHQL_TYPE.STRING)}`;
        break;
      case GRAPHQL_TYPE.ENUM:
        input = `${property} :${this.handleValue(value, GRAPHQL_TYPE.ENUM)}`;
        break;
      default:
        input = `${property} : ${this.handleValue(value, GRAPHQL_TYPE.STRING)}`;
        break;
    }


    return input;
  }

  private static handleValue(value: any, type: GRAPHQL_TYPE): string | null | number {

    switch (type) {
      case GRAPHQL_TYPE.DATE:
        return value ? `"${value}"` : null;
        break;
      case GRAPHQL_TYPE.NUMBER:
        return value ? `${value}` : 0;
        break;
      case GRAPHQL_TYPE.STRING:
        return value ? `"${value}"` : null;
        break;
      case GRAPHQL_TYPE.ENUM:
        return value ? `${value}` : null;
        break;
      default:
        return value ? `"${value}"` : null;
        break;
    }
  }


}
