Feature: Folder Test

  Scenario: Create new Folder
    Given Go to home page
    When Click on link New Item
    And Enter an item name
    And Select Folder from list and click Ok
    And click Save
    Then Result: folder was created

  Scenario: Delete Folder
    Given Go to home page
    When Folder was created
    And Return to home page
    And Click on the existing folder
    And Click on menu Delete Folder
    And Click on button Yes
    Then Result: Deleted Folder in not exist
    Then Result: All folder should be deleted
