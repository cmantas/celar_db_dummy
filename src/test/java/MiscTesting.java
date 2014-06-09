/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author cmantas
 */
public class MiscTesting {
    
       public static String makeStringID(int uniqueId, int majorVersion, int minorVersion){
       String rv = String.format("%010d", uniqueId) +"."+
               String.format("%03d", majorVersion)+"."+
               String.format("%03d", minorVersion);
        return rv;
    }
    
    public static void main(String args[]){
        
        int uniqueId =123;
        int majorVersion = 2;
        int minorVersion =3;
        
        System.out.println(makeStringID(uniqueId, majorVersion, minorVersion));
    }
    
}
