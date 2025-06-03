import {getJahiaVersion} from '@jahia/cypress';
import {compare} from 'compare-versions';

describe('Login/logout url provider', () => {
    it('should use custom login url when going to /start', function () {
        cy.visit('/start');
        cy.contains('Custom Login page');
    });

    /*
    It('should redirect to custom login url', function () {
        cy.visit('/jahia/jcontent/systemsite/en/content-folders/contents');
        cy.contains('Custom Login page')
    })
    */

    it('should use custom login logout', function () {
        cy.login();
        cy.visit('/start');
        getJahiaVersion().then(jahiaVersion => {
            console.log(jahiaVersion);
            if (compare(jahiaVersion.release.replace('-SNAPSHOT', ''), '8.2.2', '<')) {
                console.log('Test using old style primaryNavMenuButton');
                cy.get('[role="primary-nav-control"]').click();
                cy.contains('Sign out').click();
            } else {
                cy.get('[data-testid="primaryNavMenuButton"]').click();
                cy.contains('Sign out').click();
            }
        });
    });
});
