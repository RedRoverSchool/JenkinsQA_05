Feature: Multibranch pipeline

  Scenario: Create Multibranch pipeline
    Given Go to the home page
    When Click on New Item
    And Enter valid name
    And Select Multibranch Pipeline
    And Click OK Button
    And Click Save Button on Configure page without any changes
    Then Pipeline page is displayed
    Then Multibranch Pipeline with name exists
