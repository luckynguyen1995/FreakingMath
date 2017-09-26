package com.and006.freakingmath;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class activity_play extends AppCompatActivity {
    Button btnRight,btnWrong;
    TextView txtMath,txtResult,txtScore,txtTest,txtTest2;
    int resultx,result,n1,n2,math,score=0;
    boolean flag;
    ProgressBar proBar;
    int status = 1000;
    int speedUp = 10;
    Handler progressBarHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        setControls();
        createMath(score);
        proBar.setProgress(1000);
        proBar.setMax(1000);
        setEvents();


    }

    public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            do {
                SystemClock.sleep(10);
                publishProgress(status);
                status -= speedUp;
            } while (status > 0);

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int i = values[0];
            proBar.setProgress(i);

        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(activity_play.this, "Hết giờ", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled() {

            super.onCancelled();

        }
    }
//    Thread newThread(){
//       return new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (status > 0) {
//                    status = updateStatus();
//                    try {
//                        Thread.sleep(100);
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    progressBarHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            proBar.setProgress(status);
//                        }
//                    });
//                }
//                if (status == 0) {
//
//                    try {
//                        new Thread() {
//                            public void run() {
//                                Message msg = new Message();
//                                msg.arg1 = 1;
//                                handler.sendMessage(msg);
//                            }
//                        }.start();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    status = 100;
//                }
//            }
//        });
//
//    }
//

    Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if(msg.arg1==1)
            {
                //Open Dialog ở đây
                Toast.makeText(activity_play.this, "Hết giờ", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    });
    public int updateStatus() {
        if(status>0){
            status -=1;
        }
        return status;
    }

    private void setEvents() {
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(result==resultx){
                    //Đúng thì replace
                    score++;
                    txtScore.setText(score+"");
                    createMath(score);
                    status = 1000;
//                    Thread t=newThread();
//                    t.start();
                    new MyAsyncTask().execute();
                }else{
                    //Sai end Game
                    score--;
                    txtScore.setText(score+"");
                    createMath(score);

                }
            }
        });
        btnWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(result!=resultx){
                    score++;
                    txtScore.setText(score+"");
                    createMath(score);
                    status = 1000;
                    new MyAsyncTask().execute();
                }else{
                    score--;
                    txtScore.setText(score+"");
                    createMath(score);
                }
            }
        });
    }

    private void setControls() {
        btnRight= (Button) findViewById(R.id.btnRight);
        btnWrong= (Button) findViewById(R.id.btnWrong);
        txtMath= (TextView) findViewById(R.id.txtMath);
        txtResult= (TextView) findViewById(R.id.txtResult);
        txtScore= (TextView) findViewById(R.id.txtScore);
        txtTest= (TextView) findViewById(R.id.txtTest);
        txtTest2= (TextView) findViewById(R.id.txtTest2);
        proBar= (ProgressBar) findViewById(R.id.progressBar);
    }



    private void createMath(int score){
        math=2;
        String strMath="";
        int rdMath=new Random().nextInt(math)+1;
        //Random số
        if(score<=5){
            n1=new Random().nextInt(5)+1;
            n2=new Random().nextInt(5)+1;
        }
        else if(score<=10){
            speedUp++;
            n1=new Random().nextInt(10)+1;
            n2=new Random().nextInt(10)+1;

        }
        else if(score<=15){
            speedUp++;
            n1=new Random().nextInt(10+score*5)+1;
            math=3;
            rdMath=new Random().nextInt(math)+1;
            if(rdMath==3) {
                n2 = new Random().nextInt(10) + 1;
            }else {
                n2=new Random().nextInt(10+score*5)+1;
            }

        }
        else if(score<=30){
            speedUp++;
            n1=new Random().nextInt(100)+1;

            math=4;
            rdMath=new Random().nextInt(math)+1;
            if(rdMath==3||rdMath==4) {
                n2 = new Random().nextInt(20) + 1;
            }else {
                n2=new Random().nextInt(100)+1;
            }
        }

        //Khai báo biến

        flag=new Random().nextBoolean();



        //Random phép toán
        if(rdMath==1){
            resultx=n1+n2;
            strMath=n1+" + "+n2;
            txtMath.setText(strMath);
            if(flag){
                txtResult.setText(resultx+"");
                result=resultx;
            }
            else{
                int rd=new Random().nextInt((n1+n2)-(n1+n2/2))+1;
                if(rd==0)rd=1;
                result=n1+n2+rd;
                txtResult.setText(n1+n2+rd+"");
            }

            txtTest.setText(resultx + "");
            txtTest2.setText(result + "   " + flag);
        }
        else if(rdMath==2){
            resultx=Math.abs(n1-n2);
            if(n1>n2){
                strMath=n1+" - "+n2;
            }else{
                strMath=n2+" - "+n1;
            }
            txtMath.setText(strMath);
            if(flag){
                result=resultx;
                txtResult.setText(resultx+"");
            }else{
                if(Math.abs(n1-n2)==0){
                    //strResult=n1+n2+"";
                    result=n1+n2;
                    txtResult.setText(result+"");
                }else {
                    int rd = new Random().nextInt((n1 + n2));
                    if (rd == Math.abs(n1 - n2)) rd = 1;
                    result = rd;
                    //strResult = result + "";
                    txtResult.setText(result+"");
                }
            }
            txtTest.setText(resultx + "");

            txtTest2.setText(result + " " + flag);

        }else if(rdMath==3){
            resultx=n1*n2;
            strMath=n1+" X "+n2;
            txtMath.setText(strMath);
            if(flag){
                result=resultx;
                txtResult.setText(""+resultx);
            }else{
                int rd=new Random().nextInt(n1*n2)-((n1*n2)/2);
                if(rd==0) rd=1;
                result=n1*n2+rd;
                txtResult.setText(result+"");
            }
          //  txtTest.setText(resultx+"");

          //  txtTest2.setText(result+" "+flag);
        }
        else if(rdMath==4){
            int mul=n1*n2;
            strMath=mul+" / "+n1;
            txtMath.setText(strMath);
            resultx=mul/n1;
            if(flag){
                txtResult.setText(""+resultx);
                result=resultx;
            }else{
                int rd=new Random().nextInt(n2)-(n2/2);
                if(rd==0) rd=1;
                result=rd+n2;
                txtResult.setText(result+"");
            }
          //  txtTest.setText(resultx+"");

          //  txtTest2.setText(result+" "+flag);
        }

    }

}
