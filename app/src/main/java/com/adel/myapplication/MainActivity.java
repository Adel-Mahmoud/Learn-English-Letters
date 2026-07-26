package com.adel.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;


public class MainActivity extends Activity implements TextToSpeech.OnInitListener {


    private Button btnLearn;
    private Button btnGame;
    private Button btnCase;
    private Button btnRandom;
    private Button btnRepeat;

    private GridView gridLetters;

    private TextToSpeech tts;


    private String[] alphabet = {
            "a","b","c","d","e","f","g",
            "h","i","j","k","l","m",
            "n","o","p","q","r","s",
            "t","u","v","w","x","y","z"
    };


    private boolean isUpperCase = false;

    private boolean gameMode = false;

    private String currentLetter = "";

    private boolean ttsReady = false;


    private int[] states = new int[26];
	
    /*
       -1 = عادي
        1 = صحيح
        2 = خطأ
    */


    private LetterAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        btnLearn = findViewById(R.id.btnLearn);
        btnGame = findViewById(R.id.btnGame);

        btnCase = findViewById(R.id.btnCase);

        btnRandom = findViewById(R.id.btnRandom);
        btnRepeat = findViewById(R.id.btnRepeat);


        gridLetters = findViewById(R.id.gridLetters);



        resetStates();



        adapter = new LetterAdapter(
                this,
                alphabet,
                states,
                new LetterAdapter.OnLetterClickListener() {

                    @Override
                    public void onLetterClick(
                            String letter,
                            int position
                    ) {

                        if(gameMode){

                            checkAnswer(
                                    letter,
                                    position
                            );

                        }else{

                            speak(letter);

                        }

                    }
                }
        );


        gridLetters.setAdapter(adapter);



        tts = new TextToSpeech(
                this,
                this
        );



        btnLearn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                gameMode = false;

                btnRandom.setVisibility(View.GONE);
                btnRepeat.setVisibility(View.GONE);

                resetStates();

                adapter.setUpperCase(isUpperCase);

                Toast.makeText(
                        MainActivity.this,
                        "وضع التعلم",
                        Toast.LENGTH_SHORT
                ).show();

            }

        });



        btnGame.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                gameMode = true;

                btnRandom.setVisibility(View.VISIBLE);
                btnRepeat.setVisibility(View.VISIBLE);


                resetStates();

                adapter.notifyDataSetChanged();


                playRandomLetter();


            }

        });





        btnCase.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                isUpperCase = !isUpperCase;


                if(isUpperCase){

                    btnCase.setText("حروف صغيرة");

                }else{

                    btnCase.setText("حروف كبيرة");

                }

                
                adapter.setUpperCase(isUpperCase);
                adapter.notifyDataSetChanged();


            }

        });




        btnRandom.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                playRandomLetter();

            }

        });



        btnRepeat.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                if(!currentLetter.equals("")){

                    speak(currentLetter);

                }

            }

        });



    }





    private void resetStates(){

        for(int i=0;i<states.length;i++){

            states[i]=-1;

        }

    }






    private void checkAnswer(
            String letter,
            int position
    ){


        if(currentLetter.equals("")){

            Toast.makeText(
                    this,
                    "اضغط نطق حرف أولا",
                    Toast.LENGTH_SHORT
            ).show();

            return;

        }



        if(letter.equals(currentLetter)){


            states[position]=1;

            adapter.notifyDataSetChanged();


            speakText("Correct");



            gridLetters.postDelayed(
                    new Runnable(){

                        @Override
                        public void run(){

                            playRandomLetter();

                        }

                    },
                    1200
            );



        }else{


            states[position]=2;

            adapter.notifyDataSetChanged();


            speakText("Wrong");



            final int clickedPosition = position;

			gridLetters.postDelayed(
				new Runnable(){

					@Override
					public void run(){

						states[clickedPosition] = -1;

						adapter.notifyDataSetChanged();

					}

				},
				700
			);


        }


    }







    private void playRandomLetter(){


        Random random = new Random();


        String newLetter;


        do{

            newLetter =
                    alphabet[random.nextInt(
                            alphabet.length
                    )];


        }while(newLetter.equals(currentLetter));



        currentLetter = newLetter;



        resetStates();


        adapter.notifyDataSetChanged();



        speak(currentLetter);


    }







    private void speak(String text){


        if(!ttsReady)
            return;



        String value=text;


        if(isUpperCase){

            value=text.toUpperCase();

        }else{

            value=text.toLowerCase();

        }



        tts.speak(
                value,
                TextToSpeech.QUEUE_FLUSH,
                null
        );


    }






    private void speakText(String text){


        if(ttsReady){

            tts.speak(
                    text,
                    TextToSpeech.QUEUE_FLUSH,
                    null
            );

        }

    }







    @Override
    public void onInit(int status){


        if(status==TextToSpeech.SUCCESS){


            int result =
                    tts.setLanguage(
                            Locale.US
                    );



            if(result != TextToSpeech.LANG_MISSING_DATA
                    &&
                    result != TextToSpeech.LANG_NOT_SUPPORTED){


                ttsReady=true;


            }


        }


    }






    public boolean isUpperCase(){

        return isUpperCase;

    }







    @Override
    protected void onDestroy(){


        if(tts!=null){

            tts.stop();

            tts.shutdown();

        }


        super.onDestroy();


    }



}
