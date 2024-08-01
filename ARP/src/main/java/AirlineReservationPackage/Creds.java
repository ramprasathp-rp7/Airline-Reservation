package AirlineReservationPackage;

import java.util.*;

class Creds
{
    private String userName;
    private String passWord;

    Creds(String userName, String passWord)
    {
        this.userName = userName;
        this.passWord = passWord;
    }

    String getUsername()
    {
        return this.userName;
    }

    String getPassword()
    {
        return this.passWord;
    }

    boolean checkUsername(String userName)
    {
        if (this.userName.equals(userName))
            return true;
        else
            return false;
    }

    boolean checkPassword(String passWord)
    {
        if (this.passWord.equals(passWord))
            return true;
        else
            return false;
    }

    static <T extends Creds> T checkCreds(LinkedList<T> list, String userName, String passWord)
    {
        for(T t : list)
            if (t.checkUsername(userName) && t.checkPassword(passWord)) 
                return t;

        return null;
    }
}
