describe('Login flow', () => {
  it('allows a valid login and navigates to empleados', () => {
    cy.intercept('GET', '**/api/v1/empleados/contexto', {
      statusCode: 200,
      body: {
        username: 'master@example.com',
        rol: 'MASTER',
        authenticated: true
      }
    }).as('contexto');

    cy.intercept('GET', '**/api/v1/empleados?page=0&size=5', {
      statusCode: 200,
      body: {
        content: [
          {
            clave: 1,
            nombre: 'Admin User',
            email: 'master@example.com',
            direccion: 'Direccion 1',
            telefono: '555-1234',
            rol: 'MASTER',
            version: 0,
            departamento: {
              clave: 1,
              nombre: 'TI'
            }
          }
        ],
        page: 0,
        size: 5,
        totalElements: 1,
        totalPages: 1
      }
    }).as('empleados');

    cy.visit('/login');
    cy.get('input[formcontrolname="email"]').type('master@example.com');
    cy.get('input[formcontrolname="password"]').type('Abcdef1!');
    cy.contains('button', 'Entrar').click();

    cy.wait('@contexto');
    cy.wait('@empleados');
    cy.url().should('include', '/empleados');
    cy.contains('h2', 'Empleados').should('be.visible');
  });

  it('shows an error message when credentials are invalid', () => {
    cy.intercept('GET', '**/api/v1/empleados/contexto', {
      statusCode: 401,
      body: {
        status: 401,
        error: 'Unauthorized',
        message: 'credenciales invalidas o ausentes',
        path: '/api/v1/empleados/contexto'
      }
    }).as('contexto401');

    cy.visit('/login');
    cy.get('input[formcontrolname="email"]').type('user@example.com');
    cy.get('input[formcontrolname="password"]').type('wrongpass');
    cy.contains('button', 'Entrar').click();

    cy.wait('@contexto401');
    cy.contains('Credenciales invalidas o usuario sin acceso.').should('be.visible');
  });
});
