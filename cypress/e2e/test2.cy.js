// noinspection JSUnresolvedFunction

context("test2", () => {
    beforeEach(() => {
        cy.visit(Cypress.config().baseURI + "/main.xhtml");
        cy.get("button").contains("Reset").click();

    });

    it("has a canvas with id 'graph'", () => {
        cy.get("canvas#graph").should("be.visible");
    });

    it("after clicking on the canvas a new row appears in the table with id 'table'", () => {
        cy.intercept('POST', Cypress.config().baseURI + '/main.xhtml').as('post');

        cy.get("canvas#graph").click();

        cy.wait('@post').its('response.statusCode').should('eq', 200)

        cy.get("#table").find("table").find("#table_data").find("tr").should("have.length", 1);
    });

    it("after clicking on the canvas a few times a new row appears each time in the table with id 'table'", () => {
        cy.intercept('POST', Cypress.config().baseURI + '/main.xhtml').as('post');
        for (let i = 0; i < 4; i++) {
            cy.get("canvas#graph").click();
            cy.wait('@post').its('response.statusCode').should('eq', 200)
            cy.wait('@post').its('response.statusCode').should('eq', 200)
        }

        cy.get("#table").find("table").find("#table_data").find("tr").should("have.length", 4);
    });

    it("after adding a few rows to the table with id 'table' and clicking on the button with text 'Reset' the table is empty", () => {
        cy.intercept('POST', Cypress.config().baseURI + '/main.xhtml').as('post');
        for (let i = 0; i < 3 * 2; i++) {
            cy.get("canvas#graph").click();
            cy.wait('@post').its('response.statusCode').should('eq', 200)
            cy.wait('@post').its('response.statusCode').should('eq', 200)
        }
        cy.get("button").contains("Reset").click();

        cy.get("#table").find("table").find("#table_data").find("tr").should("have.length", 1);

    })

    it("after pressing the submit button with text 'Submit' a new row appears in the table with id 'table' and the row has" +
        "X = 0, Y = Y, R = 1", () => {
        cy.intercept('POST', Cypress.config().baseURI + '/main.xhtml').as('post');
        cy.get("button").contains("Submit").click();
        cy.wait('@post').its('response.statusCode').should('eq', 200)
        cy.get("#table").find("table").find("tbody").find("tr").should("have.length", 1);
    })

    it("after typing a number in the input with id 'x' and the input with id 'y' and choosing r and pressing the submit button with text 'Submit' a new row appears in the table with id 'table' and the row has" +
        "the chosen values", () => {
        cy.get("input#form\\:x_input").clear().type("1")
        cy.get("input#form\\:y_input").clear().type("2")
        cy.get("table#form\\:r").find("tbody").find("tr").find("td").find("input").check("2", {force: true})
        cy.intercept('POST', Cypress.config().baseURI + '/main.xhtml').as('post');
        cy.get("button").contains("Submit").click();
        cy.wait('@post').its('response.statusCode').should('eq', 200)
        cy.get("#table").find("table").find("#table_data").find("tr").should("have.length", 1);
        cy.get("#table").find("table").find("#table_data").find("tr").find("td").eq(1).should("have.text", "1.0");
        cy.get("#table").find("table").find("#table_data").find("tr").find("td").eq(2).should("have.text", "2.0");
        cy.get("#table").find("table").find("#table_data").find("tr").find("td").eq(3).should("have.text", "2");
    })
})

