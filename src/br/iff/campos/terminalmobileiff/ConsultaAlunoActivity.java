package br.iff.campos.terminalmobileiff;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ConsultaAlunoActivity extends Activity {

	
	private EditText matricula;
	private Button bntPesquisa;
	private Button bntVoltar;
	private TextView txvmatricula;
	private TextView txvnome;
	private TextView txvcurso;
	private TextView txvperiodo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consulta_aluno);

		matricula = (EditText) findViewById(R.conAluno.edtxMatricula);
		bntPesquisa = (Button) findViewById(R.conAluno.bntpesquisar);
		bntVoltar = (Button) findViewById(R.conAluno.bntvoltar);

		txvmatricula = (TextView) findViewById(R.conAluno.txvMatricula);
		txvnome = (TextView) findViewById(R.conAluno.txvNome);
		txvcurso = (TextView) findViewById(R.conAluno.txvCurso);
		txvperiodo = (TextView) findViewById(R.conAluno.txvPeriodo);

		bntVoltar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.consulta_aluno, menu);
		return true;
	}

	public void pesquisarAluno(View v) {

		String filtro = this.matricula.getText().toString();

		new ConsultaAlunoTask().execute(filtro + ".json");
	}

	private class ConsultaAlunoTask extends AsyncTask<String, Void, String[]> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(ConsultaAlunoActivity.this);
			dialog.show();
		}

		@Override
		protected void onPostExecute(String[] result) {
			if (result != null) {
				txvmatricula.setText(result[0]);
				txvnome.setText(result[1]);
				txvcurso.setText(result[1].substring(5, result[1].indexOf(" ")));
				txvperiodo.setText("1o.");

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
				String modelo = "alunos/" + filtro;

				String conteudo = HTTPUtils.acessar(modelo);

				JSONObject aluno = new JSONObject(conteudo);

				String[] dadosAlunos = new String[2];

				dadosAlunos[0] = aluno.getString("matricula");
				dadosAlunos[1] = aluno.getString("nome");

				return dadosAlunos;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

	}

}
