package com.fisheradelakin.mycontacts;

import java.io.Serializable;

/**
 * Created by Fisher on 6/19/15.
 */
public class Contact implements Serializable {

    private String mName;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
