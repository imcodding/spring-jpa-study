package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    public Long order(Long memberId, Long itemId, int count) {

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void orderCancel(Long orderId) {

        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);

        //주문 취소
        order.cancel();
        /*
         jpa 장점! order.cancel() 을 따라가보면,
         OrderStatus 알아서 update 해주고 stockQuantity 알아서 update 해줌.
         따로 update 쿼리문을 실행할 필요없음. mybatis 경우는 쿼리문 또 실행해야 함.
         */

        /*
         #도메인 모델 패턴
         - 엔티티가 비지니스 로직을 가지고 객체 지향의 특성을 적극 활용하는 것.
         - 주로 도메인 모델 패턴을 사용함.

         #트랜잭션 스크립트 패턴
         - 엔티티에는 비지니스 로직이 거의 없고 서비스 계층에서 대부분 비지니스 로직을 처리하는 것.
        */
    }
}
