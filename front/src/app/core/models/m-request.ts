import {Model} from "./model";
import {TOKEN_APP} from "../../constants";

export class MRequest<T extends Model> {
  list: T[] = [];
  one: T;
  application: { nom, token } = {nom: 'Les jardins de Nicolas', token: TOKEN_APP};
  additionalProperties: Map<string, any> = new Map();

}
