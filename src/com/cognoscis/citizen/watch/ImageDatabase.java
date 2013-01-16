package com.cognoscis.citizen.watch;

public class ImageDatabase {
	
	//private variables
	
	int _id;
	String _regNo;
	String _vType;
	String _vDate;
	String _vTime;
	String _vPlace;
	String _uName;
	String _uContact;
	String _uEmail;
	String _uRemarks;
	String _imagePath;
	
	//Empty constructor
	public ImageDatabase(){
		
	}
	
	// constructor
	public ImageDatabase(int id, String regNo, String vType, String vDate, String vTime, String vPlace,
			String uName, String uContact, String uEmail, String uRemarks, String imagePath) {
		this._id = id;
		this._regNo = regNo;
		this._vType = vType;
		this._vDate = vDate;
		this._vTime = vTime;
		this._vPlace = vPlace;
		this._uName = uName;
		this._uContact = uContact;
		this._uEmail = uEmail;
		this._uRemarks = uRemarks;
		this._imagePath = imagePath;
	}
	
	// constructor
	public ImageDatabase(String regNo, String vType, String vDate, String vTime, String vPlace,
			String uName, String uContact, String uEmail, String uRemarks, String imagePath) {
		this._regNo = regNo;
		this._vType = vType;
		this._vDate = vDate;
		this._vTime = vTime;
		this._vPlace = vPlace;
		this._uName = uName;
		this._uContact = uContact;
		this._uEmail = uEmail;
		this._uRemarks = uRemarks;
		this._imagePath = imagePath;
	}
	
	// getting ID
    public int getID(){
        return this._id;
    }
    
    // setting id
    public void setID(int id){
        this._id = id;
    }
    
    // getting registration no
    public String getRegNo(){
        return this._regNo;
    }
    
    // setting registration no
    public void setRegno(String regNo){
        this._regNo = regNo;
    }
    
    // getting violation type
    public String getvType(){
        return this._vType;
    }
    
    // setting violation type
    public void setvType(String vType){
        this._vType = vType;
    }
    
    // getting violation date
    public String getvDate(){
        return this._vDate;
    }
    
    // setting violation date
    public void setvDate(String vDate){
        this._vDate = vDate;
    }
    
    // getting violation time
    public String getvTime(){
        return this._vTime;
    }
    
    // setting violation time
    public void setvTime(String vTime){
        this._vTime = vTime;
    }
    
    // getting violation place
    public String getvPlace(){
        return this._vPlace;
    }
    
    // setting violation place
    public void setvPlace(String vPlace){
        this._vPlace = vPlace;
    }
    
    // getting username
    public String getuName(){
        return this._uName;
    }
    
    // setting username
    public void setuName(String uName){
        this._uName = uName;
    }
    
    // getting contact
    public String getuContact(){
        return this._uContact;
    }
    
    // setting contact
    public void setuContact(String uContact){
        this._uContact = uContact;
    }
    
    // getting Email
    public String getuEmail(){
        return this._uEmail;
    }
    
    // setting Email
    public void setuEmail(String uEmail){
        this._uEmail = uEmail;
    }
    
    // getting Remarks
    public String getuRemarks(){
        return this._uRemarks;
    }
    
    // setting Remarks
    public void setuRemarks(String uRemarks){
        this._uRemarks = uRemarks;
    }
    
    // getting Image Path
    public String getImagePath(){
        return this._imagePath;
    }
    
    // setting Image Path
    public void setImagePath(String imagePath){
        this._imagePath = imagePath;
    }

}


