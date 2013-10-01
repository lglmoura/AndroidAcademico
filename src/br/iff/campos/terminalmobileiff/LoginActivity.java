package br.iff.campos.terminalmobileiff;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private String senhabd = "123456";
	private String nome = "Luiz Gustavo";
	private String idu   = "1";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		final EditText matricula = (EditText) findViewById(R.id.etMatricula);
		final EditText senha = (EditText) findViewById(R.id.etSenha);

		Button entrar = (Button) findViewById(R.id.bEntrar);
		Button limpa = (Button) findViewById(R.id.bLimpar);

		entrar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String filtro = matricula.getText().toString();
				if (!filtro.equalsIgnoreCase("")) {

					//new LoginTask().execute(filtro + ".json");

					new LoginTask().execute(filtro + "/matricula.json");
					
					if (senha.getText().toString().equals(senhabd)) {
						

						Intent intent = new Intent(
								MainActivity.ACAO_EXIBIR_MENU_PRINCIPAL);
						intent.addCategory(MainActivity.CATEGORIA_MENU_PRINCIPAL);

						intent.putExtra(MainActivity.EXTRA_NOME_USUARIO, nome);
						intent.putExtra(MainActivity.ID_USUARIO, idu);

						startActivity(intent);
					} else {
						Toast.makeText(LoginActivity.this, "Errado",
								Toast.LENGTH_SHORT).show();

					}

				} else {
					Toast.makeText(LoginActivity.this, "matricula Vazia",
							Toast.LENGTH_SHORT).show();

				}
			}
		});

		limpa.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				matricula.setText("");
				senha.setText("");

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	private class LoginTask extends AsyncTask<String, Void, String[]> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(LoginActivity.this);
			dialog.show();
		}

		@Override
		protected void onPostExecute(String[] result) {
			if (result != null) {
				senhabd = result[2];
				nome = result[1];
				idu  = result[3];

			}

			dialog.dismiss();
		}

		@Override
		protected String[] doInBackground(String... params) {
			// String[] dadosAlunos = new String[100];
			try {
				String filtro = params[0];
				if (TextUtils.isEmpty(filtro)) {
					return null;
				}
				String modelo = "professors/" + filtro;
				

				String conteudo = HTTPUtils.acessar(modelo);
				String[] dadosProfessor = new String[4];
				if (!conteudo.equalsIgnoreCase("")) {
					
				
					JSONArray resultados = new JSONArray(conteudo);
					System.out.println("============>>>>>>>>>    "+resultados.length());
					JSONObject professor = resultados.getJSONObject(0);

					dadosProfessor[0] = professor.getString("matricula");
					dadosProfessor[1] = professor.getString("nome");
					dadosProfessor[2] = professor.getString("senha");
					dadosProfessor[3] = professor.getString("id");
					
				}
				return dadosProfessor;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

	}

}
