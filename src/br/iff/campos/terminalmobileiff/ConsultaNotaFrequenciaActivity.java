package br.iff.campos.terminalmobileiff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class ConsultaNotaFrequenciaActivity extends Activity implements
		OnItemClickListener {

	public static final String ID_USUARIO = "academico.ID_USUARIO";

	// As duas novas constantes
	public static final String ACAO_EXIBIR_CONSULTAR_NOTAS = "academico.ACAO_EXIBIR_CONSULTAR_NOTAS";
	public static final String CATEGORIA_CONSULTAR_NOTAS = "academico.CATEGORIA_CONSULTAR_NOTAS";

	private static final int REQUEST_EDIT = 2;
	private Spinner disciplinasp;
	private Spinner cursosp;
	private int pcurso = 0;
	private String ncurso = "";
	private int pdisciplina = 0;
	private String ndisciplina = "";
	private ListView lv;
	private Button bntVoltar;
	private String idProfessor = "1";

	private Hashtable<String, ArrayList<String>> cursoDisciplinas = new Hashtable<String, ArrayList<String>>();
	private Hashtable<String, ArrayList<Diario>> disciplinaNotas = new Hashtable<String, ArrayList<Diario>>();
	private ArrayList<String> aCurso = new ArrayList<String>();

	private OnItemSelectedListener disciplinaListener = new OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> adapter, View view,
				int position, long idDisciplina) {

			pdisciplina = position;
			ndisciplina = adapter.getItemAtPosition(position).toString();

			String[] from = new String[] { "id", "nome", "media", "falta" };
			int[] to = new int[] { R.id.item1, R.id.item2, R.id.item3,
					R.id.item4 };

			SimpleAdapter adapterlv = new SimpleAdapter(
					getApplicationContext(), getAlunos(ndisciplina),
					R.layout.grid_nota_frequencia, from, to);
			lv.setAdapter(adapterlv);

		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

		private List<HashMap<String, String>> getAlunos(String disciplina) {
			List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();

			ArrayList<Diario> auxDiario = disciplinaNotas.get(disciplina);

			for (int i = 0; i < auxDiario.size(); i++) {
				HashMap<String, String> map = new HashMap<String, String>();
				Diario diario = auxDiario.get(i);
				map.put("id", "" + diario.getId());
				map.put("nome", diario.getNome());
				map.put("media", diario.getNota());
				map.put("falta", diario.getFrequencia());
				fillMaps.add(map);
			}
			return fillMaps;
		}
	};

	private OnItemSelectedListener cursoListener = new OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> adapter, View view,
				int position, long idCurso) {

			pcurso = position;
			ncurso = adapter.getItemAtPosition(position).toString();

			ArrayAdapter<String> adapterdisc = new ArrayAdapter<String>(
					getApplicationContext(),
					android.R.layout.simple_spinner_item,
					cursoDisciplinas.get(ncurso));

			disciplinasp.setAdapter(adapterdisc);

		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.consulta_nota_frequencia);
		Intent intent = getIntent();
		if (intent.hasExtra(ID_USUARIO)) {

			idProfessor = intent.getStringExtra(ID_USUARIO);
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TextView textView = (TextView) view;
		HashMap<String, String> map = (HashMap) parent
				.getItemAtPosition(position);

		String mensagem = "Aluno selecionado: " + map.get("nome");
		Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_SHORT)
				.show();

		Intent i = new Intent();
		i.setClass(this, NotasFrequenciaEditActivity.class);

		i.putExtra("curso", ncurso);
		i.putExtra("disciplina", ndisciplina);
		i.putExtra("nome", map.get("nome"));
		i.putExtra("media", map.get("media"));
		i.putExtra("falta", map.get("falta"));
		i.putExtra("id", map.get("id"));

		startActivityForResult(i, REQUEST_EDIT);

		// startActivity(new Intent(this, GastoListActivity.class));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.consulta_nota_frequencia, menu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		ProgressDialog dialog;
		dialog = new ProgressDialog(ConsultaNotaFrequenciaActivity.this);
		dialog.show();

		String[] dadosProfessor = new String[4];
		cursoDisciplinas.clear();
		aCurso.clear();

		try {
			String filtro = idProfessor + "/search.json";

			String modelo = "professors/" + filtro;

			String conteudo = HTTPUtils.acessar(modelo);

			if (!conteudo.equalsIgnoreCase("")) {

				JSONObject professor = new JSONObject(conteudo);

				dadosProfessor[0] = professor.getString("matricula");
				dadosProfessor[1] = professor.getString("nome");
				dadosProfessor[2] = professor.getString("senha");
				dadosProfessor[3] = professor.getString("id");

				JSONArray jaDisciplinas = professor.getJSONArray("disciplinas");

				// String[] aDisciplinas = new String[jaDisciplinas.length()];
				for (int i = 0; i < jaDisciplinas.length(); i++) {

					JSONObject disciplina = jaDisciplinas.getJSONObject(i);
					String nomeDisciplina = disciplina.getString("nome");

					JSONObject curso = disciplina.getJSONObject("curso");
					String nomeCurso = curso.getString("nome");

					if (!cursoDisciplinas.containsKey(nomeCurso)) {
						ArrayList<String> alDisciplinas = new ArrayList<String>();
						alDisciplinas.add(nomeDisciplina);
						cursoDisciplinas.put(nomeCurso, alDisciplinas);
					} else {
						ArrayList<String> alDisciplinas = (ArrayList<String>) cursoDisciplinas
								.get(nomeCurso);
						alDisciplinas.add(nomeDisciplina);

					}

					JSONArray jaDiario = disciplina.getJSONArray("diarios");
					// String[] aDiarios = new String[jaDiario.length()];
					ArrayList<Diario> aDiario = new ArrayList<Diario>();

					for (int d = 0; d < jaDiario.length(); d++) {
						JSONObject joDiario = jaDiario.getJSONObject(d);
						JSONObject joAluno = joDiario.getJSONObject("aluno");

						Diario diario = new Diario();
						diario.setDisciplina(joDiario
								.getString("disciplina_id"));
						diario.setDisciplina(joDiario.getString("aluno_id"));
						diario.setFrequencia(joDiario.getString("frequencia"));
						diario.setNota(joDiario.getString("nota"));
						diario.setId(joDiario.getString("id"));
						diario.setNome(joAluno.getString("nome"));
						aDiario.add(diario);
					}
					disciplinaNotas.put(nomeDisciplina, aDiario);

				}

			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		Set in = cursoDisciplinas.keySet();
		Iterator<String> chaves = in.iterator();

		while (chaves.hasNext()) {
			aCurso.add((String) chaves.next());

		}

		dialog.dismiss();

		ArrayAdapter<String> adapterCurso = new ArrayAdapter<String>(
				getApplicationContext(), android.R.layout.simple_spinner_item,
				aCurso);

		cursosp = (Spinner) findViewById(R.conNF.sp_cursos);
		cursosp.setAdapter(adapterCurso);
		cursosp.setOnItemSelectedListener(cursoListener);
		bntVoltar = (Button) findViewById(R.conNF.btnVoltar);
		bntVoltar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				finish();
			}
		});

		disciplinasp = (Spinner) findViewById(R.conNF.sp_diciplinas);
		disciplinasp.setOnItemSelectedListener(disciplinaListener);

		lv = (ListView) findViewById(R.conNF.listviewnf);
		registerForContextMenu(lv);
		lv.setOnItemClickListener(this);

	}

}
