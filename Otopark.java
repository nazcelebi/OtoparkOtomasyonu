public class Otopark {

    private String adi;
    private String soyadi;
    private String arac_tipi;
    private int cikis_saat;
    private int giris_saat;
    private int id;
    private double ucret;
    private String plaka;
    private int musteri_id;





    public int getMusteri_id() {
        return musteri_id;
    }

    public void setMusteri_id(int musteri_id) {
        this.musteri_id = musteri_id;
    }





    public int getCikis_saat() {
        return cikis_saat;
    }

    public void setCikis_saat(int cikis_saat) {
        this.cikis_saat = cikis_saat;
    }




    public int getGiris_saat() {
        return giris_saat;
    }

    public void setGiris_saat(int giris_saat) {
        this.giris_saat = giris_saat;
    }




    public double getUcret() {
        return ucret;
    }

    public void setUcret(double ucret) {
        this.ucret = ucret;
    }





    public String getAdi() {
        return adi;
    }
    public void setAdi(String adi) {
        this.adi = adi;
    }





    public String getSoyadi() {
        return soyadi;
    }
    public void setSoyadi(String soyadi) {
        this.soyadi = soyadi;
    }





    public String getArac_tipi() {
        return arac_tipi;
    }
    public void setArac_tipi(String arac_tipi) {
        this.arac_tipi = arac_tipi;
    }




    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }




    public Otopark(int musteri_id,String adi, String soyadi, String arac_tipi, int giris_saat, int cikis_saat,double ucret,String plaka) {
        this.id= id;
        this.adi = adi;
        this.soyadi = soyadi;
        this.arac_tipi = arac_tipi;
        this.giris_saat = giris_saat;
        this.cikis_saat = cikis_saat;
        this.ucret = ucret;
        this.plaka=plaka;
        this.musteri_id= musteri_id;
    }

    public Otopark() {
    }

}
