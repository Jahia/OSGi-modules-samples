import {BasePage} from '@jahia/cypress';

export class DashboardPage extends BasePage {
    static visit(): DashboardPage {
        cy.visit('/jahia/dashboard');
        return new DashboardPage();
    }
}
