/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addon.extension;

/**
 *
 * @author sophoan
 */
public class Debugger {
    
    public static boolean enabled = false;
    
    public static void debug(String msg){
        if (!enabled)
            return;
        System.out.println(msg);
    }
}
