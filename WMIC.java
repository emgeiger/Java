import javax.swing.*;
import java.awt.event.*;

enum TriggerSchedule
{
    Hardware_Inventory("{00000000-0000-0000-0000-000000000001}"),
    Software_Inventory("{00000000-0000-0000-0000-000000000002}"),
    Discovery_Inventory("{00000000-0000-0000-0000-000000000003}"),
    File_Collection("{00000000-0000-0000-0000-000000000010}"),
    IDMIF_Collection("{00000000-0000-0000-0000-000000000011}"),
    Client_Machine_Authentication("{00000000-0000-0000-0000-000000000012}"),
    Request_Machine_Assignments("{00000000-0000-0000-0000-000000000021}"),
    Evaluate_Machine_Policies("{00000000-0000-0000-0000-000000000022}"),
    Refresh_Default_MP_Task("{00000000-0000-0000-0000-000000000023}"),
    LS_Refresh_Locations_Task("{00000000-0000-0000-0000-000000000024}"), // Location Service
    LS_Timeout_Refresh_Task("{00000000-0000-0000-0000-000000000025}"), // Location Service
    Policy_Agent_Request_Assignment("{00000000-0000-0000-0000-000000000026}"),
    Policy_Agent_Evaluate_Assignment("{00000000-0000-0000-0000-000000000027}"),
    Software_Metering_Generating_Usage_Report("{00000000-0000-0000-0000-000000000031}"),
    Source_Update_Message("{00000000-0000-0000-0000-000000000032}"),
    Clearing_proxy_settings_cache("{00000000-0000-0000-0000-000000000037}"),
    Machine_Policy_Agent_Cleanup("{00000000-0000-0000-0000-000000000040}"),
    User_Policy_Agent_Cleanup("{00000000-0000-0000-0000-000000000041}"),
    Policy_Agent_Validate_Machine_Policy_Assignment("{00000000-0000-0000-0000-000000000042}"),
    Policy_Agent_Validate_User_Policy_Assignment("{00000000-0000-0000-0000-000000000043}"),
    Retrying_Refreshing_certificates_in_AD_on_MP("{00000000-0000-0000-0000-000000000051}"),
    Peer_DP_Status_reporting("{00000000-0000-0000-0000-000000000061}"),
    Peer_DP_Pending_package_check_schedule("{00000000-0000-0000-0000-000000000062}"),
    SUM_Updates_install_schedule("{00000000-0000-0000-0000-000000000063}"),
    NAP_action("{00000000-0000-0000-0000-000000000071}"),
    Hardware_Inventory_Collection_Cycle("{00000000-0000-0000-0000-000000000101}"),
    Software_Inventory_Collection_Cycle("{00000000-0000-0000-0000-000000000102}"),
    Discovery_Data_Collection_Cycle("{00000000-0000-0000-0000-000000000103}"),
    File_Collection_Cycle("{00000000-0000-0000-0000-000000000104}"),
    IDMIF_Collection_Cycle("{00000000-0000-0000-0000-000000000105}"),
    Software_Metering_Usage_Report_Cycle("{00000000-0000-0000-0000-000000000106}"),
    Windows_Installer_Source_List_Update_Cycle("{00000000-0000-0000-0000-000000000107}"),
    Software_Updates_Assignments_Evaluation_Cycle("{00000000-0000-0000-0000-000000000108}"),
    Branch_Distribution_Point_Maintenance_Task("{00000000-0000-0000-0000-000000000109}"),
    DCM_policy("{00000000-0000-0000-0000-000000000110}"),
    Send_Unsent_State_Message("{00000000-0000-0000-0000-000000000111}"),
    State_System_policy_cache_cleanout("{00000000-0000-0000-0000-000000000112}"),
    Scan_by_Update_Source("{00000000-0000-0000-0000-000000000113}"),
    Update_Store_Policy("{00000000-0000-0000-0000-000000000114}"),
    State_system_policy_bulk_send_high("{00000000-0000-0000-0000-000000000115}"),
    State_system_policy_bulk_send_low("{00000000-0000-0000-0000-000000000116}"),
    AMT_Status_Check_Policy("{00000000-0000-0000-0000-000000000120}"),
    Application_manager_policy_action("{00000000-0000-0000-0000-000000000121}"),
    Application_manager_user_policy_action("{00000000-0000-0000-0000-000000000122}"),
    Application_manager_global_evaluation_action("{00000000-0000-0000-0000-000000000123}"),
    Power_management_start_summarizer("{00000000-0000-0000-0000-000000000131}"),
    Endpoint_deployment_reevaluate("{00000000-0000-0000-0000-000000000221}"),
    Endpoint_AM_policy_reevaluate("{00000000-0000-0000-0000-000000000222}"),
    External_event_detection("{00000000-0000-0000-0000-000000000223}");
}

public class WMIC extends JFrame implements ActionListener
{
    public WMIC()
    {
        createContents();
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public void createContents()
    {
        /*
// {00000000-0000-0000-0000-000000000001} Hardware Inventory
// {00000000-0000-0000-0000-000000000002} Software Inventory
// {00000000-0000-0000-0000-000000000003} Discovery Inventory
// {00000000-0000-0000-0000-000000000010} File Collection
// {00000000-0000-0000-0000-000000000011} IDMIF Collection
// {00000000-0000-0000-0000-000000000012} Client Machine Authentication
// {00000000-0000-0000-0000-000000000021} Request Machine Assignments
// {00000000-0000-0000-0000-000000000022} Evaluate Machine Policies
// {00000000-0000-0000-0000-000000000023} Refresh Default MP Task
// {00000000-0000-0000-0000-000000000024} LS (Location Service) Refresh Locations Task
// {00000000-0000-0000-0000-000000000025} LS (Location Service) Timeout Refresh Task
// {00000000-0000-0000-0000-000000000026} Policy Agent Request Assignment (User)
// {00000000-0000-0000-0000-000000000027} Policy Agent Evaluate Assignment (User)
// {00000000-0000-0000-0000-000000000031} Software Metering Generating Usage Report
// {00000000-0000-0000-0000-000000000032} Source Update Message
// {00000000-0000-0000-0000-000000000037} Clearing proxy settings cache
// {00000000-0000-0000-0000-000000000040} Machine Policy Agent Cleanup
// {00000000-0000-0000-0000-000000000041} User Policy Agent Cleanup
// {00000000-0000-0000-0000-000000000042} Policy Agent Validate Machine Policy / Assignment
// {00000000-0000-0000-0000-000000000043} Policy Agent Validate User Policy / Assignment
// {00000000-0000-0000-0000-000000000051} Retrying/Refreshing certificates in AD on MP
// {00000000-0000-0000-0000-000000000061} Peer DP Status reporting
// {00000000-0000-0000-0000-000000000062} Peer DP Pending package check schedule
// {00000000-0000-0000-0000-000000000063} SUM Updates install schedule
// {00000000-0000-0000-0000-000000000071} NAP action
// {00000000-0000-0000-0000-000000000101} Hardware Inventory Collection Cycle
// {00000000-0000-0000-0000-000000000102} Software Inventory Collection Cycle
// {00000000-0000-0000-0000-000000000103} Discovery Data Collection Cycle
// {00000000-0000-0000-0000-000000000104} File Collection Cycle
// {00000000-0000-0000-0000-000000000105} IDMIF Collection Cycle
// {00000000-0000-0000-0000-000000000106} Software Metering Usage Report Cycle
// {00000000-0000-0000-0000-000000000107} Windows Installer Source List Update Cycle
// {00000000-0000-0000-0000-000000000108} Software Updates Assignments Evaluation Cycle
// {00000000-0000-0000-0000-000000000109} Branch Distribution Point Maintenance Task
// {00000000-0000-0000-0000-000000000110} DCM policy
// {00000000-0000-0000-0000-000000000111} Send Unsent State Message
// {00000000-0000-0000-0000-000000000112} State System policy cache cleanout
// {00000000-0000-0000-0000-000000000113} Scan by Update Source
// {00000000-0000-0000-0000-000000000114} Update Store Policy
// {00000000-0000-0000-0000-000000000115} State system policy bulk send high
// {00000000-0000-0000-0000-000000000116} State system policy bulk send low
// {00000000-0000-0000-0000-000000000120} AMT Status Check Policy
// {00000000-0000-0000-0000-000000000121} Application manager policy action
// {00000000-0000-0000-0000-000000000122} Application manager user policy action
// {00000000-0000-0000-0000-000000000123} Application manager global evaluation action
//  {00000000-0000-0000-0000-000000000131} Power management start summarizer
// {00000000-0000-0000-0000-000000000221} Endpoint deployment reevaluate
// {00000000-0000-0000-0000-000000000222} Endpoint AM policy reevaluate
// {00000000-0000-0000-0000-000000000223} External event detection
*/
        /*
         * Complete GUID List: Hardware Inventory
         *
         * {00000000-0000-0000-0000-000000000001}
         *
         * Software Inventory
         *
         * {00000000-0000-0000-0000-000000000002}
         *
         * Data Discovery Record
         *
         * {00000000-0000-0000-0000-000000000003}
         *
         * File Collection
         *
         * {00000000-0000-0000-0000-000000000010}
         *
         * IDMIF Collection
         *
         * {00000000-0000-0000-0000-000000000011}
         *
         * Client Machine Authentication
         *
         * {00000000-0000-0000-0000-000000000012}
         *
         * Machine Policy Assignments Request
         *
         * {00000000-0000-0000-0000-000000000021}
         *
         * Machine Policy Evaluation
         *
         * {00000000-0000-0000-0000-000000000022}
         *
         * Refresh Default MP Task
         *
         * {00000000-0000-0000-0000-000000000023}
         *
         * LS (Location Service) Refresh Locations Task
         *
         * {00000000-0000-0000-0000-000000000024}
         *
         * LS (Location Service) Timeout Refresh Task
         *
         * {00000000-0000-0000-0000-000000000025}
         *
         * Policy Agent Request Assignment (User)
         *
         * {00000000-0000-0000-0000-000000000026}
         *
         * Policy Agent Evaluate Assignment (User)
         *
         * {00000000-0000-0000-0000-000000000027}
         *
         * Software Metering Generating Usage Report
         *
         * {00000000-0000-0000-0000-000000000031}
         *
         * Source Update Message
         *
         * {00000000-0000-0000-0000-000000000032}
         *
         * Clearing proxy settings cache
         *
         * {00000000-0000-0000-0000-000000000037}
         *
         * Machine Policy Agent Cleanup
         *
         * {00000000-0000-0000-0000-000000000040}
         *
         * User Policy Agent Cleanup
         *
         * {00000000-0000-0000-0000-000000000041}
         *
         * Policy Agent Validate Machine Policy / Assignment
         *
         * {00000000-0000-0000-0000-000000000042}
         *
         * Policy Agent Validate User Policy / Assignment
         *
         * {00000000-0000-0000-0000-000000000043}
         *
         * Retrying/Refreshing certificates in AD on MP
         *
         * {00000000-0000-0000-0000-000000000051}
         *
         * Peer DP Status reporting
         *
         * {00000000-0000-0000-0000-000000000061}
         *
         * Peer DP Pending package check schedule
         *
         * {00000000-0000-0000-0000-000000000062}
         *
         * SUM Updates install schedule
         *
         * {00000000-0000-0000-0000-000000000063}
         *
         * Hardware Inventory Collection Cycle
         *
         * {00000000-0000-0000-0000-000000000101}
         *
         * Software Inventory Collection Cycle
         *
         * {00000000-0000-0000-0000-000000000102}
         *
         * Discovery Data Collection Cycle
         *
         * {00000000-0000-0000-0000-000000000103}
         *
         * File Collection Cycle
         *
         * {00000000-0000-0000-0000-000000000104}
         *
         * IDMIF Collection Cycle
         *
         * {00000000-0000-0000-0000-000000000105}
         *
         * Software Metering Usage Report Cycle
         *
         * {00000000-0000-0000-0000-000000000106}
         *
         * Windows Installer Source List Update Cycle
         *
         * {00000000-0000-0000-0000-000000000107}
         *
         * Software Updates Assignments Evaluation Cycle
         *
         * {00000000-0000-0000-0000-000000000108}
         *
         * Branch Distribution Point Maintenance Task
         *
         * {00000000-0000-0000-0000-000000000109}
         *
         * DCM policy
         *
         * {00000000-0000-0000-0000-000000000110}
         *
         * Send Unsent State Message
         *
         * {00000000-0000-0000-0000-000000000111}
         *
         * State System policy cache cleanout
         *
         * {00000000-0000-0000-0000-000000000112}
         *
         * Scan by Update Source
         *
         * {00000000-0000-0000-0000-000000000113}
         *
         * Update Store Policy
         *
         * {00000000-0000-0000-0000-000000000114}
         *
         * State system policy bulk send high
         *
         * {00000000-0000-0000-0000-000000000115}
         *
         * State system policy bulk send low
         *
         * {00000000-0000-0000-0000-000000000116}
         *
         * Application manager policy action
         *
         * {00000000-0000-0000-0000-000000000121}
         *
         * Application manager user policy action
         *
         * {00000000-0000-0000-0000-000000000122}
         *
         * Application manager global evaluation action
         *
         * {00000000-0000-0000-0000-000000000123}
         *
         * Power management start summarizer
         *
         * {00000000-0000-0000-0000-000000000131}
         *
         * Endpoint deployment reevaluate
         *
         * {00000000-0000-0000-0000-000000000221}
         *
         * Endpoint AM policy reevaluate
         *
         * {00000000-0000-0000-0000-000000000222}
         *
         * External event detection
         *
         * {00000000-0000-0000-0000-000000000223}
         *
         */

        String options[] = {"Hardware Inventory", "Software Inventory", "Discovery Inventory", "File Collection", "IDMIF Collection", "Client Machine Authentication",
                                        "Request Machine Assignments", "Evaluate Machine Policies", "Refresh Default MP Task", "LS (Location Service) Refresh Locations Task",
                                        "LS (Location Service) Timeout Refresh Task", "Policy Agent Request Assignment (User)", "Policy Agent Evaluate Assignment (User)",
                                        "Software Metering Generating Usage Report", "Source Update Message", "Clearing proxy settings cache", "Machine Policy Agent Cleanup",
                                        "User Policy Agent Cleanup", "Policy Agent Validate Machine Policy / Assignment", "Policy Agent Validate User Policy / Assignment",
                                        "Retrying/Refreshing certificates in AD on MP", "Peer DP Status reporting", "Peer DP Pending package check schedule",
                                        "SUM Updates install schedule", "NAP action", "Hardware Inventory Collection Cycle", "Software Inventory Collection Cycle",
                                        "Discovery Data Collection Cycle", "File Collection Cycle", "IDMIF Collection Cycle", "Software Metering Usage Report Cycle",
                                        "Windows Installer Source List Update Cycle", "Software Updates Assignments Evaluation Cycle",
                                        "Branch Distribution Point Maintenance Task", "DCM policy", "Send Unsent State Message", "State System policy cache cleanout",
                                        "Scan by Update Source", "Update Store Policy", "State system policy bulk send high", "State system policy bulk send low",
                                        "AMT Status Check Policy", "Application manager policy action", "Application manager user policy action",
                                        "Application manager global evaluation action", "Power management start summarizer", "Endpoint deployment reevaluate",
                                        "Endpoint AM policy reevaluate", "External event detection"};

        String list[] = { "{00000000-0000-0000-0000-000000000001}", "{00000000-0000-0000-0000-000000000002}", "{00000000-0000-0000-0000-000000000003}",
                                        "{00000000-0000-0000-0000-000000000010}", "{00000000-0000-0000-0000-000000000011}",  "{00000000-0000-0000-0000-000000000012}",
                                        "{00000000-0000-0000-0000-000000000021}", "{00000000-0000-0000-0000-000000000022}", "{00000000-0000-0000-0000-000000000023}",
                                        "{00000000-0000-0000-0000-000000000024}", "{00000000-0000-0000-0000-000000000025}", "{00000000-0000-0000-0000-000000000026}",
                                        "{00000000-0000-0000-0000-000000000027", "{00000000-0000-0000-0000-000000000031}", "{00000000-0000-0000-0000-000000000032}",
                                        "{00000000-0000-0000-0000-000000000037}", "{00000000-0000-0000-0000-000000000040}", "{00000000-0000-0000-0000-000000000041}",
                                        "{00000000-0000-0000-0000-000000000042}", "{00000000-0000-0000-0000-000000000043}", "{00000000-0000-0000-0000-000000000051}",
                                        "{00000000-0000-0000-0000-000000000061}", "{00000000-0000-0000-0000-000000000062}", "{00000000-0000-0000-0000-000000000063}",
                                        "{00000000-0000-0000-0000-000000000071}", "{00000000-0000-0000-0000-000000000101}", "{00000000-0000-0000-0000-000000000102}",
                                        "{00000000-0000-0000-0000-000000000103}", "{00000000-0000-0000-0000-000000000104}", "{00000000-0000-0000-0000-000000000105}",
                                        "{00000000-0000-0000-0000-000000000106}", "{00000000-0000-0000-0000-000000000107}", "{00000000-0000-0000-0000-000000000108}",
                                        "{00000000-0000-0000-0000-000000000109}", "{00000000-0000-0000-0000-000000000110}", "{00000000-0000-0000-0000-000000000111}",
                                        "{00000000-0000-0000-0000-000000000112}", "{00000000-0000-0000-0000-000000000113}", "{00000000-0000-0000-0000-000000000114}",
                                        "{00000000-0000-0000-0000-000000000115}", "{00000000-0000-0000-0000-000000000116}", "{00000000-0000-0000-0000-000000000120}",
                                        "{00000000-0000-0000-0000-000000000121}", "{00000000-0000-0000-0000-000000000122}", "{00000000-0000-0000-0000-000000000123}",
                                        "{00000000-0000-0000-0000-000000000131}", "{00000000-0000-0000-0000-000000000221}", "{00000000-0000-0000-0000-000000000222}",
                                        "{00000000-0000-0000-0000-000000000223}"};

        String[] option = { "Hardware Inventory", "Software Inventory", "Discovery Inventory", "File Collection",
                "IDMIF Collection", "Client Machine Authentication", "Request Machine Assignments",
                "Evaluate Machine Policies", "Refresh Default MP Task", "LS (Location Service) Refresh Locations Task",
                "LS (Location Service) Timeout Refresh Task", "Policy Agent Request Assignment (User)",
                "Policy Agent Evaluate Assignment (User)", "Software Metering Generating Usage Report",
                "Source Update Message", "Clearing proxy settings cache", "Machine Policy Agent Cleanup",
                "User Policy Agent Cleanup", "Policy Agent Validate Machine Policy / Assignment",
                "Policy Agent Validate User Policy / Assignment", "Retrying/Refreshing certificates in AD on MP",
                "Peer DP Status reporting", "Peer DP Pending package check schedule", "SUM Updates install schedule",
                "NAP action", "Hardware Inventory Collection Cycle", "Software Inventory Collection Cycle",
                "Discovery Data Collection Cycle", "File Collection Cycle", "IDMIF Collection Cycle",
                "Software Metering Usage Report Cycle", "Windows Installer Source List Update Cycle",
                "Software Updates Assignments Evaluation Cycle", "Branch Distribution Point Maintenance Task",
                "DCM policy", "Send Unsent State Message", "State System policy cache cleanout",
                "Scan by Update Source", "Update Store Policy", "State system policy bulk send high",
                "State system policy bulk send low", "AMT Status Check Policy", "Application manager policy action",
                "Application manager user policy action", "Application manager global evaluation action",
                "Power management start summarizer", "Endpoint deployment reevaluate", "Endpoint AM policy reevaluate",
                "External event detection" };

        String[] choice = { "{00000000-0000-0000-0000-000000000001}", "{00000000-0000-0000-0000-000000000002}", "{00000000-0000-0000-0000-000000000003}",
                            "{00000000-0000-0000-0000-000000000010}", "{00000000-0000-0000-0000-000000000011}", "{00000000-0000-0000-0000-000000000012}",
                            "{00000000-0000-0000-0000-000000000021}", "{00000000-0000-0000-0000-000000000022}", "{00000000-0000-0000-0000-000000000023}",
                            "{00000000-0000-0000-0000-000000000024}", "{00000000-0000-0000-0000-000000000025}", "{00000000-0000-0000-0000-000000000026}",
                            "{00000000-0000-0000-0000-000000000027", "{00000000-0000-0000-0000-000000000031}", "{00000000-0000-0000-0000-000000000032}",
                            "{00000000-0000-0000-0000-000000000037}", "{00000000-0000-0000-0000-000000000040}", "{00000000-0000-0000-0000-000000000041}",
                            "{00000000-0000-0000-0000-000000000042}", "{00000000-0000-0000-0000-000000000043}", "{00000000-0000-0000-0000-000000000051}",
                            "{00000000-0000-0000-0000-000000000061}", "{00000000-0000-0000-0000-000000000062}", "{00000000-0000-0000-0000-000000000063}",
                            "{00000000-0000-0000-0000-000000000071}", "{00000000-0000-0000-0000-000000000101}", "{00000000-0000-0000-0000-000000000102}",
                            "{00000000-0000-0000-0000-000000000103}", "{00000000-0000-0000-0000-000000000104}", "{00000000-0000-0000-0000-000000000105}",
                            "{00000000-0000-0000-0000-000000000106}", "{00000000-0000-0000-0000-000000000107}", "{00000000-0000-0000-0000-000000000108}",
                            "{00000000-0000-0000-0000-000000000109}", "{00000000-0000-0000-0000-000000000110}", "{00000000-0000-0000-0000-000000000111}",
                            "{00000000-0000-0000-0000-000000000112}", "{00000000-0000-0000-0000-000000000113}", "{00000000-0000-0000-0000-000000000114}",
                            "{00000000-0000-0000-0000-000000000115}", "{00000000-0000-0000-0000-000000000116}", "{00000000-0000-0000-0000-000000000120}",
                            "{00000000-0000-0000-0000-000000000121}", "{00000000-0000-0000-0000-000000000122}", "{00000000-0000-0000-0000-000000000123}",
                            "{00000000-0000-0000-0000-000000000131}", "{00000000-0000-0000-0000-000000000221}", "{00000000-0000-0000-0000-000000000222}",
                            "{00000000-0000-0000-0000-000000000223}" };

        JPanel jPanel = new JPanel();
        JComboBox jComboBox = new JComboBox(options);
        add(jComboBox);
//        jPanel.add(jComboBox);
//        add(jPanel);
    }

    public void actionPerformed(ActionEvent ae)
    {
        Runtime runtime = Runtime.getRuntime();
        Process process = rt.exec();
	}

   public static void main(String[] args)
    {
        new WMIC();
    }
}