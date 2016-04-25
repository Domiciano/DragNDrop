package icesi.i2t.dragndrop;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
    TextView cuadro;

    int width, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;



        cuadro = (TextView) findViewById(R.id.cuadro);
        cuadro.setText("W:"+width+" H:"+height);

        cuadro.setOnTouchListener(new View.OnTouchListener() {
            float Xini=0, Yini=0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        cuadro.setBackgroundColor(Color.rgb(0xBB,0x66,0x66));
                        Xini = event.getX();
                        Yini = event.getY();
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        cuadro.setText(event.getX()+","+event.getY());
                        cuadro.setX(cuadro.getX()+event.getX()-Xini);
                        cuadro.setY(cuadro.getY()+event.getY()-Yini);
                        return true;

                    case MotionEvent.ACTION_UP:
                        cuadro.setBackgroundColor(Color.rgb(0xFF,0xAA,0xAA));
                        float Xfinal = cuadro.getX();
                        float Yfinal = cuadro.getY();

                        SharedPreferences preferencias = PreferenceManager
                                .getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferencias.edit();
                        editor.putFloat("X", Xfinal);
                        editor.putFloat("Y", Yfinal);
                        editor.commit();

                        //cuadro.animate().setDuration(500).x(0).y(0);
                        return true;
                }

                return false;


            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferencias =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        float Xrecuperado = preferencias.getFloat("X", 0);
        float Yrecuperado = preferencias.getFloat("Y", 0);

        cuadro.setX(Xrecuperado);
        cuadro.setY(Yrecuperado);
    }


}
