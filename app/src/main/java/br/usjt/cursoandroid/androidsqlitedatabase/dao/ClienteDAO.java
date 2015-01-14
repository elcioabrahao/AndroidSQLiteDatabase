package br.usjt.cursoandroid.androidsqlitedatabase.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import br.usjt.cursoandroid.androidsqlitedatabase.database.DBMS;
import br.usjt.cursoandroid.androidsqlitedatabase.entity.Cliente;

/**
 * Created by Elcio on 14/01/15.
 */
public class ClienteDAO {
    private Context context;

    public ClienteDAO(Context context) {
        this.context=context;
    }

    public int insert(Cliente cliente) {

        //Open connection to write data
        SQLiteDatabase db = DBMS.getInstance(this.context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", cliente.getNome());
        values.put("cpf",cliente.getCpf());
        values.put("telefone", cliente.getTelefone());
        values.put("email", cliente.getEmail());

        // Inserting Row
        long cliente_Id = db.insert("cliente", null, values);
        db.close(); // Closing database connection
        return (int) cliente_Id;
    }

    public void delete(int cliente_Id) {

        SQLiteDatabase db = DBMS.getInstance(this.context).getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete("cliente", "id = ?", new String[] { String.valueOf(cliente_Id) });
        db.close(); // Closing database connection
    }

    public void update(Cliente cliente) {

        SQLiteDatabase db = DBMS.getInstance(this.context).getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nome", cliente.getNome());
        values.put("cpf",cliente.getCpf());
        values.put("telefone", cliente.getTelefone());
        values.put("email", cliente.getEmail());

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update("cliente", values, "id = ?", new String[] { String.valueOf(cliente.getId()) });
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>> getClienteList() {
        //Open connection to read only
        SQLiteDatabase db = DBMS.getInstance(this.context).getWritableDatabase();
        String selectQuery =  "SELECT  " +
                "id," +
                "nome," +
                "cpf," +
                "telefone," +
                "email" +
                " FROM cliente ORDER BY nome";

        ArrayList<HashMap<String, String>> clienteList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> cliente = new HashMap<String, String>();
                cliente.put("id", cursor.getString(cursor.getColumnIndex("id")));
                cliente.put("nome", cursor.getString(cursor.getColumnIndex("nome")));
                clienteList.add(cliente);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return clienteList;

    }

    public Cliente getClienteById(int Id){
        SQLiteDatabase db = DBMS.getInstance(this.context).getWritableDatabase();
        String selectQuery =  "SELECT  " +
                "id," +
                "nome," +
                "cpf," +
                "telefone," +
                "email" +
                " FROM cliente"
                + " WHERE " +
                "id =?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Cliente cliente = new Cliente();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                cliente.setId(cursor.getInt(cursor.getColumnIndex("id")));
                cliente.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                cliente.setCpf(cursor.getString(cursor.getColumnIndex("cpf")));
                cliente.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
                cliente.setEmail(cursor.getString(cursor.getColumnIndex("email")));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return cliente;
    }

}