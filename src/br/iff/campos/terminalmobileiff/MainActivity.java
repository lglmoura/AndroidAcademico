package br.iff.campos.terminalmobileiff;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	public static final String EXTRA_NOME_USUARIO = "academico.EXTRA_NOME_USUARIO";
	public static final String ID_USUARIO = "helloandroid.ID_USUARIO";

	// As duas novas constantes
	public static final String ACAO_EXIBIR_MENU_PRINCIPAL = "academico.ACAO_EXIBIR_MENU_PRINCIPAL";
	public static final String CATEGORIA_MENU_PRINCIPAL = "academico.CATEGORIA_MENU_PRINCIPAL";

	private String idProfessor = "1";
	private Button bntConsultaAluno;
	private Button bntConsultaNotasFreqquencia;
	
	private Button bntSair;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final TextView nomeProfessor = (TextView) findViewById(R.id.txvNameProfesso);

		bntConsultaAluno = (Button) findViewById(R.main.btnConsultaAluno);
		bntConsultaNotasFreqquencia = (Button) findViewById(R.main.btnConsultaNotasFrequencia);
		//bntCadastrarNotasFreqquencia = (Button) findViewById(R.id.bntCadNotasFrequencia);
		bntSair = (Button) findViewById(R.id.bntSair);

		Intent intent = getIntent();
		if (intent.hasExtra(EXTRA_NOME_USUARIO)) {

			nomeProfessor.setText(intent.getStringExtra(EXTRA_NOME_USUARIO));
			idProfessor = intent.getStringExtra(ID_USUARIO);
		} else {
			nomeProfessor.setText("O nome do usuário não foi informado");
		}

		/*
		 * if (intent.hasExtra("params")) { Bundle params =
		 * intent.getBundleExtra("params");
		 * nomeProfessor2.setText(params.getString("professor")); } else {
		 * nomeProfessor2.setText("O nome do usuário não foi informado"); }
		 */

		bntConsultaAluno.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				consultaAluno();

			}
		});
		
		bntConsultaNotasFreqquencia.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				consultaNotasFrequencia();

			}
		});

		bntSair.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void consultaAluno() {

		Intent i = new Intent();
		i.setClass(this, ConsultaAlunoActivity.class);
		startActivityForResult(i, 0);

	}
	
	private void consultaNotasFrequencia() {

		/*Intent i = new Intent();
		i.setClass(this, ConsultaNotaFrequenciaActivity.class);
		startActivityForResult(i, 1);
		*/
		
		Intent intent = new Intent(
				ConsultaNotaFrequenciaActivity.ACAO_EXIBIR_CONSULTAR_NOTAS);
		intent.addCategory(ConsultaNotaFrequenciaActivity.CATEGORIA_CONSULTAR_NOTAS);

		
		intent.putExtra(ConsultaNotaFrequenciaActivity.ID_USUARIO, idProfessor);

		startActivity(intent);

	}


}
