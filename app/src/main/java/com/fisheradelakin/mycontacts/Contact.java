package com.fisheradelakin.mycontacts;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Fisher on 6/19/15.
 */
public class Contact implements Serializable {

    private String mName;
    public ArrayList<String> emails;
    public ArrayList<String> phoneNumbers;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
