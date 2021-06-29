export class FeatureError extends Error {

    constructor(message: string) {
        super(message);

        this.constructor = FeatureError;
        this.message = message;

    }

}