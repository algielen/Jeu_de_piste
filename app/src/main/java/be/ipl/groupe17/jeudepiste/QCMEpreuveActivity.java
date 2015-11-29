package be.ipl.groupe17.jeudepiste;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class QCMEpreuveActivity extends EpreuveActivity {
    public static final int REQUEST_QCM = 3;
    private ArrayList<String> choices;
    private ListView listview;
    private int correct;
    private int selected;

    public void submit(View view) {
        if (selected == correct) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
        else {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qcmepreuve);
        listview = (ListView) findViewById(R.id.listView_qcm);
        choices = new ArrayList<>();
        loadFromXML();
        ArrayList<String> arrayList = new ArrayList<>(choices);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.epreuve_qcm_item, arrayList);

        listview.setAdapter(arrayAdapter);
        listview.setItemsCanFocus(false);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView textView = (CheckedTextView) view;
                selected = position;
            }
        });
    }

    public void loadFromXML() {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xPath = factory.newXPath();
        NodeList nl = null;
        try {
            InputStream is = getResources().openRawResource(R.raw.game);

            String query = "//question[@num=\"" + num + "\"]/answer";
            nl = (NodeList) xPath.evaluate(query, new InputSource(is), XPathConstants.NODESET);
            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);
                String choice = xPath.evaluate(".", node);
                choices.add(choice);
                String isCorrect = xPath.evaluate("@true", node);
                if (isCorrect.equals("true")) {
                    this.correct = i;
                }
            }
            is.close();


        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
