const { defineConfig } = require('cypress');

const configuredBaseUrl = process.env.CYPRESS_BASE_URL || 'http://localhost:4200';

module.exports = defineConfig({
  e2e: {
    baseUrl: configuredBaseUrl,
    supportFile: false
  },
  video: false,
  screenshotOnRunFailure: false
});
