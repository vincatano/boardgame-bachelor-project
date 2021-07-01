package it.polimi.ingsw.model.gamelogic.checker;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * This interface implements the default method to invoke methods using Java Reflection from a list of rules taken before from the tool card chosen
 */

interface InspectorTool {
    /**
     * Invoke dynamically the method from the ruleEngine and verify return value.
     * @param nameMethods Name methods to invoke, token from tool card before.
     * @param ruleEngine Object which contains all implemented methods.
     * @return True if all methods invoked returned true.
     */
   default boolean doMethods(List<String> nameMethods, RuleEngine ruleEngine){
        for(String name : nameMethods){
            try {
                Method method = ruleEngine.getClass().getDeclaredMethod(name);
                if(!(boolean)method.invoke(ruleEngine)){
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    default boolean doMethods(String nameMethod, RuleEngine ruleEngine){
       try {
            Method method = null;
            method = ruleEngine.getClass().getDeclaredMethod(nameMethod);
           if(!(boolean)method.invoke(ruleEngine)) {
               return false;
           }
           return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }












}
