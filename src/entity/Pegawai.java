package entity;

public class Pegawai {
    private String id_pegawai;
    private String first_name;
    private String last_name;
    private String address;
    private Departement departement;

    public Pegawai() {
    }

    public Pegawai(String id_pegawai, String first_name, String last_name, String address, Departement departement) {
        this.id_pegawai = id_pegawai;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.departement = departement;
    }

    public String getId_pegawai() {
        return id_pegawai;
    }

    public void setId_pegawai(String id_pegawai) {
        this.id_pegawai = id_pegawai;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }
}
