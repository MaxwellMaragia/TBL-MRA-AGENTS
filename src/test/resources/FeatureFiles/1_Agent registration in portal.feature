Feature: Agent Registration in Portal

  Background:
    Given User navigates to portal
    Then Click taxpayer
    Then Enters the username "C0023586" and password "Password@123" to login to portal

  @agent_registration
  Scenario: Verify The Process of Agent registration in taxpayer portal
    Then Click register as agent button in portal home screen
    Then Verify portal success message "Your tax Agent registration request has been successfully submitted. Your reference number is"
    Then Obtain ARN for portal agent registration "Your tax Agent registration request has been successfully submitted. Your reference number is"

    




