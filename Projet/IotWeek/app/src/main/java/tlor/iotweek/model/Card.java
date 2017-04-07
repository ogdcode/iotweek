package tlor.iotweek.model;

public class Card {

    private String cardId;
    private String cardName;

    public Card(String cardId, String cardName) {
        this.cardId = cardId;
        this.cardName = cardName;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
}
