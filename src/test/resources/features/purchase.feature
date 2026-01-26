Feature: Purchase products at SauceDemo

  @happyPath @e2e
  Scenario Outline: Proceso de compra completo
    Given el usuario esta en la pagina de login de SauceDemo
    When inicia sesion con usuario "<username>" y password "<password>"
    And agrega el producto "<producto1>" al carrito
    And agrega el producto "<producto2>" al carrito
    And visualiza el carrito
    And completa el checkout con nombre "<firstName>" apellido "<lastName>" y codigo postal "<postalCode>"
    And finaliza la compra
    Then deberia ver el mensaje de confirmacion "Thank you for your order!"

    Examples:
      | username      | password     | producto1           | producto2             | firstName | lastName | postalCode |
      | standard_user | secret_sauce | Sauce Labs Backpack | Sauce Labs Bike Light | Kevin     | Donoso   | 170150     |
