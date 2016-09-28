package com.phongbui.android.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static private final int REQUEST_CODE_EDIT_ITEM = 20;

    static public final String KEY_ITEM_TEXT        = "Item_Text";
    static public final String KEY_ITEM_POSITION    = "Item_Position";

    private ArrayList<String> items = null;
    private ArrayAdapter<String> itemsAdapter = null;
    ListView lvItems = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems =  (ListView) findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        setupListViewListtener();
    }

    public void onAddItem(View v){
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    private void setupListViewListtener(){
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        items.remove(position);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }
        );

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra(KEY_ITEM_TEXT, items.get(position));
                i.putExtra(KEY_ITEM_POSITION, position);
                startActivityForResult(i, REQUEST_CODE_EDIT_ITEM);
            }
        });
    }

    private void readItems(){
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo.txt");

        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }catch (IOException e){
            items = new ArrayList<String>();
        }
    }

    private void writeItems(){
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo.txt");

        try {
            FileUtils.writeLines(todoFile, items);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_EDIT_ITEM) {
            String itemEdit = data.getExtras().getString(KEY_ITEM_TEXT);
            int itemPosition = data.getExtras().getInt(KEY_ITEM_POSITION, -1);

            if(itemPosition != -1){
                items.set(itemPosition, itemEdit);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
            }
        }
    }
}
