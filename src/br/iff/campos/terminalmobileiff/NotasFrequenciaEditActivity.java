package br.iff.campos.terminalmobileiff;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NotasFrequenciaEditActivity extends Activity {

	private Button bntVoltar;
	private Button bntEdit;
	private String id;

	private TextView curso;
	private TextView disciplina;
	private TextView nome;
	private EditText media;
	private EditText falta;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.otas_frequencia_edit);

		curso = (TextView) findViewById(R.editNF.txvNomeCurso);
		disciplina = (TextView) findViewById(R.editNF.txvNomeDisciplina);
		nome = (TextView) findViewById(R.editNF.txvNameAluno);
		media = (EditText) findViewById(R.editNF.nota);
		falta = (EditText) findViewById(R.editNF.frequencia);

		Intent intent = getIntent();

		disciplina.setText(intent.getStringExtra("disciplina"));
		curso.setText(intent.getStringExtra("curso"));
		nome.setText(intent.getStringExtra("nome"));
		media.setText(intent.getStringExtra("media"));
		falta.setText(intent.getStringExtra("falta"));
		id = intent.getStringExtra("id");

		bntEdit = (Button) findViewById(R.editNF.btnEditar);
		bntEdit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				HttpClient httpclient = new DefaultHttpClient();
				String url = "http://192.168.0.100:3000/diarios/" + id;
				System.out.println("=========>>>>>>>>>> " + url);

				HttpPut httpput = new HttpPut(url);

				HashMap<String, String> notaFreq = new HashMap<String, String>();

				// pairs.add(new BasicNameValuePair("diario[id]", id));
               
				notaFreq.put("nota", media.getText().toString());
				notaFreq.put("frequencia", falta.getText().toString());
				HashMap<String, Map> notas = new HashMap<String, Map>();
				notas.put("diario", notaFreq);

				JSONObject JOnotas;
				try {
					JOnotas = getJsonObjectFromMap(notas);

					System.out.println("=========>>>>>>>>>> " + JOnotas.toString());
					
					// passes the results to a string builder/entity
					StringEntity se;

					se = new StringEntity(JOnotas.toString());
					httpput.setEntity(se);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// sets the post request as the resulting string

				// sets a request header so the page receving the request
				// will know what to do with it
				httpput.setHeader("Accept", "application/json");
				httpput.setHeader("Content-type", "application/json");

				// Handles what is returned from the page
				ResponseHandler responseHandler = new BasicResponseHandler();
				try {
					httpclient.execute(httpput, responseHandler);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finish();
			}

			private JSONObject getJsonObjectFromMap(Map params)
					throws JSONException {

				
				Iterator iter = params.entrySet().iterator();

				// Stores JSON
				JSONObject holder = new JSONObject();

				
				while (iter.hasNext()) {
					// gets an entry in the params
					Map.Entry pairs = (Map.Entry) iter.next();

					// creates a key for Map
					String key = (String) pairs.getKey();

					// Create a new map
					Map m = (Map) pairs.getValue();

					// object for storing Json
					JSONObject data = new JSONObject();

					// gets the value
					Iterator iter2 = m.entrySet().iterator();
					while (iter2.hasNext()) {
						Map.Entry pairs2 = (Map.Entry) iter2.next();
						data.put((String) pairs2.getKey(),
								(String) pairs2.getValue());
					}

					// puts email and 'foo@bar.com' together in map
					holder.put(key, data);
				}
				return holder;
			}

		});

		bntVoltar = (Button) findViewById(R.editNF.btnVoltar);
		bntVoltar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				finish();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notas_frequencia_edit, menu);
		return true;
	}

}
