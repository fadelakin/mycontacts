package com.fisheradelakin.mycontacts;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactViewFragment extends ContractFragment<ContactViewFragment.Contract> {

    private int mColor;
    private Contact mContact;
    private int mPosition;
    private TextView mContactName;
    private FieldsAdapter mAdapter;

    public ContactViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact_view, container, false);

        // dynamic ratio for imageview
        // only works if the image is the exact width of the screen
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        int width = point.x;
        int height = point.y;

        RelativeLayout headerSection = (RelativeLayout) v.findViewById(R.id.contact_view_header);
        headerSection.setLayoutParams(new LinearLayout.LayoutParams(width, (int) (width * (9.0/16.0))));

        mContact = ContactList.getInstance().get(mPosition);
        mContactName = (TextView) v.findViewById(R.id.contact_view_name);

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.contact_view_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.contact_view_edit) {
                    getContract().selectEditPosition(mPosition);
                    return true;
                }
                return false;
            }
        });
        toolbar.inflateMenu(R.menu.menu_contact_view);

        ListView listView = (ListView) v.findViewById(R.id.contact_view_fields);
        mAdapter = new FieldsAdapter(mContact);
        listView.setAdapter(mAdapter);

        // get color palette from image
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dope);
        Palette palette = Palette.from(bitmap).generate();
        mColor = palette.getVibrantSwatch().getRgb();

        updateUI();

        return v;
    }

    private void updateUI() {
        mContactName.setText(mContact.getName());
        mAdapter.notifyDataSetChanged();
    }

    public void setPosition(int position) {
        mPosition = position;
        if(mAdapter != null) {
            mContact = ContactList.getInstance().get(position);
            mAdapter.setContact(mContact);
            updateUI();
        }
    }

    private class FieldsAdapter extends BaseAdapter {

        ArrayList<String> emails;
        ArrayList<String> phoneNumbers;

        FieldsAdapter(Contact contact) {
            setContact(contact);
        }

        public void setContact(Contact contact) {
            this.phoneNumbers = contact.phoneNumbers;
            this.emails = contact.emails;
        }

        @Override
        public int getCount() {
            return phoneNumbers.size() + emails.size();
        }

        @Override
        public Object getItem(int position) {
            if(isEmail(position)) {
                return emails.get(position - phoneNumbers.size());
            } else {
                return phoneNumbers.get(position);
            }
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.contact_view_field_row, parent, false);
            }

            String value = (String) getItem(position);

            TextView contactValue = (TextView) convertView.findViewById(R.id.contact_view_row_value);
            contactValue.setText(value);

            ImageView iv = (ImageView) convertView.findViewById(R.id.field_icon);
            if(isFirst(position)){
                if(isEmail(position)) {
                    iv.setImageResource(R.drawable.ic_email);
                } else {
                    iv.setImageResource(R.drawable.ic_call);
                }
            }

            // change color of icons
            iv.setColorFilter(mColor);

            return convertView;
        }

        private boolean isFirst(int position) {
            if(position == 0 || position == phoneNumbers.size()) {
                return true;
            }
            return false;
        }

        private boolean isEmail(int position) {
            if(position > phoneNumbers.size() - 1) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_contact_view, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public interface Contract {
        void selectEditPosition(int position);
    }

}
