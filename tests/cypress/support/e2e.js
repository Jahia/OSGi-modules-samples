// ***********************************************************
// This example support/index.js is processed and
// loaded automatically before your test files.
//
// This is a great place to put global configuration and
// behavior that modifies Cypress.
//
// You can change the location of this file or turn off
// automatically serving support files with the
// 'supportFile' configuration option.
//
// You can read more here:
// https://on.cypress.io/configuration
// ***********************************************************

// Import commands.js using ES2015 syntax:

import "./commands";
import addContext from 'mochawesome/addContext';
// import "@cypress/code-coverage/support";
// eslint-disable-next-line @typescript-eslint/no-var-requires
require('cypress-terminal-report/src/installLogsCollector')({
    xhr: {
        printHeaderData: true,
        printRequestData: true,
    },
    enableExtendedCollector: true,
    collectTypes: ['cons:log','cons:info', 'cons:warn', 'cons:error', 'cy:log', 'cy:xhr', 'cy:request', 'cy:intercept', 'cy:command'],
});
require('@jahia/cypress/dist/support/registerSupport').registerSupport()

Cypress.on('uncaught:exception', (err, runnable) => {
    // Returning false here prevents Cypress from
    // failing the test
    return false;
});

Cypress.on('test:after:run', (test, runnable) => {
    let videoName = Cypress.spec.relative;
    videoName = videoName.replace('/.cy.*', '').replace('cypress/e2e/', '');
    const videoUrl = 'videos/' + videoName + '.mp4';
    addContext({test}, videoUrl);
    if (test.state === 'failed') {
        const screenshot = `screenshots/${Cypress.spec.relative.replace('cypress/e2e/', '')}/${runnable.parent.title} -- ${test.title} (failed).png`;
        addContext({test}, screenshot);
    }
});