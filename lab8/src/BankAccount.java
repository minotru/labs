import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;

public class BankAccount implements Serializable {
    private final long id;
    private int code;
    private String ownerName;
    private Currency currency;
    private double amount;
    private double percent;
    private final Date creationDate;

    private boolean isIdValid(long id) {
        //return id > 0 && new Long(id).toString().length() == 13;
        return true;
    }

    public BankAccount(
            long id,
            String ownerName,
            int code,
            String currencyCode,
            double percent) {
        if (!isIdValid(id))
            throw new IllegalArgumentException("ID has illegal value");
        this.id = id;
        this.code = code;
        this.ownerName = ownerName;
        this.currency = Currency.getInstance(currencyCode.toUpperCase());
        this.amount = 0;
        setPercent(percent);
        this.creationDate = new Date();
    }

    public long getId() {
        return id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getCode() {
        return code;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmountOn(double amount) {
        this.amount += amount;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        if (percent < 0)
            throw new IllegalArgumentException("Percent can not be below 0.");
        this.percent = percent;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Account ID: " + getId() + "\n");
        builder.append("Owner name: " + getOwnerName() + "\n");
        builder.append("Account code:" + getCode() + "\n");
        builder.append("Currency: " + getCurrency().getDisplayName() + "\n");
        NumberFormat currencyFormat = NumberFormat.getInstance();
        //currencyFormat.setCurrency(getCurrency());
        String amountString = currencyFormat.format(getAmount());
        builder.append("Amount: " + amountString + "\n");
        String percentString = NumberFormat.getPercentInstance().format(getPercent());
        builder.append("Percent: " + percentString + "\n");
        String creationDateString = new SimpleDateFormat().format(getCreationDate());
        builder.append("Created on: " + creationDateString + "\n");
        return builder.toString();
    }
}
