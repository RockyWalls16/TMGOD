package com.gp5.projettutore.game.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class MapNameActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final EditText txtUrl = new EditText(this);
        txtUrl.setHint("col / lololol / pokemon / test / test_big / very_big / whale");
        new AlertDialog.Builder(this).setTitle("Map").setMessage("Enter a map name").setView(txtUrl)
                .setPositiveButton("Go !", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        MainActivity.mapName = txtUrl.getText().toString();
                        Intent intent = new Intent(MapNameActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }).show();
    }
}
