package com.adel.myapplication;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;



public class LetterAdapter extends BaseAdapter {


    private Context context;

    private String[] letters;

    private int[] states;

    private boolean upperCase = false;


    private LayoutInflater inflater;


    private OnLetterClickListener listener;



    public interface OnLetterClickListener {

        void onLetterClick(
                String letter,
                int position
        );

    }




    public LetterAdapter(
            Context context,
            String[] letters,
            int[] states,
            OnLetterClickListener listener
    ){


        this.context = context;

        this.letters = letters;

        this.states = states;

        this.listener = listener;


        inflater =
                LayoutInflater.from(context);


    }






    @Override
    public int getCount(){

        return letters.length;

    }





    @Override
    public Object getItem(int position){

        return letters[position];

    }





    @Override
    public long getItemId(int position){

        return position;

    }







    @Override
    public View getView(
            final int position,
            View convertView,
            ViewGroup parent
    ){



        ViewHolder holder;



        if(convertView == null){


            convertView =
                    inflater.inflate(
                            R.layout.item_letter,
                            parent,
                            false
                    );



            holder = new ViewHolder();


            holder.card =
                    convertView.findViewById(
                            R.id.card
                    );


            holder.text =
                    convertView.findViewById(
                            R.id.txtLetter
                    );



            convertView.setTag(holder);



        }else{


            holder =
                    (ViewHolder)
                            convertView.getTag();


        }





        String letter =
                letters[position];



        if(upperCase){

            holder.text.setText(
                    letter.toUpperCase()
            );


        }else{


            holder.text.setText(
                    letter.toLowerCase()
            );


        }







        /*
          تغيير لون الكارت
          حسب حالة الإجابة
        */


        if(states[position]==1){


            // صحيح

            holder.card.setBackgroundColor(
                    Color.parseColor("#4CAF50")
            );


            holder.text.setTextColor(
                    Color.WHITE
            );



        }else if(states[position]==2){


            // خطأ


            holder.card.setBackgroundColor(
                    Color.parseColor("#F44336")
            );


            holder.text.setTextColor(
                    Color.WHITE
            );



        }else{


            // الحالة العادية


            holder.card.setBackgroundColor(
                    Color.WHITE
            );


            holder.text.setTextColor(
                    Color.parseColor("#222222")
            );


        }







        convertView.setOnClickListener(
                new View.OnClickListener(){


                    @Override
                    public void onClick(View v){


                        if(listener!=null){


                            listener.onLetterClick(
                                    letters[position],
                                    position
                            );


                        }


                    }


                }
        );





        return convertView;


    }









    public void setUpperCase(boolean value){


        upperCase=value;


        notifyDataSetChanged();


    }






    static class ViewHolder{


        LinearLayout card;

        TextView text;


    }



}