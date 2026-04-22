describe('Empleados CRUD flow', () => {
  it('creates, updates, and deletes an empleado as MASTER', () => {
    const departamento = { clave: 1, nombre: 'TI' };

    /**
     * Keep an in-memory list to emulate backend state changes across requests.
     */
    let empleados = [
      {
        clave: 1,
        nombre: 'Admin User',
        email: 'master@example.com',
        direccion: 'Direccion 1',
        telefono: '555-1234',
        rol: 'MASTER',
        version: 0,
        departamento
      }
    ];

    cy.intercept('GET', '**/api/v1/empleados/contexto', {
      statusCode: 200,
      body: {
        username: 'master@example.com',
        rol: 'MASTER',
        authenticated: true
      }
    }).as('contexto');

    cy.intercept('GET', '**/api/v1/departamentos?page=0&size=5', {
      statusCode: 200,
      body: {
        content: [departamento],
        page: 0,
        size: 5,
        totalElements: 1,
        totalPages: 1
      }
    }).as('departamentos');

    cy.intercept('GET', '**/api/v1/empleados?page=0&size=5', (req) => {
      req.reply({
        statusCode: 200,
        body: {
          content: empleados,
          page: 0,
          size: 5,
          totalElements: empleados.length,
          totalPages: 1
        }
      });
    }).as('listEmpleados');

    cy.intercept('POST', '**/api/v1/empleados', (req) => {
      const nuevo = {
        clave: 2,
        nombre: req.body.nombre,
        email: req.body.email,
        direccion: req.body.direccion,
        telefono: req.body.telefono,
        rol: req.body.rol,
        version: 0,
        departamento
      };
      empleados = [...empleados, nuevo];
      req.reply({ statusCode: 201, body: nuevo });
    }).as('createEmpleado');

    cy.intercept('GET', '**/api/v1/empleados/2', (req) => {
      const empleado = empleados.find((e) => e.clave === 2);
      req.reply({ statusCode: 200, body: empleado });
    }).as('getEmpleado2');

    cy.intercept('PUT', '**/api/v1/empleados/2', (req) => {
      const actualizado = {
        clave: 2,
        nombre: req.body.nombre,
        email: req.body.email,
        direccion: req.body.direccion,
        telefono: req.body.telefono,
        rol: req.body.rol,
        version: 1,
        departamento
      };
      empleados = empleados.map((e) => (e.clave === 2 ? actualizado : e));
      req.reply({ statusCode: 200, body: actualizado });
    }).as('updateEmpleado');

    cy.intercept('DELETE', '**/api/v1/empleados/2', (req) => {
      empleados = empleados.filter((e) => e.clave !== 2);
      req.reply({ statusCode: 204 });
    }).as('deleteEmpleado');

    cy.visit('/login');
    cy.get('input[formcontrolname="email"]').type('master@example.com');
    cy.get('input[formcontrolname="password"]').type('Abcdef1!');
    cy.contains('button', 'Entrar').click();

    cy.wait('@contexto');
    cy.wait('@listEmpleados');
    cy.url().should('include', '/empleados');

    // Create
    cy.contains('a', 'Nuevo empleado').click();
    cy.wait('@departamentos');
    cy.get('input[formcontrolname="nombre"]').type('QA Cypress');
    cy.get('input[formcontrolname="email"]').type('qa.cypress@example.com');
    cy.get('input[formcontrolname="direccion"]').type('Direccion QA');
    cy.get('input[formcontrolname="telefono"]').type('555-0000');
    cy.get('input[formcontrolname="contrasena"]').type('Abcdef1!');
    cy.get('select[formcontrolname="rol"]').select('STANDARD');
    cy.get('select[formcontrolname="departamentoClave"]').select('1');
    cy.contains('button', 'Guardar cambios').click();

    cy.wait('@createEmpleado');
    cy.wait('@listEmpleados');
    cy.contains('td', 'QA Cypress').should('be.visible');

    // Update
    cy.contains('tr', 'QA Cypress').within(() => {
      cy.contains('a', 'Editar').click();
    });
    cy.wait('@getEmpleado2');
    cy.url().should('include', '/empleados/2/editar');
    cy.get('input[formcontrolname="nombre"]').clear().type('QA Cypress Editado');
    cy.get('input[formcontrolname="email"]').clear().type('qa.editado@example.com');
    cy.get('input[formcontrolname="direccion"]').clear().type('Direccion Editada');
    cy.get('input[formcontrolname="telefono"]').clear().type('555-1111');
    cy.get('input[formcontrolname="contrasena"]').type('Xyzabc1!');
    cy.contains('button', 'Guardar cambios').click();

    cy.wait('@updateEmpleado');
    cy.wait('@listEmpleados');
    cy.contains('td', 'QA Cypress Editado').should('be.visible');

    // Delete
    cy.on('window:confirm', () => true);
    cy.contains('tr', 'QA Cypress Editado').within(() => {
      cy.contains('button', 'Eliminar').click();
    });

    cy.wait('@deleteEmpleado');
    cy.wait('@listEmpleados');
    cy.contains('td', 'QA Cypress Editado').should('not.exist');
  });
});
