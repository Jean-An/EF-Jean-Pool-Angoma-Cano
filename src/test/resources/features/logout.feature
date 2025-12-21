@logout

Feature: Cerrar sesion

  Scenario: Logout exitoso
    Given que el usuario esta logueado en la aplicacion
    When hace clic en el menu de usuario
    And hace clic en cerrar sesion
    Then deberia regresar a la pantalla de login
