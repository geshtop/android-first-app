package com.geshtop.firstproject.Entities;

import java.util.Date;
import java.util.HashMap;

public class Travel {
    private String travelId ;
    private String ClientName;
    private String ClientPhone;
    private String clientEmail;

    private UserLocation travelLocation;

    private RequestType requesType;

    private Date travelDate;

    private Date arrivalDate;

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    private Date creationDate;


    private HashMap<String, Boolean> company;

    public String getTravelId() {
        return travelId;
    }

    public void setTravelId(String travelId) {
        this.travelId = travelId;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getClientPhone() {
        return ClientPhone;
    }

    public void setClientPhone(String clientPhone) {
        ClientPhone = clientPhone;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public UserLocation getTravelLocation() {
        return travelLocation;
    }

    public void setTravelLocation(UserLocation travelLocation) {
        this.travelLocation = travelLocation;
    }

    public RequestType getRequesType() {
        return requesType;
    }

    public void setRequesType(RequestType requesType) {
        this.requesType = requesType;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public HashMap<String, Boolean> getCompany() {
        return company;
    }

    public void setCompany(HashMap<String, Boolean> company) {
        this.company = company;
    }

    public Travel() {
        creationDate = new Date();
    }

    public boolean isValid() {
        //add validation
        return ClientName != null;
    }
}

