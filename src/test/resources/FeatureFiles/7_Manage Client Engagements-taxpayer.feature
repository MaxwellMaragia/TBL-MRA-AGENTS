Feature: Manage Client Engagement by taxpayer

  Background:
    Given User navigates to portal
    Then Click taxpayer
    Then Enters the username "C0102982" and password "Password@123" to login to portal

  @engagement
  Scenario: Taxpayer manage agent engagement
    Then Navigate to My Tax > My agent
    Then Select agent from dropdown
    Then Select engagement details taxtype
    Then Select allowable action " Statement Request "
    Then Submit engagement changes
    Then Verify alert success message "Record saved successfully."