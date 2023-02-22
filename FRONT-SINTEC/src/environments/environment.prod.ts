export const environment = {
  production: true,
  apiUrl: 'https://api-sintec.grupofloripark.com.br/api-sintec',
  tokenWhitelistedDomains: [new RegExp('https://api-sintec.grupofloripark.com.br')],
  tokenBlacklistedRoutes: [new RegExp('\/oauth\/token')]


  // apiUrl: 'http://localhost:8080',
  // tokenWhitelistedDomains: [new RegExp('localhost:8080')],
  // tokenBlacklistedRoutes: [new RegExp('\/oauth\/token')]
};
