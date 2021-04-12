import {MDevisOption, MODELE_OPTION} from 'rhum1-sakharov-core-lib';

export class DevisOptionUtils {

  public static getDevisOption(doList: MDevisOption[], mo: MODELE_OPTION) {

    if (doList) {
      for (const ao of doList) {
        if (ao.modeleOption === mo) {
          return ao.actif;
        }
      }
    }

    return null;
  }

  public static setDevisOption(doList: MDevisOption[], mo: MODELE_OPTION, $event: any) {
    if (doList) {
      for (const ao of  doList) {
        if (ao.modeleOption === mo) {
          ao.actif = $event;
        }
      }
    }
  }

}
