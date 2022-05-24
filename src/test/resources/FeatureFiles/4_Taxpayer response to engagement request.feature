Feature: Engagement request response

  Background:
    Given User navigates to portal
    Then Click taxpayer
    Then Enters the username "C0102982" and password "Password@123" to login to portal

  Scenario: Respond to engagement request
    Then Navigate to My Tax > Engagement notifications
    Then Click respond
    Then Select approve
    Then Select taxtype
    Then Select allowable action " Certificate Request "
    Then Select allowable action " Submit a Return "
    Then Select allowable action " General Service Request "
    Then Select allowable action " Make a Payment "
    Then Select allowable action " Statement Request "
    Then Select allowable action " View a Return "
    Then Select allowable action " View Refund Status "
    Then Select allowable action " View Certificate Request Status "
    Then Click submit
    Then Verify portal alert success message "Record saved successfully."