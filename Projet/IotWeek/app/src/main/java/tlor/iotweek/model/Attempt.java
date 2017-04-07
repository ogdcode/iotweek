package tlor.iotweek.model;

public class Attempt {

    private String cardId;
    private long timestamp;
    private boolean authorized;

    public Attempt(String cardId, long timestamp, boolean authorized) {
        this.cardId = cardId;
        this.timestamp = timestamp;
        this.authorized = authorized;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }
}
