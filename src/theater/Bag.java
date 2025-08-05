package theater;

public class Bag {

    private long amount;
    private Invitation invitation;
    private Ticket ticket;

    // Bag의 인스턴스를 생성하는 시점에 제약을 강제한다
    // 이벤트에 당첨되지 않은 관람객의 가방안에는 현금만 있다
    public Bag(long amount) {
        this(null, amount);
    }

    // 이벤트에 당첨된 관람객의 가방 안에는 현금과 초대장이 들어있다
    public Bag(Invitation invitation, long amount) {
        this.invitation = invitation;
        this.amount = amount;
    }

    public boolean hasInvitation() {
        return invitation != null;
    }

    public boolean hasTicket() {
        return ticket != null;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public void minusAmount(Long amount) {
        this.amount = this.amount - amount;
    }

    public void plusAmount(Long amount) {
        this.amount = this.amount + amount;
    }
}
