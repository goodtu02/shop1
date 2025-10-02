package com.shop.shop.repository;


import com.shop.shop.constant.ItemSellStatus;
import com.shop.shop.entity.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("상품명 생성 테스트")
    @BeforeEach
    public void createItemTest() {
        for (int i = 0; i < 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품 " + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명 " + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100 + i);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
            System.out.println(savedItem);
        }
    }

    @Test
    public void 상품명조회테스트() {
        List<Item> itemList = itemRepository.findByItemNm("테스트 상품 1");

        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    public void 상품명or상품상세설명테스트() {
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품 5", "테스트 상품 상세 설명 1");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByItemDetailTest() {
        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

}

class createitem{

}
