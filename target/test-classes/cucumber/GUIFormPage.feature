Feature: Form Submission with Data

  Scenario: Fill form and select date for calendar using Excel data
    Given i opened the automation practise website
    When I fill the form and calendar with Excel data
    And I perform mouse actions and alerts for all users
    Then Browser will close
