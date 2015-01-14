package br.usjt.cursoandroid.androidsqlitedatabase;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.usjt.cursoandroid.androidsqlitedatabase.dao.ClienteDAO;
import br.usjt.cursoandroid.androidsqlitedatabase.entity.Cliente;


public class ClienteDetalhesActivity extends ActionBarActivity implements android.view.View.OnClickListener{

    Button btnSave ,  btnDelete;
    Button btnClose;
    EditText editTextName;
    EditText editTextEmail;
    EditText editTextCpf;
    EditText editTextTelefone;

    private int _Cliente_Id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_detalhes);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextCpf = (EditText) findViewById(R.id.editTextCpf);
        editTextTelefone = (EditText) findViewById(R.id.editTextTelefone);

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);


        _Cliente_Id =0;
        Intent intent = getIntent();
        _Cliente_Id =intent.getIntExtra("cliente_Id", 0);
        ClienteDAO clienteDAO = new ClienteDAO(this);
        Cliente cliente;
        cliente = clienteDAO.getClienteById(_Cliente_Id);

        editTextCpf.setText(cliente.getCpf());
        editTextName.setText(cliente.getNome());
        editTextEmail.setText(cliente.getEmail());
        editTextTelefone.setText(cliente.getTelefone());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cliente_detalhes, menu);
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

    public void onClick(View view) {
        if (view == findViewById(R.id.btnSave)){
            ClienteDAO clienteDAO = new ClienteDAO(this);
            Cliente cliente = new Cliente();
            cliente.setCpf(editTextCpf.getText().toString());
            cliente.setEmail(editTextEmail.getText().toString());
            cliente.setNome(editTextName.getText().toString());
            cliente.setTelefone(editTextTelefone.getText().toString());
            cliente.setId(_Cliente_Id);

            if (_Cliente_Id==0){
                _Cliente_Id = clienteDAO.insert(cliente);

                Toast.makeText(this, "Novo Cliente Incluido", Toast.LENGTH_SHORT).show();
            }else{

                clienteDAO.update(cliente);
                Toast.makeText(this,"Cliente Atualizado",Toast.LENGTH_SHORT).show();
            }
        }else if (view== findViewById(R.id.btnDelete)){
            ClienteDAO clienteDAO = new ClienteDAO(this);
            clienteDAO.delete(_Cliente_Id);
            Toast.makeText(this, "Cliente Deletado", Toast.LENGTH_SHORT);
            finish();
        }else if (view== findViewById(R.id.btnClose)){
            finish();
        }


    }

}
