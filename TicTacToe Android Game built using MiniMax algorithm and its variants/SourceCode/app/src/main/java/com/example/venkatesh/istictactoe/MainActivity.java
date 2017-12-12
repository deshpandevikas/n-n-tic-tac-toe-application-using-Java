package com.example.venkatesh.istictactoe;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Runnable{

    int idcounter=12589;
    public static int size=3;
    String playersymbol = "O";
    String compsymbol = "X";

    int algochooser=1;

    ProgressDialog progress;

    Handler handler;

    static int countnodes=0;

    NodeAlphaBetaWithDepth mygame1;
    NodeAlphaBetaNoDepth mygame2;
    NodeMiniMaxWithDepth mygame3;
    NodeMiniMaxNoDepth mygame4;
    NodeAlphaBetaWithDepth3 mygame5;

    static Context maincontext;

    Button btn[][];

    public static enum Seed {Cross, Round, Empty}

    public static Seed myseed, oppseed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        maincontext = getApplicationContext();


        progress = new ProgressDialog(this);
        progress.setTitle("Please Wait!!");
        progress.setMessage("Computer is playing its turn");
        progress.setCancelable(true);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String algoType = pref.getString("algoPreference","");
        String matrixSize = pref.getString("matrixPreference","");
        String symboltype = pref.getString("symbolPreference","");

        int custommatrxsize = 0;
        try{
            custommatrxsize = Integer.parseInt(pref.getString("custommatrixsize",""));

            if(custommatrxsize<3)
                custommatrxsize=3;
        }catch (Exception e){
            custommatrxsize = 3;
        }

        size = custommatrxsize;


        if(symboltype.equals("O"))
        {
            playersymbol="O";
            compsymbol="X";
        }else if (symboltype.equals("X"))
        {
            playersymbol="X";
            compsymbol="O";
        }


        LinearLayout root = (LinearLayout) findViewById(R.id.root);
        root.setWeightSum(size);


        if(algoType.equals("AD"))
        {
            algochooser=1;
            mygame1 = new NodeAlphaBetaWithDepth();
        }
        else if(algoType.equals("AN"))
        {
            algochooser=2;
            mygame2 = new NodeAlphaBetaNoDepth();
        }
        else if(algoType.equals("MD"))
        {
            algochooser=3;
            mygame3 = new NodeMiniMaxWithDepth();
        }
        else if(algoType.equals("MN"))
        {
            algochooser=4;
            mygame4 = new NodeMiniMaxNoDepth();
        }
        else if(algoType.equals("ADD"))
        {
            algochooser=5;
            mygame5 = new NodeAlphaBetaWithDepth3();
        }

        int duplicateidkeeper=idcounter;

        for(int i=0;i<size;i++)
        {
            LinearLayout eachrow = new LinearLayout(this);
            eachrow.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1.0f);
            eachrow.setLayoutParams(params);
            eachrow.setWeightSum(size);

            for(int j=0;j<size;j++)
            {
                Button eachButton = new Button(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1.0f);
                eachButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 990/(size*size));
                eachButton.setId(duplicateidkeeper++);
                eachButton.setOnClickListener(this);
                eachButton.setTag((i*10)+j);
                eachrow.addView(eachButton,lp);
            }

            root.addView(eachrow);

        }

        initializewinmatrix();

        btn = new Button[MainActivity.size][MainActivity.size];
        duplicateidkeeper=idcounter;
        for (int i=0;i<size;i++)
        {
            for (int j=0;j<size;j++)
            {
                btn[i][j] = (Button) findViewById(duplicateidkeeper++);
            }
        }


        final String playfirst = pref.getString("playfirstPreference","");

        handler = new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                switch(msg.what) {
                    case 1:
                        btn[msg.getData().getInt("row")][msg.getData().getInt("col")].setText(compsymbol);
                        Log.d("computernodes ",MainActivity.countnodes+"");
                        btn[msg.getData().getInt("row")][msg.getData().getInt("col")].setEnabled(false);
                        progress.dismiss();
                        break;


                    case 2:progress.dismiss();
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Game Over")
                                .setMessage(msg.getData().getString("tempwinner"))
                                .setCancelable(false)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton("Play Again?", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                        finish();
                                        startActivity(i);

                                    }})
                                .setNegativeButton("Exit", new DialogInterface.OnClickListener(){

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        finish();

                                    }}).show();
                        break;

                }
                return false;
            }
        });


        if(playfirst.equals("computer"))
        {
            progress.show();
            Thread t1=new Thread(this);
            t1.start();


        }


    }

    //assigns the symbols to the user and the computer

    public void initializewinmatrix()
    {
        myseed=Seed.Cross;
        oppseed=Seed.Round;
    }


    //Places the user symbol on the location of the grid chosen by the user
    @Override
    public void onClick(View v) {

        Button temp = (Button) findViewById(v.getId());

        temp.setText(playersymbol);
        temp.setEnabled(false);

        int temprowcol = (Integer) v.getTag();

        int row = temprowcol/10;

        int col = temprowcol%10;

        if(algochooser==1)
        {
            mygame1.place(row,col,oppseed);
        }else if(algochooser==2)
        {
            mygame2.place(row,col,oppseed);

        }else if(algochooser==3)
        {
            mygame3.place(row,col,oppseed);

        }else if(algochooser==4)
        {
            mygame4.place(row,col,oppseed);

        }else if(algochooser==5)
        {
            mygame5.place(row,col,oppseed);

        }


        progress.show();
        Thread t1=new Thread(this);
        t1.start();

    }

    //Calls the appropriate functions based on the user’s algorithm selection to determine the computer’s next move

    void playCompTurn()
    {

        MainActivity.countnodes=0;


        int rowcol[]=new int[3];

        if(algochooser==1)
        {
            rowcol = mygame1.getCompNextMove();
        }else if(algochooser==2)
        {
            rowcol = mygame2.getCompNextMove();

        }else if(algochooser==3)
        {
            rowcol = mygame3.getCompNextMove();

        }else if(algochooser==4)
        {
            rowcol = mygame4.getCompNextMove();
        }else if(algochooser==5)
        {
            rowcol = mygame5.getCompNextMove();
        }


        if(rowcol[0]==rowcol[1] && rowcol[0]==-1)
        {


            String tempwinner;
            if(rowcol[2]==1)
                tempwinner="Computer won the game";
            else if(rowcol[2]==2)
                tempwinner="You won the game";
            else
                tempwinner="Game Drawn";


            Message msg = new Message();
            msg.what = 2;
            Bundle data = new Bundle();
            data.putString("tempwinner",tempwinner);
            msg.setData(data);
            handler.sendMessage(msg);


        }
        else {

            int tempresult=0;

            if(algochooser==1)
            {
                mygame1.place(rowcol[0],rowcol[1],myseed);
                tempresult = mygame1.checkend();

            }else if(algochooser==2)
            {
                mygame2.place(rowcol[0],rowcol[1],myseed);
                tempresult = mygame2.checkend();

            }else if(algochooser==3)
            {
                mygame3.place(rowcol[0],rowcol[1],myseed);
                tempresult = mygame3.checkend();

            }else if(algochooser==4)
            {
                mygame4.place(rowcol[0],rowcol[1],myseed);
                tempresult = mygame4.checkend();
            }else if(algochooser==5)
            {
                mygame5.place(rowcol[0],rowcol[1],myseed);
                tempresult = mygame5.checkend();
            }


            Message msg = new Message();
            msg.what = 1;
            Bundle data = new Bundle();
            data.putInt("row",rowcol[0]);
            data.putInt("col",rowcol[1]);
            msg.setData(data);
            handler.sendMessage(msg);


            if(tempresult>0)
            {
                String tempwinner;
                if(tempresult==1)
                    tempwinner="Computer won the game";
                else if(tempresult==2)
                    tempwinner="You won the game";
                else
                    tempwinner="Game Drawn";


                Message msg1 = new Message();
                msg1.what = 2;
                Bundle data1 = new Bundle();
                data1.putString("tempwinner",tempwinner);
                msg1.setData(data1);
                handler.sendMessage(msg1);



            }
        }


    }

    @Override
    public void run() {

        playCompTurn();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public MenuInflater getMenuInflater() {


        return super.getMenuInflater();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        try{
            int id = item.getItemId();

            if(id == R.id.settings){

                Intent i = new Intent(this, SettingsPreference.class);
                finish();
                startActivity(i);
            }
        }catch (Exception e)
        {
            Log.d("debug2",e.getMessage());
        }

        return super.onOptionsItemSelected(item);
    }
}
