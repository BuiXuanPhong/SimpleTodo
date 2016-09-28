package com.phongbui.android.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static com.phongbui.android.simpletodo.MainActivity.KEY_ITEM_POSITION;
import static com.phongbui.android.simpletodo.MainActivity.KEY_ITEM_TEXT;

public class EditItemActivity extends AppCompatActivity {
    private EditText etEditItem = null;
    private int itemPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        String itemEdit = getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT);
        itemPosition = getIntent().getIntExtra(MainActivity.KEY_ITEM_POSITION, -1);

        if(itemPosition == -1){
            setResult(RESULT_CANCELED);
            finish();
        }

        etEditItem = (EditText) findViewById(R.id.etEditItem);
        etEditItem.setText(itemEdit);
    }

    public void onSave(View v){
        Intent data = new Intent();
        data.putExtra(MainActivity.KEY_ITEM_TEXT, etEditItem.getText().toString());
        data.putExtra(MainActivity.KEY_ITEM_POSITION, itemPosition);

        setResult(RESULT_OK, data);
        finish();
    }
}
