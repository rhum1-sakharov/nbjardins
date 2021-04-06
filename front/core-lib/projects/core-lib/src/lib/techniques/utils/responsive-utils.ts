export class ResponsiveUtils {

  public static getHeightStr(deltaPx: number) : string {
    const height = window.innerHeight - deltaPx;

    return `${height}px`;
  }
}
