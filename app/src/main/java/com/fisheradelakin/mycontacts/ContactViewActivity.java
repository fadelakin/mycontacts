package com.fisheradelakin.mycontacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactViewActivity extends AppCompatActivity implements ContactViewFragment.Contract {

    public static final String EXTRA = "CVA_Contact";
    private static final String TAG = "ContactViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);

        if(getFragmentManager().findFragmentById(R.id.view_fragment_container) == null) {
            ContactViewFragment contactViewFragment = new ContactViewFragment();
            contactViewFragment.setPosition(getIntent().getIntExtra(EXTRA, 0));
            getFragmentManager().beginTransaction()
                    .add(R.id.view_fragment_container, contactViewFragment)
                    .commit();
        }
    }

    @Override
    public void selectEditPosition(int position) {
        Intent i = new Intent(this, ContactEditActivity.class);
        i.putExtra(ContactEditActivity.EXTRA, position);
        startActivity(i);
    }
}
