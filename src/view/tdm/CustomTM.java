package view.tdm;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CustomTM {
    private LocalDate date;
    private BigDecimal income;

    public CustomTM() {
    }

    public CustomTM(LocalDate date, BigDecimal income) {
        this.date = date;
        this.income = income;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    @Override
    public String toString() {
        return "CustomTM{" +
                "date=" + date +
                ", income=" + income +
                '}';
    }
}
