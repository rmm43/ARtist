package com.example.artistapp2;

public class Board {

    private static Board sBoard;
    private String hash;

    public static Board getInstance()
    {
        if(sBoard == null)
        {
            sBoard = new Board();
        }
        return sBoard;
    }

    public void setHash(String newHash)
    {
        hash = newHash;
    }

    public String getHash()
    {
        if(hash == null)
            return "null";
        else
            return hash;
    }

    private Board(){}

}
