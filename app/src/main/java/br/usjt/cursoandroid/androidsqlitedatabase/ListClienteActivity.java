package br.usjt.cursoandroid.androidsqlitedatabase;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import br.usjt.cursoandroid.androidsqlitedatabase.dao.ClienteDAO;


public class ListClienteActivity extends ListActivity implements android.view.View.OnClickListener{

    Button btnAdd,btnGetAll;
    TextView cliente_Id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view== findViewById(R.id.btnAdd)){

            Intent intent = new Intent(this,ClienteDetalhesActivity.class);
            intent.putExtra("cliente_Id",0);
            startActivity(intent);

        }else {

            ClienteDAO clienteDAO = new ClienteDAO(this);

            ArrayList<HashMap<String, String>> clienteList =  clienteDAO.getClienteList();
            if(clienteList.size()!=0) {
                ListView lv = getListView();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                        cliente_Id = (TextView) view.findViewById(R.id.id_cliente);
                        String clienteId = cliente_Id.getText().toString();
                        Intent objIndent = new Intent(getApplicationContext(),ClienteDetalhesActivity.class);
                        objIndent.putExtra("cliente_Id", Integer.parseInt( clienteId));
                        startActivity(objIndent);
                    }
                });
                ListAdapter adapter = new SimpleAdapter( ListClienteActivity.this,clienteList, R.layout.view_cliente_registro, new String[] { "id","nome"}, new int[] {R.id.id_cliente, R.id.nome_cliente});
                setListAdapter(adapter);
            }else{
                Toast.makeText(this,"Sem Clientes!", Toast.LENGTH_SHORT).show();
            }

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}