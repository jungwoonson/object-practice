# Note

## 티켓 판매 프로그램

### 소프트웨어 모듈의 세 가지 목적
1. 실행 중에 제대로 동작한다.
2. 대부분의 모듈은 생명주기 동안 변경되기 때문에 쉽게 변경 가능해야한다.
3. 코드를 읽는 사람과 의사소통 되어야하기 때문에 모듈은 특별한 훈련 없이도 개발자가 쉽게 읽고 이해할 수 있어야 한다.

### 최초 티켓 판매 프로그램의 문제점

```java
public class Theater {

    private TicketSeller ticketSeller;

    public Theater(TicketSeller ticketSeller) {
        this.ticketSeller = ticketSeller;
    }

    public void enter(Audience audience) {
        if (audience.getBag().hasInvitation()) {
            Ticket ticket = ticketSeller.getTicketOffice().getTicket();
            audience.getBag().setTicket(ticket);
            return;
        }

        Ticket ticket = ticketSeller.getTicketOffice().getTicket();
        audience.getBag().minusAmount(ticket.getFee());
        ticketSeller.getTicketOffice().plusAmount(ticket.getFee());
        audience.getBag().setTicket(ticket);
    }
}
```
1. 관람객과 판매원이 소극장의 통제를 받는 수동적인 존재라는 점
   - 소극장이라는 제 3자가 초대장을 확인 하기 위해 관람객의 가방읠 마음대로 열어보고 돈을 가져간다.
   - 판매원의 허락 없이 소극장이 매표소의 티켓과 현금에 마음대로 접근하고 통제한다.
   - 우리의 상식과는 다르게 동작하기 때문에 코드를 읽는 사람과 의사소통하지 못한다.
2. 이 코드를 이해하기 위해서는 세부적인 내용들은 모두 기억하고 있어야한다.
   - Audience가 Bag을 가지고 있고, Bag안에는 현금과 티켓이 있고, TicketSeller가 TicketOffice에서 티켓을 판매하고, TicketOffice안에 돈과 티켓이 보관돼 있는 모든 사실을 동시에 기억하고 있어야한다.
3. 변경에 취약한 코드
   - 관람객이 가방을 들고 있다는 가정이 바뀌면 Audience, Theater 둘 다 수정해야한다.
   - 판매원, 또는 매표소의 세부 사실이 바뀌면 Theater도 변경되어야한다.
   - 지나친 의존성(dependency)을 가지고 있다. -> 결합도가 높다.

### 문제점 개선
> [티켓 판매 프로그램 개선 커밋](https://github.com/jungwoonson/object-practice/commit/132c7e9a6b7d64ba97a281162b3741824d4937d7)
1. Theater의 과도한 의존성 제거
   - enter()에서 TicketOffice에 접근하는 모드는 코드를 TicketSeller 내부로 숨긴다.
   - TicketSeller.sellTo() 메서드 추가하고 Theater.enter() 내부 로직을 이동시킨다.
   - TicketSeller에서 getTicketOffice()가 사라지며 TicketSeller의 세부사항이 감춰지고 접근이 제한되므로 **캡슐화(encapsulation)** 되었다.
2. Audience 캡슐화
   - sellTo()에 여전히 남아있는 Bag에 접근하는 모든 로직을 Audience 내부로 감추기 위해 buy()로 옮긴다.
   - Audience안의 Bag의 존재를 내부로 캡슐화 할 수 있다.

### 개선된 점
1. 의사소통 관점 개선
   - Audience와 TicketSeller는 자신이 가지고 있는 소지품을 스스로 관리한다.
2. 변경하기 쉬워짐
   - Audience나 TicketSeller의 내부 구현을 변경하더라도 Theater를 함께 변경할 필요가 없어졌다.

### 이를 통해 배운 것들
- Audience와 TicketSeller가 자신의 문제를 스스로 해결 하도록 내부로 옮겼을 뿐인데(캡슐화) 문제가 개선되며 응집도가 높아지고 결합도가 낮아졌다.
- 캡슐화하여 결합도는 낮추고 응집도를 높이면 유지보수하기 쉬워지는 코드가 만들어진다.
- 객체 내부 상태를 캡슐화하고 메시지를 통해서만 상호작용 하도록 만드는 것이 중요하다.

