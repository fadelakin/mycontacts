package com.fisheradelakin.mycontacts;

import java.util.ArrayList;

/**
 * Created by Fisher on 6/20/15.
 */
public class ContactList extends ArrayList<Contact> {

    private static ContactList sInstance = null;

    public static ContactList getInstance() {
        if(sInstance == null) {
            sInstance = new ContactList();
        }
        return sInstance;
    }

    private ContactList() {
    }
}
