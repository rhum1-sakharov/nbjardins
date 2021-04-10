import {differenceInDays, formatISO, parseISO} from 'date-fns';

export class DateUtils {

  /**
   * Parse the given string in ISO 8601 format and return an instance of Date.<br>
   *   ISO DATE =    2021-04-06
   * @param {string} value
   * @returns {Date}
   */
  public static getDateFromIso(value: string): Date {

    return parseISO(value);
  }

  public static getIsoFromDate(value: Date): string {
    return formatISO(value, { representation: 'date' });
  }

  public static getNbDays(laterDate: Date, earlierDate: Date) {

    return differenceInDays(laterDate, earlierDate);
  }
}
