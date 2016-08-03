package com.auto.mm.database;

public class Contact {

        // private variables
        public int _id;
        public String _name;
        public String _six;
        

        public Contact() {
            }

        // constructor
        public Contact(int id, String name, String _six) {
                this._id = id;
                this._name = name;
                this._six = _six;
                

            }

        // constructor
        public Contact(String name, String _six) {
                this._name = name;
                this._six = _six;
                
            }

        // getting ID
        public int getID() {
                return this._id;
            }

        // setting id
        public void setID(int id) {
                this._id = id;
            }

        // getting name
        public String getName() {
                return this._name;
            }

        // setting name
        public void setName(String name) {
                this._name = name;
            }

        // getting phone number
        public String getWechatSix() {
                return this._six;
            }

        // setting phone number
        public void setWechatSix(String _Six) {
                this._six = _Six;
            }

 

    }
