Feature: Engagement request initiation

  Background:
    Given User navigates to portal
    Then Click tax agent
    Then Enters the username "C0022970A" and password "Password@123" to login to portal

  @tax-return
  Scenario: Initiate engagement request
    Then Navigate to My clients
    Then Select taxpayer
    Then Click on manage client account
    Then Click on Submit a return
    Then Select taxtype as "PAYE"
    Then Select period as "2022/02"
    Then Click continue
    Then Upload PAYE template
    Then Verify alert success message "File uploaded successfully."
    Then Confirm information given for paye tax is true
    Then Submit PAYE Tax returns
    Then Verify success message displayed as "Your PAYE Returns request has been submitted successfully. Your reference number is: EPMR"
