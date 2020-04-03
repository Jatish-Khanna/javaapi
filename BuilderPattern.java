
import java.time.LocalDate;
/**
 * Transaction model representing attributes for each transaction
 * @author Jatish_Khanna
 *
 */

public class Transaction {

  private final String externalTransactionId;
  private final String clientId;
  private final String securityId;
  private final TransactionType transactionType;
  private final LocalDate transactionDate;
  private final Double marketValue;
  private final Boolean priority;

  public Transaction(TransactionBuilder transactionBuilder) {
    this.externalTransactionId = transactionBuilder.externalTransactionId;
    this.clientId = transactionBuilder.clientId;
    this.securityId = transactionBuilder.securityId;
    this.transactionType = transactionBuilder.transactionType;
    this.transactionDate = transactionBuilder.transactionDate;
    this.marketValue = transactionBuilder.marketValue;
    this.priority = transactionBuilder.priority;
  }

  public String getExternalTransactionId() {
    return externalTransactionId;
  }

  public String getClientId() {
    return clientId;
  }

  public String getSecurityId() {
    return securityId;
  }


  public TransactionType getTransactionType() {
    return transactionType;
  }


  public LocalDate getTransactionDate() {
    return transactionDate;
  }


  public Double getMarketValue() {
    return marketValue;
  }


  public Boolean getPriority() {
    return priority;
  }

  public static class TransactionBuilder {
    private String externalTransactionId;
    private String clientId;
    private String securityId;
    private TransactionType transactionType;
    private LocalDate transactionDate;
    private Double marketValue;
    private Boolean priority;

    private TransactionBuilder() {

    }

    public static TransactionBuilder instance() {
      return new TransactionBuilder();
    }

    public Transaction build() {
      return new Transaction(this);
    }

    public TransactionBuilder setExternalTransactionId(String externalTransactionId) {
      this.externalTransactionId = externalTransactionId;
      return this;
    }

    public TransactionBuilder setClientId(String clientId) {
      this.clientId = clientId;
      return this;
    }

    public TransactionBuilder setSecurityId(String securityId) {
      this.securityId = securityId;
      return this;
    }

    public TransactionBuilder setTransactionType(TransactionType transactionType) {
      this.transactionType = transactionType;
      return this;
    }


    public TransactionBuilder setTransactionDate(LocalDate transactionDate) {
      this.transactionDate = transactionDate;
      return this;
    }


    public TransactionBuilder setMarketValue(Double marketValue) {
      this.marketValue = marketValue;
      return this;
    }

    public TransactionBuilder setPriority(Boolean priority) {
      this.priority = priority;
      return this;
    }

  }
}
