Feature: Engagement request initiation

  Background:
    Given User navigates to portal
    Then Click tax agent
    Then Enters the username "C0022970A" and password "Password@123" to login to portal

  Scenario: Initiate engagement request
    Then Navigate to My clients > New Engagement request
    Then Enter taxpayers tin as "C0102982" and engagement request details
    Then Click submit
    Then Verify portal alert success message "Request Submitted Successfully"

