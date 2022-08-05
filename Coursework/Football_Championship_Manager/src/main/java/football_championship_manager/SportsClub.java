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
abstract class SportsClub {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    
    private String clubName;
    private String clubLocation;
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructors">
    
    //Constructor for Sports Club - Sets the club name and location fields
    public SportsClub(String clubName, String clubLocation) {
        setClubName(clubName);
        setClubLocation(clubLocation);
    }
  
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Set Methods">
    
    //Set method to assign value to the clubName field
    public void setClubName(String clubName) {        
        this.clubName = clubName;    
    }
    
    //Set method to assign value to the clubLocation field
    public void setClubLocation(String clubLocation) {
        this.clubLocation = clubLocation;
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get Methods">
    
    //Get method to retrieve the clubName value
    public String getClubName() {       
        return clubName;     
    }
    
    //Get method to retrieve the clubName value
    public String getClubLocation() {
        return clubLocation;
    }
    
//</editor-fold>
              
}
