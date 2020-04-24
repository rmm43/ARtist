package com.example.artistapp2;

import java.util.ArrayList;

public class Board {

    private static Board sBoard;
    private String hash;
    private ArrayList<String> hashes;

    public static Board getInstance()
    {
        if(sBoard == null)
        {
            sBoard = new Board();
        }
        return sBoard;
    }

    public void addHash(String newHash)
    {
        hashes.add(newHash);
    }

    public ArrayList<String> getHash()
    {
            return hashes;
    }

    private Board(){hashes = new ArrayList<String>();}

}
