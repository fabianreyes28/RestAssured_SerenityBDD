@regression @getBookingById
Feature: Retrieve booking by id

  Background:
    Given the user needs to perform a search by id

  @RB-REG-007-EXCEL #@RB-NEG-003 @RB-NEG-004 @RB-NEG-005
  Scenario Outline: Retrieve booking using different id values
    When User request users by "<bookingId>"
    Then the response status code should be <statusCode>

    Examples:
      | bookingId | statusCode |
 ##@externaldata@.\src\test\resources\datadriven\dataGET.xlsx@Get_user_id
   |12   |200|
   |0   |404|
   |-2   |404|
   |abcd   |404|


