package com.example.mango.mytest;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
private CircleProgressView circleProgressView;
private EditText editText1;
private EditText editText2;
private EditText editText3;
private EditText editText4;
private Button buttonStart;
private Button buttonEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                circleProgressView.ShowCircleProgressView( (float)Integer.parseInt(editText1.getText().toString()),(float)Integer.parseInt(editText2.getText().toString()),(float)Integer.parseInt(editText3.getText().toString()),Integer.parseInt(editText4.getText().toString()));
                circleProgressView.setProgressListener(new CircleProgressView.ProgressListener() {
                    @Override
                    public void setProgress(int currentProgress, int allProgress) {
                        Log.d("进度回调", "setProgress: "+currentProgress);
                    }
                });
            }
        });
    }


    public void findViewById(){
        circleProgressView=(CircleProgressView)findViewById(R.id.circleProgressView);
        editText1=(EditText)findViewById(R.id.edit1);
        editText2=(EditText)findViewById(R.id.edit2);
        editText3=(EditText)findViewById(R.id.edit3);
        editText4=(EditText)findViewById(R.id.edit4);
        buttonStart=(Button)findViewById(R.id.start);
        buttonEnd=(Button)findViewById(R.id.end);
    }


}
