/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package football_championship_manager;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Tomasz Dawid Wasowski
 */
public class GUI {
    
    //<editor-fold defaultstate="collapsed" desc="Fields">
    
    PremierLeagueManager leagueManager;
    
    JFrame frame;
    
    JPanel framePanel;
    JPanel buttonPanel;
    JPanel searchPanel;

    JScrollPane leagueTablePane;
    JTable leagueTable;
    
    JButton displayTableButton;
    JButton sortByGoalsButton;
    JButton sortByWinsButton;
    
    JButton displayMatchesButton;
    JButton generateMatchButton;
    
    JButton searchMatchButton;
    JTextField searchMatchTextField;
    
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructors">
    
    //This constructor assigns the league manager object and calls the method
    //which actually creates the physical GUI
    public GUI(PremierLeagueManager leagueManager) {
        setPremierLeagueManager(leagueManager);    
        createGUI();    
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Set Methods">
    
    public void setPremierLeagueManager(PremierLeagueManager leagueManager){
        this.leagueManager = leagueManager;
    }
    
//</editor-fold>
      
    //<editor-fold defaultstate="collapsed" desc="GUI Creation Methods">
    
    private void createGUI() {
    
        //Creates a frame for the GUI
        frame = new JFrame("Premier League Manager");
        
        //INITIATE FIELD COMPONENTS ----------------------------------------
        
        //Initiates JPanels with GridBagLayouts
        framePanel = new JPanel(new GridBagLayout()); 
        buttonPanel = new JPanel(new GridBagLayout());
        searchPanel = new JPanel(new GridBagLayout());
    
        //Initiates JTable and JScrollPane while inserting league data
        leagueTable = new JTable(insertTableData(), insertTableHeaders());
        leagueTablePane = new JScrollPane(leagueTable);
        
        //Initiates all the JButtons and JTextField
        displayTableButton = new JButton("Dispaly League Table");
        sortByGoalsButton = new JButton("Sort Table By Goals");
        sortByWinsButton = new JButton("Sort Table by Wins");
        displayMatchesButton = new JButton("Display Played Matches");
        generateMatchButton = new JButton("Generate a Random Match");    
        searchMatchButton = new JButton("Search");
        searchMatchTextField = new JTextField(6);
           
        //SET CONSTRAINTS FOR BUTTON PANEL ---------------------------------

        //Creating layout constraints for displayTableButton
        GridBagConstraints displayTableButtonConstraints = 
                new GridBagConstraints();
        assignConstraints(displayTableButtonConstraints, 0, 0);
               
        //Creating layout constraints for sortByGoals
        GridBagConstraints sortByGoalsButtonConstraints = 
                new GridBagConstraints();
        assignConstraints(sortByGoalsButtonConstraints, 0, 1);
        
        //Creating layout constraints for sortByWins
        GridBagConstraints sortByWinsButtonConstraints = 
                new GridBagConstraints();
        assignConstraints(sortByWinsButtonConstraints, 0, 2);
        
        //Creating layout constraints for displayMatchesButton
        GridBagConstraints displayMatchesButtonConstraints = 
                new GridBagConstraints();
        assignConstraints(displayMatchesButtonConstraints, 2, 0);
        
        //Creating layout constraints for generateMatchButton
        GridBagConstraints generateMatchButtonConstraints = 
                new GridBagConstraints();
        assignConstraints(generateMatchButtonConstraints, 2, 1);
        
        //Creating layout constraints for searchPanel
        GridBagConstraints searchPanelConstraints =
                new GridBagConstraints();
        assignConstraints(searchPanelConstraints, 1, 2, 2, 1);

        //SET CONSTRAINTS FOR SERACH PANEL ---------------------------------
        
        //Creating layout constraints for searchButton
        GridBagConstraints searchButtonConstraints = 
                new GridBagConstraints();
        assignConstraints(searchButtonConstraints, 0, 0);
        
        //Creating layout constraints for searchTextField
        GridBagConstraints searchTextFieldConstraints = 
                new GridBagConstraints();
        assignConstraints(searchTextFieldConstraints, 1, 0); 
         
        // SET CONSTRAINTS FOR FRAME ---------------------------------------
        
        //Creating layout constraints for buttonPanel
        GridBagConstraints buttonPanelConstraints =
                new GridBagConstraints();
        assignConstraints(buttonPanelConstraints, 0, 0, 3, 1);
        
        //Creating layout constraints for leagueTable
        GridBagConstraints leagueTablePaneConstraints = 
                new GridBagConstraints();
        assignConstraints(leagueTablePaneConstraints, 0, 2, 3, 2);
                
        //ADD COMPONENTS TO PANELS -----------------------------------------
        
        //Adds search components to the search panel using their constraints
        searchPanel.add(searchMatchButton, searchButtonConstraints);
        searchPanel.add(searchMatchTextField, searchTextFieldConstraints);
        
        //Adds button components to the button panel using their constraints
        buttonPanel.add(displayTableButton, displayTableButtonConstraints);
        buttonPanel.add(sortByGoalsButton, sortByGoalsButtonConstraints);
        buttonPanel.add(sortByWinsButton, sortByWinsButtonConstraints);
        
        buttonPanel.add(displayMatchesButton, displayMatchesButtonConstraints);
        buttonPanel.add(generateMatchButton, generateMatchButtonConstraints);
        
        buttonPanel.add(searchPanel, searchPanelConstraints);       
        
        //Adds the previous panels to the main frame panel
        framePanel.add(buttonPanel, buttonPanelConstraints);
        framePanel.add(leagueTablePane, leagueTablePaneConstraints);

        
        //SET GUI ADJUSTMENTS ----------------------------------------------
        
        //Set button preferred size to standardise all buttons
        Dimension buttonDimension = new Dimension(220, 36);
        
        //Sets scrollpane preferred size
        leagueTablePane.setPreferredSize(new Dimension(442,143));
        
        //Adjusts table column widths to match league table column headers
        adjustTableColumns();
        
        //Assigns the preferred button dimension to most buttons
        displayTableButton.setPreferredSize(buttonDimension);
        sortByGoalsButton.setPreferredSize(buttonDimension);
        sortByWinsButton.setPreferredSize(buttonDimension); 
        displayMatchesButton.setPreferredSize(buttonDimension);
        generateMatchButton.setPreferredSize(buttonDimension);
        
        //Assigns a special button dimension to the search button
        searchMatchButton.setPreferredSize(new Dimension(85, 36));
        
        //This gets the font of the search field and creates a new, bigger font
        Font searchTextFieldFont = 
                searchMatchTextField.getFont().deriveFont((float)25);
        
        //Setting the bigger font to searchTextField
        searchMatchTextField.setFont(searchTextFieldFont);
        
        //Adds padding to the bottom of buttonPanel 
        //to seperate it from leagueTable
        buttonPanel.setBorder(new EmptyBorder(0,0,3,0));
        
        //ASSIGN LISTENERS -------------------------------------------------
        
        //Assign event handler to the main frame for window events
        frame.addWindowListener(new leagueWindowListener());
        
        //Assigns action listener to all buttons 
        displayTableButton.addActionListener(new leagueActionListener());
        sortByGoalsButton.addActionListener(new leagueActionListener());
        sortByWinsButton.addActionListener(new leagueActionListener());
        displayMatchesButton.addActionListener(new leagueActionListener());
        generateMatchButton.addActionListener(new leagueActionListener());
        searchMatchButton.addActionListener(new leagueActionListener());
        
        //FINAL ADJUSTMENTS ------------------------------------------------
        
        //The GUI starts with the league table displayed, so the button to 
        //display it will start as disabled.
        displayTableButton.setEnabled(false);
                
        //Sets the fullFramePanel as the content pane for the frame
        frame.setContentPane(framePanel);
        
        //Forces the GUI window to display above everything else
        //This was done to make the window pop up when the GUI is created
        frame.setAlwaysOnTop( true );

        //Sets window size
        frame.setSize(465,300); //465
        
        //Forces the window to be centered, regardless of screen resolution
        frame.setLocationRelativeTo( null );
        
        //Sets the window to appear visible
        frame.setVisible(true);      
    }
    
    //Passes the constraints and assigns starting coordinates
    //Used to reduce the amount of code (As these lines have to be repeated
    //many times
    private void assignConstraints(GridBagConstraints constraints, 
            int x, int y) {
        constraints.gridx = x;
        constraints.gridy = y;     
    }
    
    //Same as above; also includes the the width and height of the component
    private void assignConstraints(GridBagConstraints constraints,
            int x, int y, int width, int height) {       
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = width;
        constraints.gridheight = height;    
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Button Functions">
    
    //Called when the display league table button is pressed
    private void displayTable() {
        
        //Grays out the button to display the table, as it is already displayed
        displayTableButton.setEnabled(false);
        
        //Enables the buttons that would usually be disabled coming from 
        //match table view
        displayMatchesButton.setEnabled(true);
        sortByGoalsButton.setEnabled(true);
        sortByWinsButton.setEnabled(true);
                  
        //Uses a default table sort to ensure the data is in the correct order
        leagueManager.leagueTableSort();

        //Refreshes the table with sorted club data
        refreshLeagueTable();
    }
    
    //Called when sort by goals button is pressed
    private void sortByGoals() {
        
        //Sorts the table by scored goals
        leagueManager.leagueTableSortByGoals();
        
        //Refreshes the table with sorted club data
        refreshLeagueTable();
    }
    
    //Called when sort by wins button is pressed
    private void sortByWins() {
        
        //Sorts table by achieved wins
        leagueManager.leagueTableSortByWins();
        
        //Refreshes the table with sorted club data
        refreshLeagueTable();    
    }
    
    //Called when display matches button is pressed
    private void displayMatches() {
        
        //Disables all the button that are irrelevant with this table view
        displayMatchesButton.setEnabled(false);
        sortByGoalsButton.setEnabled(false);
        sortByWinsButton.setEnabled(false);
        
        //Enables the button to display leagueTable again
        displayTableButton.setEnabled(true);
  
        //Sorts the matches by match date to ensure that they are 
        //correctly displalyed
        leagueManager.leagueMatchesSort();

        //Refreshes the table with sorted match data
        refreshMatchesTable();      
    }
    
    //Called when the generate match button is pressed
    private void generateMatch() {
        
        //Calls the method which generates a random match from leagueManager
        leagueManager.generateRandomMatch();

        //Decides whether the user is currently looking at the league or match
        //table and refreshes the one that they are currently viewing
        //As a result, the user will see the table update no matter wich one
        //they currently have selected
        if(leagueTable.getColumnName(0).equals("Pos")) {           
            leagueManager.leagueTableSort();
            refreshLeagueTable();     
        } else if (leagueTable.getColumnName(0).equals("Date")) {
            leagueManager.leagueMatchesSort();
            refreshMatchesTable();
        }  
    }
    
    //Called when the search match button is pressed
    private void searchMatch() {
        
        //Disables sorting buttons, as they cannot sort this table format
        sortByGoalsButton.setEnabled(false);
        sortByWinsButton.setEnabled(false);
        
        //Enables display buttons to allow user to return to the standard tables
        displayTableButton.setEnabled(true);
        displayMatchesButton.setEnabled(true);
        
        //Imports league matches form league manager
        ArrayList<FootballMatch> leagueMatches = 
                leagueManager.getLeagueMatches();
        
        //Creates a new arraylist which will contain all matches that took
        //place on the searched for date
        ArrayList<FootballMatch> dateSpecificMatches =
                new ArrayList<FootballMatch>();
       
        //Creates simple date format
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        //String to store the simple date
        String matchDate;   

        //Checks for matches with the matching date
        for (FootballMatch match: leagueMatches) {
            
            matchDate = sdf.format(match.getMatchDate());
            
            if (matchDate.equals(searchMatchTextField.getText())) {
                
                dateSpecificMatches.add(match);
                
            }    
        }
        
        //Code below generates match data to feed to the table using date 
        //specific matches retrieved from code above
        Object[][] matchData = new Object[dateSpecificMatches.size()][];
        int i = 0;
 
        for (FootballMatch match: dateSpecificMatches) {
            
            matchDate = sdf.format(match.getMatchDate());
            
            Object[] matchArray = {
                matchDate,
                match.getFirstClub().getClubName(),
                match.getFirstClubScore() + ":" + match.getSecondClubScore(),
                match.getSecondClub().getClubName()
            };
            
            matchData[i] = matchArray;
            i++;
        }
        
        //Replaces table with search result data
        JTable table = new JTable(matchData, insertMatchHeaders());
        
        leagueTable.setModel(table.getModel());
        adjustMatchColumns();       
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Table Methods">
    
    //Returns table headers for creating the league table
    private String[] insertTableHeaders() {
        
        String[] tableHeaders = {"Pos", "Club", "MP",
                "W", "D", "L", "GF", "GA", "GD", "Pts"};
        
        return tableHeaders;   
    }
    
    //Returns table data for creating the league table
    private Object[][] insertTableData() {
        
        //Ensures that the data in league table is sorted appropriately
        ArrayList<FootballClub> leagueClubs = leagueManager.getLeagueClubs();

        //Creates 2D array
        Object[][] tableData = new Object[leagueClubs.size()][];
        
        //Fills inner array (row) with individual club data
        int posCounter = 0;
        for (FootballClub club: leagueClubs) {
            
            Object[] clubArray = {
                ++posCounter,
                club.getClubName(),
                club.getMatchesPlayed(), 
                club.getWins(), 
                club.getDraws(),
                club.getDefeats(), 
                club.getGoalsScored(), 
                club.getGoalsReceived(), 
                club.getGoalDifference(), 
                club.getPoints()};
            
            //Inserts row into outter array 
            tableData[posCounter-1] = clubArray;
            
        }
        
        return tableData;      
    }  
    
    //Returns headers fro creating match table
    private String[] insertMatchHeaders() {
        
        String[] matchHeaders = {"Date","First Club","Score","Second Club"};
   
        return matchHeaders;   
    }
 
    //Returns data for creating match table
    private Object[][] insertMatchData() {
        
        ArrayList<FootballMatch> leagueMatches = 
                leagueManager.getLeagueMatches();
        
        //Creates 2D array to store rows
        Object[][] matchData = new Object[leagueMatches.size()][];
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String matchDate;
        int i = 0;
  
        //Populates 2D array
        for (FootballMatch match: leagueMatches) {
            
            matchDate = sdf.format(match.getMatchDate());
            
            //Creates and populates each row with match data
            Object[] matchArray = {
                matchDate,
                match.getFirstClub().getClubName(),
                match.getFirstClubScore() + ":" + match.getSecondClubScore(),
                match.getSecondClub().getClubName()
            };
            
            //Adds row to outter array
            matchData[i] = matchArray;
            i++;
        }
        
        return matchData;  
    }
    
    //This method adjusts the column widths to correctly 
    //display leagueTable data    
    private void adjustTableColumns() {

        //Only need to change the column with the name of the table, the rest 
        //will automatically adjust to fit around it.
        changeColumnWidth(1,300); //300 is an arbitrary large figure
    }
    
    //This method adjusts the column widths to correctly display match data
    private void adjustMatchColumns() {
        
        changeColumnWidth(1,200);
        changeColumnWidth(3,200);
        changeColumnWidth(0,100);     
    }

    //This method changes the width of a specific column 
    //within the leagueTable; used with above methods
    private void changeColumnWidth(int col, int width) {
        
        leagueTable.getColumnModel().getColumn(col).setPreferredWidth(width);     
    }
    
    //This method displays the club information in the leagueTable
    private void refreshLeagueTable() {
        
        JTable table = new JTable(insertTableData(), insertTableHeaders());

        leagueTable.setModel(table.getModel());
        adjustTableColumns();
    }
    
    //This method displays match history in the leagueTable
    private void refreshMatchesTable() {
        
        JTable table = new JTable(insertMatchData(), insertMatchHeaders());
        
        leagueTable.setModel(table.getModel());
        adjustMatchColumns();     
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Listeners">
 
    //Nested ActionListener implementation class, so that it can 
    //access the the private buttons to identify event origins
    class leagueActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            //Calls appropriate method depending on which button is pressed
            if (e.getSource() == displayTableButton) {
                displayTable();
            } else if (e.getSource() == sortByGoalsButton) {
                sortByGoals();
            } else if (e.getSource() == sortByWinsButton) {
                sortByWins();
            } else if (e.getSource() == displayMatchesButton) {
                displayMatches();
            } else if (e.getSource() == generateMatchButton) {
                generateMatch();
            } else if (e.getSource() == searchMatchButton) {
                searchMatch();
            }                      
        }   
    }
}

//Window listener class, used primarily to identify when the x button is pressed
class leagueWindowListener extends WindowAdapter {
    
    @Override
    public void windowClosing(WindowEvent event) {
        
        //Terminates the program when the x button on the GUI is pressed
        System.out.println("\n--- Closing the GUI window.");
        System.exit(0);           
    }  
}

//</editor-fold>