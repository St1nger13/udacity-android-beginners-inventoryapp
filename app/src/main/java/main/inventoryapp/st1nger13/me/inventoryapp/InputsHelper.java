package main.inventoryapp.st1nger13.me.inventoryapp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by St1nger13 on 30.06.2016.
 */
public class InputsHelper
{
    /**
     * Method checks input Name of Product
     * @param name
     * @return
     */
    public static boolean checkAddProductInputName(String name)
    {
        if(name == null || name.length() < 5)
            return false ;
        return true ;
    }

    /**
     * Method checks is String can be parsed as Integer
     * @param integer
     * @return
     */
    public static boolean checkIsStringValidInteger(String integer)
    {
        if(integer == null || integer.length() == 0)
            return false ;

        try
        {
            Integer.parseInt(integer) ;
        }
        catch(Exception e)
        {
            return false;
        }

        return true ;
    }

    /**
     * Checks Email Address
     * @param mailAddress
     * @return
     */
    public static boolean checkAddProductInputMailAddress(String mailAddress)
    {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN) ;
        Matcher matcher = pattern.matcher(mailAddress) ;

        return matcher.matches() ;
    }
}
