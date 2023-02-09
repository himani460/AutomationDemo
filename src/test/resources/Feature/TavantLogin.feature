Feature: Trane Loginpage

Background: 
Given user loads fixture "TestData_Trane.xlsx"

@Test_Id_TRANE-01 @TRANE_MACHINECLAIM
Scenario: Check that main elements on Google Homepage are displayed
Given user launches browser in chrome
And user enters url as "#ApplicationURL"
And user maximize the window
And user enters text "#UserName" in the textbox with attribute "id" and value "userName"
And user enters text "#Password" in the textbox with attribute "id" and value "password"
And user click element with value "logMeInButton"
And user click element with value "inventory_button"
And user click element with value "inside_retailed_inventory"
And user switch to frame with value "tab_0_iframe"
And user click element with select "th" and attribute "class" and value "dgrid-cell dgrid-cell-padding dgrid-column-inventoryTable_col_wntyEndDate field-wntyEndDate dgrid-sortable"
And user click element with select "th" and attribute "class" and value "dgrid-cell dgrid-cell-padding dgrid-column-inventoryTable_col_wntyEndDate field-wntyEndDate dgrid-sortable"
And user input the model number
And user apply the wait of 20
And user click element with select "div" and attribute "class" and value " ui-state-default dgrid-row dgrid-row-even dgrid-selected ui-state-active"
And user click element with value "_summaryTable_button_createClaimButton_label"
And user switch to default window
And user switch to frame with value "tab_1_iframe"
And user enters text "#DieselHour" in the textbox with attribute "id" and value "hoursInService"
And user enters text "#ElectricHour" in the textbox with attribute "id" and value "electricHours"
And user enters text "#WorkOrderNumber" in the textbox with attribute "id" and value "workOrderNumber"
And user click element with value "widget_dateOfFailure"
And user select the previous date
And user click element with value "widget_dateOfRepair"
And user select the previous date
And user click element with value "form_0"
And user scroll to the element with value "casualPartNumberLabel"
And user enters text "#CasualPartNumber" in the textbox with attribute "id" and value "causalPart"
And user click element with value "casualPartNumberLabel"
And user click element with value "launchFaultCodeTree"
And user select the radio button value with "dijit_form_RadioButton_0"
And user click element with value "addFaultCode"
And user enters text "#FaultFound" in the textbox with attribute "id" and value "faultFound"
And user click element with value "casualPartNumberLabel"
And user scroll to the element with value "widget_technicianId"
And user clear the textbox with value "technicianId"
And user enters text "#TechnicianName" in the textbox with attribute "id" and value "technicianId"
And user click element with attribute "class" and value "dijitReset dijitInputField dijitArrowButtonInner" and index 5
And user enters textarea "#ConditionFound" in the textbox with attribute "id" and value "condition_found"
And user enters textarea "#WorkPerformed" in the textbox with attribute "id" and value "work_performed"
And user click element with value "validateButton"
And user click element with value "submitButton"
And user verify the text with value "Machine"

@Test_Id_TRANE-02 @TRANE_Loginpage
Scenario: Check that main elements on Google Homepage are displayed
Given user launches browser in chrome
And user enters url as "#ApplicationURL"
And user maximize the window
And user enters text "#UserName" in the textbox with attribute "id" and value "userName"
And user enters text "#Password" in the textbox with attribute "id" and value "password"
And user click element with attribute "id" and value "logMeInButton"

