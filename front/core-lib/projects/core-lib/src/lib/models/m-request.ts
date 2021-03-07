import {MApplication} from './referentiel/applications/m-application';


export class MRequest extends Map {

  application: MApplication ;
  elements: any[] ;

  constructor(application: MApplication, elements: any[]) {
    super();
    this.application = application;
    this.elements = elements;
  }
}
