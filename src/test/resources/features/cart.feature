@cart
Feature: Carrito de compras
  Como usuario de la aplicacion
  Quiero agregar productos al carrito
  Para poder realizar mis compras

  Background:
    Given el usuario inicia sesion con credenciales validas

  @smoke @agregar-producto
  Scenario: Agregar producto al carrito desde pantalla de detalle
    Given el usuario esta en la pantalla principal
    When el usuario selecciona el producto "Laptop HP Pavilion"
    And el usuario hace clic en agregar al carrito
    Then el usuario deberia ver el mensaje de confirmacion

  @smoke @verificar-carrito
  Scenario: Verificar que el producto aparece en el carrito
    Given el usuario esta en la pantalla principal
    When el usuario selecciona el producto "Mouse Gamer Logitech"
    And el usuario hace clic en agregar al carrito
    And el usuario regresa a la pantalla principal
    And el usuario navega al carrito
    Then el usuario deberia ver el producto "Mouse Gamer Logitech" en el carrito

  @carrito-vacio
  Scenario: Verificar mensaje de carrito vacio
    Given el usuario esta en la pantalla principal
    When el usuario navega al carrito
    Then el usuario deberia ver el mensaje de carrito vacio