/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package football_championship_manager;

/**
 *
 * @author Tomasz Dawid Wasowski
 */
public class FootballClub extends SportsClub {
   
    //<editor-fold defaultstate="collapsed" desc="Fields">
    
    private int wins;
    private int draws;
    private int defeats;
    
    private int goalsReceived;
    private int goalsScored;
    
    private int goalDifference;   
    private int points;
    private int matchesPlayed;
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructors">
    
    //Sets club name and location using super to access parent constructor
    public FootballClub(String clubName, String clubLocation) {
        super(clubName, clubLocation);
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Set Methods">
    
    public void setWins(int wins) {
        this.wins = wins;
    }
    
    public void setDraws(int draws) {
        this.draws = draws;     
    }
    
    public void setDefeats(int defeats) {
        this.defeats = defeats;
    }
    
    public void setGoalsRecieved(int goalsReceived) {
        this.goalsReceived = goalsReceived;
    }
    
    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }
    
    public void setGoalDifference(int goalDifference) {
        this.goalDifference = goalDifference;
    }
    
    public void setPoints(int points) {
        this.points = points;
    }
    
    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }
    
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Get Methods">
    
    public int getWins() {       
        return wins;
    }
    
    public int getDraws() {
        return draws;
    }
    
    public int getDefeats() {
        return defeats;
    }
    
    public int getGoalsReceived() {
        return goalsReceived;
    }
    
    public int getGoalsScored() {
        return goalsScored;
    }
    
    public int getGoalDifference() {
        return goalDifference;
    }
    
    public int getPoints() {
        return points;
    }
    
    public int getMatchesPlayed() {
        return matchesPlayed;
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Class Methods">
    
    public void incrementWins() {
        wins++;
    }
    
    public void incrementDraws() {
        draws++;
    }
        
    public void incrementDefeats() {
        defeats++;
    }
    
    public void increaseGoalsScored(int goalsScored) {
        this.goalsScored += goalsScored;
    }
    
    public void increaseGoalsReceived(int goalsReceived) {
        this.goalsReceived += goalsReceived;
    }

    //This method calculates non-set satistics and is used after every match
    //is resolved to update club stats
    public void updateStats() {
        goalDifference = goalsScored - goalsReceived;
        points = (wins * 3) + (draws * 1);
        matchesPlayed = wins + draws + defeats;
    }
    
//</editor-fold>
         
}
