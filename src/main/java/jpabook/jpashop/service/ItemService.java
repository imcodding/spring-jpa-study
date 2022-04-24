package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional // readOnly 면 안 됨.
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    /*
     # 변경 감지(Dirty Checking) => 트랜잭션 커밋 시점에 변경 감지
     - param 은 준영속성 상태의 엔티티, findItem 은 영속성 엔티티
     - 영속성 엔티티는 트랜잭션에 의해 커밋되면서 변경된 사항을 update 한다.
     - 원하는 속성만 변경 가능. 엔티티를 변경할 때는 항상 변경 감지 사용!!
     */
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }

    /*
     # 병합(Merge)
     - 준영속 상태의 엔티티를 영속 상태로 변경
     - 영속 엔티티의 값을 준영속 엔티티의 값으로 모두 교체
     - 트랜잭션 커밋 시점에 update
     - 주의! 병합을 사용하면 모든 속성이 변경된다 => 병합시 값이 없으면 null 로 update

    @Transactional
    void update(Item param) { //itemParam: 파리미터로 넘어온 준영속 상태의 엔티티
        Item findItem = em.merge(item);
    }

     */

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findItem(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
