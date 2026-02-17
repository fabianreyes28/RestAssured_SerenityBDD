@regression @booking @happyPath
Feature: Booking CRUD (Happy Path)

  Background:
    Given the Booking API is available

  @postBooking
  Scenario Outline: Create a booking (POST /booking)
    When the user creates a booking
      | firstname   | lastname   | totalprice   | depositpaid   | checkin   | checkout   | additionalneeds   | statusCode   |
      | <firstname> | <lastname> | <totalprice> | <depositpaid> | <checkin> | <checkout> | <additionalneeds> | <statusCode> |
    Then the response status code should be success
    And the response should contain a bookingid

    Examples:
      | firstname | lastname | totalprice | depositpaid | checkin | checkout | additionalneeds | statusCode |
   ##@externaldata@src/test/resources/datadriven/datapost.xlsx@Post
   |Jim   |Brown   |111   |true   |43101   |43466   |Breakfast   |200|
   |Ana   |White   |250   |false   |43586   |43595   |Dinner   |200|


  @getBooking
  Scenario: Retrieve a booking by id (GET /booking/{id})
    Given an existing booking has been created
    When the user retrieves the booking by id
    Then the response status code should be 200
    And the response booking should contain:
      | firstname | Jim   |
      | lastname  | Brown |

  @putBooking
  Scenario: Update a booking (PUT /booking/{id})
    Given an existing booking has been created
    And the user is authenticated
    When the user updates the booking with:
      | firstname       | James      |
      | lastname        | Brown      |
      | totalprice      | 222        |
      | depositpaid     | true       |
      | checkin         | 2018-02-01 |
      | checkout        | 2019-02-01 |
      | additionalneeds | Dinner     |
    Then the response status code should be 200
    And the response booking should contain:
      | firstname  | James |
      | lastname   | Brown |
      | totalprice | 222   |

  @deleteBooking
  Scenario: Delete a booking (DELETE /booking/{id})
    Given an existing booking has been created
    And the user is authenticated
    When the user deletes the booking by id
    Then the response status code should be 201
    And the booking should no longer be retrievable
