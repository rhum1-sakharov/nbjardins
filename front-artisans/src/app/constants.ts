export const OAUTH_GOOGLE_ID = '342992225722-f12jtc44h4gvsdn7kepj4nuudn91lfhj.apps.googleusercontent.com';
export const OAUTH_GOOGLE_SECRET = '_1R5cJ2dpBF8cxt4wIC0VMUZ';
export const OAUTH_GOOGLE_REDIRECT_URI = `${encodeURI('http://localhost:4200/api/authorization/connect')}`;
export const OAUTH_GOOGLE_CONNECTION_URL = `https://accounts.google.com/o/oauth2/v2/auth?scope=email&access_type=online&state=state_parameter_passthrough_value&redirect_uri=${OAUTH_GOOGLE_REDIRECT_URI}&response_type=code&client_id=${OAUTH_GOOGLE_ID}`


