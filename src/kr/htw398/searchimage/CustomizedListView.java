package kr.htw398.searchimage;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
 
public class CustomizedListView extends Activity {
	
    // All static variables
    public  String URL; 
    // XML node keys
    static final String KEY_ITEM = "item"; // parent node
    static final String KEY_ID = "id";
    static final String KEY_TITLE = "title";
    static final String KEY_ARTIST = "artist";
    static final String KEY_LINK = "link";
    static final String KEY_DURATION = "duration";
    static final String KEY_THUMNAIL = "thumbnail";
    static final String KEY_SEARCH = "search";
 
    ListView list;
    LazyAdapter adapter;
    ArrayList<HashMap<String, String>> songsList;
    EditText eEdit;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String searchstring = "수지";    
        displayxml(searchstring);
        
        Button button = (Button) findViewById(R.id.button1);
        eEdit = (EditText) findViewById(R.id.editText1);
             
        button.setOnClickListener(new View.OnClickListener() {
        	
        			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(),, Toast.LENGTH_LONG).show();
				if (eEdit.getText().toString() !=null)
					displayxml( eEdit.getText().toString());
				
			}
		});
        
    }
	public void displayxml(String searchstring) {
		URL = "http://openapi.naver.com/search?key=59dc99d9a80cde398258872d71263e5d&query=" + searchstring +"&target=image&start=1&display=10&sort=sim";
 
        songsList = new ArrayList<HashMap<String, String>>();
 
        XMLParser parser = new XMLParser();
        String xml = parser.getXmlFromUrl(URL); // getting XML from URL
        Document doc = parser.getDomElement(xml); // getting DOM element
 
        NodeList nl = doc.getElementsByTagName(KEY_ITEM);
        // looping through all song nodes <song>
        for (int i = 0; i < nl.getLength(); i++) {
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            Element e = (Element) nl.item(i);
            // adding each child node to HashMap key => value
            map.put(KEY_ID, parser.getValue(e, KEY_ID));
            map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
            map.put(KEY_ARTIST, parser.getValue(e, KEY_ARTIST));
            map.put(KEY_LINK, parser.getValue(e, KEY_LINK));
            map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
            map.put(KEY_THUMNAIL, parser.getValue(e, KEY_THUMNAIL));
 
            // adding HashList to ArrayList
            songsList.add(map);
        }
 
        list=(ListView)findViewById(R.id.list);
 
   
        adapter=new LazyAdapter(this, songsList);
        list.setAdapter(adapter);
 
        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
            //	Toast.makeText(parent.getContext(), "Success" + "position" + position + "id=" + id , Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), DetailEdit.class);
                i.putExtra( KEY_THUMNAIL, songsList.get(position).get(KEY_THUMNAIL).toString());
                i.putExtra( KEY_TITLE, songsList.get(position).get(KEY_TITLE).toString());
                i.putExtra( KEY_LINK, songsList.get(position).get(KEY_LINK).toString());
                startActivityForResult(i,1);
                overridePendingTransition(R.anim.leftout, R.anim.leftin);
                //finish();
            }
        });
	}
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	if (resultCode==RESULT_OK)
    	{
    		if(requestCode==1)
    		{
    		
    			String search2 = data.getStringExtra(KEY_SEARCH);
    			if (search2 !=null){
    				Toast.makeText(getApplicationContext(), search2, Toast.LENGTH_SHORT).show();
    				displayxml(search2);

    			}
    				    			
    		}
    	}
		
	}
   
    
}