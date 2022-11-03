context("test1", () => {
    beforeEach(() => {
        cy.visit(Cypress.config().baseURI + "/index.xhtml");
    });

    it("has a clock canvas", () => {
        cy.get("canvas").should("be.visible");
    });

    it("has an input with type button with text 'To main'", () => {
        cy.get("input[type='button']").should("have.value", "To main");
    });

    it("the button with text 'To main' navigates to the /main.xhtml page", () => {
        cy.get("input[type='button']").click();
        cy.url().should("include", Cypress.config().baseURI + "/main.xhtml");
    })
})

