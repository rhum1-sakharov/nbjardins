export class TechnicalError extends Error {

    constructor(message: string) {
        super(message);

        this.constructor = TechnicalError;
        this.message = message;

    }

}