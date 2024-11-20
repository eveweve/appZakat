package xml.zakatapp.models;

public class Zakat {
    private String zakatType;
    private double amount;
    private String date;

    public Zakat (){}

    public Zakat(String zakatType, double amount, String date){
        this.zakatType = zakatType;
        this.date = date;
        this.amount = amount;
    }

    public String getZakatType() { return zakatType; }
    public void setZakatType(String zakatType) { this.zakatType = zakatType; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
