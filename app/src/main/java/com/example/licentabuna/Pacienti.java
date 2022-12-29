package com.example.licentabuna;

public class Pacienti {
    private String numeP;
    private String prenP;
    private String cnpP;
    private String ageP;
    private String simpP;
    private String qrcode;


    public Pacienti(){

    }

    public Pacienti(String numeP,String prenP,String cnpP,String ageP,String simpP,String qrcode)
    {
        this.numeP=numeP;
        this.prenP=prenP;
        this.cnpP=cnpP;
        this.ageP=ageP;
        this.simpP=simpP;
        this.qrcode=qrcode;


    }
    public String getNumeP(){
        return numeP;
    }
    public String getPrenP(){
        return prenP;
    }
    public String getCnpP(){
        return cnpP;
    }
    public String getAgeP(){
        return ageP;
    }
    public String getSimpP(){
        return simpP;
    }
    public String getQrcode(){
        return qrcode;
    }
}
