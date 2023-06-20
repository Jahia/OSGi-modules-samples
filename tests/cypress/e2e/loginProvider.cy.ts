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
        cy.get('[role="primary-nav-control"]').click();
        cy.contains('Sign out').click();
    });
});
