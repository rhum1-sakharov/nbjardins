import {TOKEN_APP} from "../../constants";

export const REQUEST_KEY_DEVIS='REQUEST_KEY_DEVIS';

export class MRequest extends Map {

  application: { nom, token } = {nom: 'Les jardins de Nicolas', token: TOKEN_APP};


  constructor() {
    super();
  }
}
