Feature: Engagement request initiation

  Background:
    Given User navigates to portal
    Then Click tax agent
    Then Enters the username "C0022970A" and password "Password@123" to login to portal

  @engagement
  Scenario: Initiate engagement request
    Then Navigate to My clients
    Then Select taxpayer
    Then Click on manage client engagements
    Then Assign engagement " Certificate Request " to associates
    Then Submit engagement changes
    Then Verify alert success message "Record saved successfully."