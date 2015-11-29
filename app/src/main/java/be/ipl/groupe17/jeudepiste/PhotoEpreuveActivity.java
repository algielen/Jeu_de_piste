package be.ipl.groupe17.jeudepiste;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.io.IOException;

public class PhotoEpreuveActivity extends EpreuveActivity {

    public static final int REQUEST_PHOTO_EPREUVE = 2;

    //TODO : récupérer la description

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_epreuve2);
    }

    /*
     * Gestion de l'appareil photo
     */
    public void takeAPicture(View view) {
        // epreuveEnCours = true;
        String photoPath = null;
        try {
            File image = Camera.createImageFile();
            model.setCurrentPhoto(image);
            Camera.dispatchTakePictureIntent(this, image);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //retour de la prise de photo
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Camera.REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                //TODO : vérifier les données EXIF pour la position ?
                Camera.galleryAddPic(model.getCurrentPhoto(), this);

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);

                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        super.onBackPressed();
    }
}
