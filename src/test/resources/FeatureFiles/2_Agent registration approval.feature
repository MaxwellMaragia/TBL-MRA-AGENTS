Feature: Approval in CRM

  @agent_registration
  Scenario: Approve agent registration
    Given Open CRM URL Module as "crmadmin"
    And Close Popup Window
    And Click on Case management dropdown
    And click on Queues
    Then switch to frame0
    And enters reference number in search results
    Then Click on reference number
    Then Agent registration status should be "In Progress"
    Then switch to frame1
    And wait for plan to load "First Name"
    Then Approve application
    And Click on Save button
    Then Agent registration status should be "Approved"