/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package football_championship_manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;
import java.util.Random;


/**
 *
 * @author Tomasz Dawid Wasowski
 */
public class PremierLeagueManager implements LeagueManager {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    
    //This ArrayList is used to store football clubs
    private ArrayList<FootballClub> leagueClubs;
    
    //This ArrayList is used to store the played out matches
    private ArrayList<FootballMatch> leagueMatches;
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructors">

    //This constructor initialises both Array Lists
    public PremierLeagueManager() {
        leagueClubs = new ArrayList<FootballClub>();
        leagueMatches = new ArrayList<FootballMatch>();
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get Methods">
    
    //These get methods are used by the GUI to retrieve the private arraylists
    public ArrayList<FootballClub> getLeagueClubs() {        
        return leagueClubs;        
    }
    
    public ArrayList<FootballMatch> getLeagueMatches() {
        return leagueMatches;
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Overridden Methods">
    
    @Override
    public void menu() {

        //Automaticall loads clubs and matches from respective save files
        loadFromFile();
        
        //Print commands available to the user
        printMenuCommands();
        
        //Flag to check if the program is running for menu loop
        boolean running = true;
        
        //Main loop that keeps the program running until the user enters 'q'
        //or chooses to display the GUI
        while (running) {
            
            System.out.println("\n- Enter your command below:");
            
            //Switch case statement which defines the user's possible choices
            //Some of these options were used for early testing and are
            //commented out for the final version of the program
            switch(collectUserInput().toLowerCase()){
                
                case "a" -> createClub();
                
                case "d" -> deleteClub();
                    
                case "c" -> displayClubStatistics();
                    
                case "t" -> displayLeagueTable();
                    
                case "m" -> addPlayedMatch();
                
                //Start GUI returns 'false' in order to terminate the menu
                case "g" -> running = startGUI();
                
                case "r" -> restartManager();
                    
                //case "s" -> saveToFile();
                    
                //case "l" -> loadFromFile();
                
                case "p" -> printMenuCommands();
                
                //case "x" -> insertTestData();
                    
                case "q" -> running = false;
                    
                default -> System.out.println(
                        "\n--- Please enter a valid command!");                         
            }          
        }        
    }
     
    @Override
    public void createClub() {
        System.out.println("\n--- Creating new club.");
        
        System.out.println("- Please enter the club name:"); 
        String clubName = collectUserInput();
        
        System.out.println("\n- Please enter the club location:");
        String clubLocation = collectUserInput();
        
        FootballClub club = new FootballClub(clubName, clubLocation);
        leagueClubs.add(club);
        
        System.out.println("\n--- The club " + clubName + ", located in " + 
                clubLocation + ", has been created and added to the league.");
        
        //Automatically overwrites the save file with the updated club list
        saveToFile();
    }
     
    @Override
    public void deleteClub() {
        System.out.println("\n--- Deleting (Relegating) existing club.");
        
        System.out.println("- Please enter club name:");
        String clubName = collectUserInput().toLowerCase();
        
        //Flag to check if the club is found after the loop terminates
        boolean found = false;
        for (FootballClub club: leagueClubs) {
            if (club.getClubName().toLowerCase().equals(clubName)) {
                leagueClubs.remove(club);
                found = true;
                break;               
            }
        }
        
        //Checks flag to determine feedback
        if (found) {
            System.out.println("\n--- The club " + clubName + " has been "
                    + "relegated from the premier league.");
            
            //Automatically overwrites the save file with the updated club list
            saveToFile();
        } else {
            System.out.println("\n--- " + clubName + " is not currently "
                    + "in the premier league, so it cannot be relegated.");
        }
    }

    @Override
    public void displayClubStatistics() {
        System.out.println("\n--- Displaying club statistics.");
        
        System.out.println("- Please enter club name:");
        String clubName = collectUserInput().toLowerCase();
        
        //Falg to determine whether the club has been found in the list
        boolean found = false;
        
        for (FootballClub club : leagueClubs) {
            if (club.getClubName().toLowerCase().equals(clubName)) {
                System.out.println("\nClub Statistics for " + 
                        club.getClubName());
                System.out.println("---------------------");
                
                System.out.println("  Wins: " + 
                        Integer.toString(club.getWins()));
                
                System.out.println("  Draws: " +
                        Integer.toString(club.getDraws()));
                
                System.out.println("  Defeats: " + 
                        Integer.toString(club.getDefeats()));
                
                System.out.println("  Goals Scored: " + 
                        Integer.toString(club.getGoalsScored()));
                
                System.out.println("  Goals Recieved: " +
                        Integer.toString(club.getGoalsReceived()));
                
                System.out.println("  Goal Difference: " +
                        Integer.toString(club.getGoalDifference()));
                
                System.out.println("  Points: " +
                        Integer.toString(club.getPoints()));
                
                System.out.println("  Matches Played: " +
                        Integer.toString(club.getMatchesPlayed()));
                
                found = true;
                
                break;
            }
        }
        
        //Checks for the flag to see if the club was found in leagueClubs
        if (!found) {
            System.out.println("\n--- " + clubName + " is not currently "
                    + "in the league, so it's stats cannot be displayed.");
        }    
    }

    @Override
    public void displayLeagueTable() {
        System.out.println("\n--- Displaying league statistics.");
        
        //This method sorts the league table based on points, then goal 
        //difference, then name, before the table is displayed to the user
        leagueTableSort();
        
        System.out.println("League Statistics");
        System.out.println("---------------------");
        
        //Creates a format for the table data
        String tableFormat = " %-3d | %-30s | %-2d | %-2d | %-2d | %-2d | "
                + "%-4d | %-4d | %-4d | %-4d %n";
        
        //Prints the column names, formatted to match table data
        System.out.format(" %-3s | %-30s | %-2s | %-2s | %-2s | %-2s | "
                + "%-4s | %-4s | %-4s | %-4s %n", "Pos", "Club", "MP",
                "W", "D", "L", "GF", "GA", "GD", "Pts");
        
        //Prints the seperator line between column names and table data
        System.out.println("-----+" + "--------------------------------+" +
                "----+" + "----+" + "----+" + "----+" + "------+" +
                "------+" + "------+" + "------");
        
        //Prints the data in the table format
        int posCounter = 0;
        for (FootballClub club : leagueClubs) {
            System.out.format(tableFormat, 
                    ++posCounter, 
                    club.getClubName(),
                    club.getMatchesPlayed(), 
                    club.getWins(), 
                    club.getDraws(),
                    club.getDefeats(), 
                    club.getGoalsScored(), 
                    club.getGoalsReceived(), 
                    club.getGoalDifference(), 
                    club.getPoints());    
        }
    }

    @Override
    public void addPlayedMatch() {
        System.out.println("\n--- Adding a played match.");

        System.out.println("- Please enter the first club name:");
        String firstClubName = collectUserInput().toLowerCase();

        System.out.println("\n- Please enter the second club name:");
        String secondClubName = collectUserInput().toLowerCase();

        //Creates 2 null FootballClub objects to assign clubs to
        FootballClub firstClub = null;
        FootballClub secondClub = null;

        //Checks if the club names are found in the leagueClubs list
        for (FootballClub club: leagueClubs) {
            if (club.getClubName().toLowerCase().equals(firstClubName)) {
                firstClub = club;
            } else if (club.getClubName().toLowerCase().equals(secondClubName)){
                secondClub = club;
            }
        }

        //Checks if both teams have been found in the list
        if (firstClub != null && secondClub != null) {

            try {
            
                System.out.println("\n- Please enter the match score "
                        + "(i.e. 'x:y'):");
                String matchScore = collectUserInput();

                System.out.println("\n- Please enter the match date "
                        + "(i.e. 'dd/mm/yyyy'):");
                String matchDateString = collectUserInput();

                //Splits the matchScore into 2 seperate strings
                String[] scoreArray = matchScore.split(":");

                //Parses the scores from scoreArray as individual int scores
                int firstClubScore = Integer.parseInt(scoreArray[0]);
                int secondClubScore = Integer.parseInt(scoreArray[1]);

                //Splits date into 3 seperate strings
                String[] dateArray = matchDateString.split("/");

                //Generates Date object from strings in dateArray
                Date matchDate = new Date(Integer.parseInt(dateArray[2])-1900, 
                        Integer.parseInt(dateArray[1])-1, 
                        Integer.parseInt(dateArray[0]));

                //Creates the match object using all available parameters
                FootballMatch match = new FootballMatch(firstClub, secondClub,
                        firstClubScore, secondClubScore, matchDate);

                //Adds the match to the match ArrayList
                leagueMatches.add(match);

                //Prints confirmation message
                System.out.println("\n--- The match between " 
                        + firstClub.getClubName() + " and " 
                        + secondClub.getClubName() + " on " + matchDateString 
                        + " has been added.");

                //Automatically overwrites the save file with the updated lists
                saveToFile();
                
            } catch (Exception exception) {
                //The only things that can throw an exception in the code above
                //are the match results and date arrays. As a result, we can
                //warn the user about incorrect match details
                System.out.println("\n--- Please enter a valid match details.");
            }

        } else {
            //Prints error message if one of the two clubs isn't found in list
            System.out.println("\n--- It seems that one of the teams you "
                    + "entered is not in the premier league, so this "
                    + "match could not have happened.");
        }   
    }

    @Override
    public void saveToFile() {
       
        //Sorting before saving standardises the way that files are saved.
        leagueTableSort();
        leagueMatchesSort();
        
        //Saves each list individually
        saveLeagueClubs();
        saveLeagueMatches();
        
        //Prints confirmation message
        System.out.println("\n--- Your files have been successfully saved.");  
    }

    @Override
    public void loadFromFile() {

        //Loads each list individually
        loadLeagueClubs();
        loadLeagueMatches(); 
        
        //Sorting after laod to ensure that the tables are sorted for display.
        leagueTableSort();
        leagueMatchesSort();      
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Other Class Methods">
    
        public void generateRandomMatch() {
        
        //Creates a new Random object to generate random values
        Random random = new Random();
        
        //Generates two random values which will reference the index values of
        //football clubs in the club arraylsit
        int firstClubIndex = random.nextInt(leagueClubs.size());
        int secondClubIndex = random.nextInt(leagueClubs.size());
        
        //This loop ensures that the first and second club are not the same
        //as a club cannot play a game against itself!
        while (firstClubIndex == secondClubIndex) {
            secondClubIndex = random.nextInt(leagueClubs.size());
        }
       
        //Creates a match object using 2 random clubs from the club arraylist
        FootballMatch match = new FootballMatch(
                leagueClubs.get(firstClubIndex), 
                leagueClubs.get(secondClubIndex),
                
                //Randomises the scores of both clubs
                random.nextInt(6), 
                random.nextInt(6), 
                
                //Creates a random date for the match to have taken place
                new Date(
                        2017+random.nextInt(4)-1900,
                        1+random.nextInt(12),
                        1+random.nextInt(29)
                    )
        );
        
        //Adds the match to the match arraylist
        leagueMatches.add(match);
        
        //Prints confirmation
        System.out.println("\n--- A new random match has been generated.");
        
        //Saves the updated match list to file
        saveToFile();
    }
    
    public void restartManager() {
        
        System.out.println("\n- Are you sure you want to restart the manager?");
        System.out.println("- This action will clear the save files and "
                + "cannot be undone.");
        System.out.println("\n- Please type 'Yes' to proceed:");
        
        if (collectUserInput().equals("Yes")) {
                    //Clears the current arraylists of all their objects 
            leagueClubs.clear();
            leagueMatches.clear();

            System.out.println("\n--- Premier League Manager has been "
            + "restarted.");

            //Clears both save files by overwriting them with an empty string
            clearFile("premierLeagueClubSave.txt");
            clearFile("premierLeagueMatchSave.txt");

            System.out.println("\n--- Premier League files have been "
                    + "cleared.");     
        } else {
            System.out.println("\n--- This command has been cancelled.");
        }
    } 
    
    //Random test data used for testing - can be inserted by un-commenting
    //'x' in the siwtch-case statement in the menu method.
    public void insertTestData() {
        
        FootballClub club1 = new FootballClub("Disneyland FC", "Arizona");
        FootballClub club2 = new FootballClub("Legoland City", "Paris");
        FootballClub club3 = new FootballClub("Phantasialand United", "London");
        
        FootballMatch match1 = new FootballMatch(club1, club2, 2, 3, 
                new Date(2014-1900, 12-1, 14));
        FootballMatch match2 = new FootballMatch(club3, club1, 4, 1,
                new Date(2015-1900, 6-1, 23));
        FootballMatch match3 = new FootballMatch(club2, club3, 1, 1,
                new Date(2014-1900, 8-1, 21));
        FootballMatch match4 = new FootballMatch(club1, club2, 2, 1,
                new Date(2016-1900, 9-1, 18));
        
        leagueClubs.add(club1);
        leagueClubs.add(club2);
        leagueClubs.add(club3);
        
        leagueMatches.add(match1);
        leagueMatches.add(match2);
        leagueMatches.add(match3);
        leagueMatches.add(match4);
    }
    
    public boolean startGUI() {
        
        System.out.println("\n--- Premier League Manager GUI is now starting");
        
        //Passes the league manager back to the GUI object
        GUI gui = new GUI(this);
        
        //returns false to stop menu interaction.
        return false;   
    }
    
    //Prints all menu commands, can be re-called by the user from the menu
    private void printMenuCommands() {
        System.out.println("Premier League Manager");
        System.out.println("Please use the following commands.");
        System.out.println("------------------------------------------------");
        System.out.println("'A' - Create a club and add it to the league");
        System.out.println("'D' - Delete a club from the league");
        System.out.println("'C' - Display a club's statistics");
        System.out.println("'T' - Display the league table");
        System.out.println("'M' - Add a played match");
        System.out.println("'P' - Print menu commands");
        System.out.println("'G' - Start GUI");
        System.out.println("'R' - Restart league manager and clear save file");
        System.out.println("'Q' - Quit program");
        System.out.println("------------------------------------------------");
    }
    
    //Collects and returns user input
    private String collectUserInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    
    public void leagueTableSort() {
        
        //Uses a comparator to compare each football club
        Comparator<FootballClub> clubComparator = Comparator
                .comparing(FootballClub::getPoints)
                .thenComparing(FootballClub::getGoalDifference)
                .thenComparing(FootballClub::getClubName);
        
        //Sorts football clubs in the club arraylist using the above comparator
        Collections.sort(leagueClubs, clubComparator);
        
        //Reverses the arrayList to get the clubs in the correct order
        Collections.reverse(leagueClubs);
    }
    
    public void leagueTableSortByGoals() {
        Comparator<FootballClub> clubComparator = Comparator
                .comparing(FootballClub::getGoalsScored)
                .thenComparing(FootballClub::getClubName);
        
        Collections.sort(leagueClubs, clubComparator);
        Collections.reverse(leagueClubs);
    }
    
    public void leagueTableSortByWins() {        
        Comparator<FootballClub> clubComparator = Comparator
                .comparing(FootballClub::getWins)
                .thenComparing(FootballClub::getClubName);
        
        Collections.sort(leagueClubs, clubComparator);
        Collections.reverse(leagueClubs);    
    }
    
    public void leagueMatchesSort() {
        Comparator<FootballMatch> matchComparator = Comparator
                .comparing(FootballMatch::getMatchDate);
        
        Collections.sort(leagueMatches, matchComparator);
        Collections.reverse(leagueMatches);
    }
    
    private void saveLeagueClubs() {
        
        try {
            
            FileWriter clubWriter;
            clubWriter = new FileWriter("premierLeagueClubSave.txt");
        
            //Save club data to the club file
            for (FootballClub club: leagueClubs) {

                //Write club data into a single line in a CSV format
                //This has been re-factored to only include club name and
                //location, since with the addition of the match class, all
                //statistics can be re-calculated when the matches are loaded.
                clubWriter.write(club.getClubName() + "," 
                        + club.getClubLocation() + "\n");           
            }   
            
            clubWriter.close(); 
            
        } catch (IOException exception) {
            System.out.println("\n--- There was an error saving "
                    + "to club file.");  
        }
    }
    
    private void saveLeagueMatches() {
        
        try {
            
            FileWriter matchWriter;
            matchWriter = new FileWriter("premierLeagueMatchSave.txt");
            
            //Creates a simple date format to save the date
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            //String to store the simple date
            String matchDate;
            
            //Saves match data to the match file
            for (FootballMatch match: leagueMatches) {
                
                matchDate = sdf.format(match.getMatchDate());
                
                matchWriter.write(match.getFirstClub().getClubName() + ","
                        + match.getSecondClub().getClubName() + ","
                        + match.getFirstClubScore() + ","
                        + match.getSecondClubScore() + ","
                        + matchDate + "\n");
            }
            
            matchWriter.close();
            
        } catch (IOException exception) {
            System.out.println("\n--- There was an error saving "
                    + "to match file.");  
        }
    }
    
    private void loadLeagueClubs() {
        
        //clears the current leagueClubs list of objects to prepare it for 
        //loading from file (Only useful when manually loading)
        leagueClubs.clear();
        
        //Attempts to load clubs from file
        try {
            
            File saveFile = new File("premierLeagueClubSave.txt");
            Scanner fileReader = new Scanner(saveFile);
            
            //Loops through each line of the txt document
            while (fileReader.hasNextLine()) {
                
                //Reads the next line of the document
                String clubString = fileReader.nextLine();
                
                //Splits the line at every comma (Since the file is in CSV)
                String[] clubData = clubString.split(",");
                
                //Re-creates the club from its saved data
                FootballClub club = new FootballClub(clubData[0], clubData[1]);

                //Adds club to the arraylist
                leagueClubs.add(club);
            } 
            
            fileReader.close();
            
            System.out.println("\n--- Premier League clubs have been "
                    + "successfully loaded.");
           
        //This catch clause checks for a plethora of excpetions that can occur
        //during the file loading process
        } catch (FileNotFoundException |
                NumberFormatException | 
                NullPointerException |
                ArrayIndexOutOfBoundsException exception) {
            System.out.println("\n--- There was an error loading from file,"
                    + "please make sure that the file exists.");
        }
    }
            
    private void loadLeagueMatches() {
        
        leagueMatches.clear();
        
        try {
            
            File saveFile = new File("premierLeagueMatchSave.txt");
            Scanner fileReader = new Scanner(saveFile);
            
            //Loops through each line of the txt document
            while (fileReader.hasNextLine()) {
                
                //Reads the next line of the document
                String matchString = fileReader.nextLine();
                
                //Splits the line at every comma (Since the file is in CSV)
                String[] matchData = matchString.split(",");
                
                //Creates 2 null FootballClub objects to assign clubs to
                FootballClub firstClub = null;
                FootballClub secondClub = null;

                //Checks if the club names are found in the leagueClubs list
                for (FootballClub club: leagueClubs) {
                    if (club.getClubName().equals(matchData[0])) {
                        firstClub = club;
                    } else if (club.getClubName().equals(matchData[1])){
                        secondClub = club;
                    }
                }
                
                //Splits date into 3 seperate strings
                String[] dateArray = matchData[4].split("/");

                //Generates Date object from 
                Date matchDate = new Date(Integer.parseInt(dateArray[2])-1900, 
                        Integer.parseInt(dateArray[1])-1, 
                        Integer.parseInt(dateArray[0]));
                
                //Creates a new match object out of the retrieved data
                FootballMatch match;
                
                match = new FootballMatch(
                        firstClub, 
                        secondClub, 
                        Integer.parseInt(matchData[2]),
                        Integer.parseInt(matchData[3]),
                        matchDate);
                
                //Adds match object to its respective arrayList
                leagueMatches.add(match);
            }
            
            fileReader.close();
            
            System.out.println("\n--- Premier League matches have been "
                    + "successfully loaded.\n");

        //This catch clause checks for a plethora of excpetions that can occur
        //during the file loading process            
        } catch (FileNotFoundException | 
                NumberFormatException | 
                NullPointerException |
                ArrayIndexOutOfBoundsException exception) {
            System.out.println("\n--- There was an error loading from file,"
                    + "please make sure that the file exists.\n");
        }
        
    }    
    
    //This method re-writes the file with an empty string, clearing it
    private void clearFile(String fileName) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write("");
            fileWriter.close();
        } catch(IOException exception) {
            System.out.println("There was an error clearing the file.");
        }
    }
    
//</editor-fold>
  
}
