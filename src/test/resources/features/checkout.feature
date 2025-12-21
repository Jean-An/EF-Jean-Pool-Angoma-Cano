@checkout
  Feature: Proceso de compra

    Scenario: Completar compra existosamente
      Given que el usuario tiene productos en el carrito
      When procede al checkout
      And ingresa los datos de envio
      And confirma la compra
      Then deberia ver el mensaje de compra existosa

    Scenario: Carrito vacio no permite checkout
      Given que el usuario tiene el carrito vacio
      When intenta proceder al checkout
      Then deberia ver mensaje de carrito vacio
