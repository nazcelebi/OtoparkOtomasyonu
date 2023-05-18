import java.sql.*;
import java.util.Scanner;

class Main {
    public static void main(String[] args) throws SQLException {
        musteriBilgileri();
    }

    public static Otopark musteriBilgileri() throws SQLException {
        Otopark s = new Otopark();
        Scanner ekrandanOku = new Scanner(System.in);
        String musteriAdi, musteriSoyadi, arac_tipi;
        int cikis_saat , giris_saat , musteri_id, id, ucret = 0;
        String kullanici = "naz";
        String sifre = "12345";
        int islem;

        System.out.print("Lütfen Kullanıcı Adınızı Giriniz : ");
        String kullaniciAdi = ekrandanOku.nextLine();

        System.out.print("Lütfen Şifrenizi Giriniz : ");
        String kullaniciSifre = ekrandanOku.nextLine();


        while (true) {
            if (kullaniciAdi.equals(kullanici) && (kullaniciSifre.equals(sifre))) {
                System.out.println("SİSTEME HOŞGELDİNİZ :) ");
                System.out.println("1 - Müşteri Giriş");
                System.out.println("2 - Müşteri Çıkış Saati Güncelleme");
                System.out.println("3 - Ücret Hesaplama");
                System.out.println("4 - İçeride Arabam Var");
                System.out.println("5- Çıkış Yap");

                System.out.print("Yapmak istediğim işlem : ");
                islem = ekrandanOku.nextInt();

                Scanner giris = new Scanner(System.in);

                if (islem == 1) {

                    System.out.print("Müşterinin adı : ");
                    musteriAdi = giris.nextLine();
                    s.setAdi(musteriAdi);

                    System.out.print("Müşterinin soyadı : ");
                    musteriSoyadi = giris.nextLine();
                    s.setSoyadi(musteriSoyadi);

                    System.out.println("Araç tipini giriniz : ");
                    System.out.println("1- Otomobil");
                    System.out.println("2- Kamyon");
                    System.out.println("3- Motorsiklet");
                    System.out.println("4- Çıkış Yap");
                    System.out.print("Araç tipi : ");
                    arac_tipi = giris.nextLine();
                    s.setArac_tipi(arac_tipi);

                    System.out.print("Giriş saatini giriniz : ");
                    giris_saat = giris.nextInt();
                    s.setGiris_saat(giris_saat);

                    System.out.println("Bilgiler Kaydedildi. ");
                    InsertData(s);
                }


                else if (islem == 2) {
                    System.out.print("Çıkış saatini giriniz : ");
                    cikis_saat = giris.nextInt();
                    s.setCikis_saat(cikis_saat);

                    if (s.getCikis_saat() < s.getGiris_saat()) {
                        System.out.println("--> HATALI BİLGİ . ÇIKIŞ SAATİ GİRİŞ SAATİNDEN KÜÇÜK OLAMAZ !!");
                        continue;
                    }

                    System.out.print("Güncellemek istediğiniz id'yi giriniz: ");
                    id = giris.nextInt();
                    s.setId(id);

                    UpdateData(s);
                    System.out.println("Kayıt Güncellendi ! ");
                }


                else if (islem == 3) {
                    System.out.print("hesaplamak istediğiniz id'yi giriniz : ");
                    id = giris.nextInt();
                    s.setId(id);

                    s.setUcret(ucret);
                    hesaplaUcret(s);
                    System.out.println("ücret hesaplandı ");
                }



                else if (islem == 4) {
                    System.out.print("Lütfen size daha önce verilen müşteri id'sini giriniz : ");
                    musteri_id = giris.nextInt();
                    s.setMusteri_id(musteri_id);

                    giris.nextLine();
                    System.out.println("Araç tipini giriniz ");
                    System.out.println("1 - Otomobil  ");
                    System.out.println("2 - Kamyon");
                    System.out.println("3 - Motorsiklet ");
                    System.out.print("Araç tipi : ");
                    arac_tipi = giris.nextLine();
                    s.setArac_tipi(arac_tipi);


                    System.out.print("Giriş saatini giriniz : ");
                    giris_saat = giris.nextInt();
                    s.setGiris_saat(giris_saat);

                    arac2(s);

                }


                else if (islem == 5) {
                    System.out.println("Programdan çıkılıyor...");
                    break;
                }


                else {
                    System.out.println("Geçersiz işlem seçildi . Lütfen tekrar deneyiniz !");
                }
            }
        }
        return s;
    }




    //GİRİŞTE ALINAN BİLGİLERİ GEREKLİ TABLOLARA EKLEYEN SORGU
    public static void InsertData(Otopark s) throws SQLException {
        Connection connection = null;
        DbHelper helper = new DbHelper();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = helper.getConnection();
            String MusteriTablosu = "INSERT INTO tbl_musteri (adi, soyadi) VALUES (?, ?)";
            String AracEklemeTablosu = "INSERT INTO tbl_arac (musteri_id, arac_tipi, giris_saat) VALUES (?, ?, ?)";

            statement = connection.prepareStatement("INSERT INTO tbl_musteri(adi, soyadi) VALUES(?, ?)");
            statement.setString(1, s.getAdi());
            statement.setString(2, s.getSoyadi());
            statement.executeUpdate();

            statement = connection.prepareStatement("INSERT INTO tbl_arac(musteri_id, giris_saat, arac_tipi) VALUES(LAST_INSERT_ID(), ?, ?)");
            statement.setInt(1, s.getGiris_saat());
            statement.setString(2, s.getArac_tipi());
            statement.executeUpdate();


        } catch (SQLException e) {
            helper.showErrorMessage(e);
        } finally {
            statement.close();
            connection.close();
        }
    }




    //ÇIKIŞ SAATİNİ GÜNCELLEYEN SORGU
    public static void UpdateData(Otopark s) throws SQLException {
        Connection connection = null;
        DbHelper helper = new DbHelper();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = helper.getConnection();
            try {
                connection = helper.getConnection();
                String sql = "update tbl_arac set cikis_saat=? where id=? ";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, s.getCikis_saat());
                statement.setInt(2, s.getId());

                int result = statement.executeUpdate();

            } catch (SQLException e) {
                helper.showErrorMessage(e);
            } finally {
                statement.close();
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void hesaplaUcret(Otopark s) throws SQLException {
        Connection connection = null;
        DbHelper helper = new DbHelper();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = helper.getConnection();
            ResultSet set;
            String veriOku = "select * from tbl_arac where id=? ";
            statement = connection.prepareStatement(veriOku);
            statement.setInt(1, s.getId());
            set=statement.executeQuery();
            while(set.next()){
                s.setGiris_saat(Integer.parseInt(set.getString(4)));
                s.setCikis_saat(Integer.parseInt(set.getString(5)));
                s.setArac_tipi(set.getString(3));
            }

            double toplamUcret;
            int ucret;
            switch (s.getArac_tipi()) {
                case "Motorsiklet":
                    ucret = 5;
                    break;
                case "kamyon":
                    ucret = 10;
                    break;
                case "Otomobil":
                default:
                    ucret = 7;
                    break;
            }
            long saatFarki = (s.getCikis_saat() - s.getGiris_saat());
            toplamUcret = saatFarki * ucret;
            s.setUcret(toplamUcret);

            String sql = "UPDATE tbl_arac SET ucret=? WHERE id=?";
            statement = connection.prepareStatement(sql);
            statement.setDouble(1, s.getUcret());
            statement.setInt(2, s.getId());
            statement.executeUpdate();
            System.out.println("______________işlem başarılı toplam alınacak ücret: "+ s.getUcret() +" TL dir");
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        } finally {
            statement.close();
            connection.close();
        }
    }


    public static void arac2(Otopark s) throws SQLException {
        Connection connection = null;
        DbHelper helper = new DbHelper();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = helper.getConnection();
            statement = connection.prepareStatement("INSERT INTO tbl_arac(musteri_id ,arac_tipi, giris_saat) VALUES(?, ?,?)");
            statement.setInt(1, s.getMusteri_id());
            statement.setString(2, s.getArac_tipi());
            statement.setInt(3, s.getGiris_saat());
            statement.executeUpdate();

        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        } finally {
            statement.close();
            connection.close();
        }
    }
}





















