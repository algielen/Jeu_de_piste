package be.ipl.groupe17.jeudepiste;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public abstract class EpreuveActivity extends AppCompatActivity {
    protected Model model;
    protected String num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = Model.getInstance();

        //on récupère l'épreuve
        Intent intent = getIntent();
        num = intent.getStringExtra("num");
    }
}
