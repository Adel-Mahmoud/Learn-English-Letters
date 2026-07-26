package com.adel.myapplication;


public class Letter {


    private String smallLetter;

    private String capitalLetter;

    private String word;

    private int image;



    public Letter(
            String smallLetter,
            String capitalLetter,
            String word,
            int image
    ){


        this.smallLetter = smallLetter;

        this.capitalLetter = capitalLetter;

        this.word = word;

        this.image = image;


    }





    public String getSmallLetter(){

        return smallLetter;

    }




    public String getCapitalLetter(){

        return capitalLetter;

    }




    public String getWord(){

        return word;

    }




    public int getImage(){

        return image;

    }



}