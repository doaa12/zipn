package lbs.baidu.test;

import java.util.ArrayList;
import java.util.List;

import cn.bmwm.modules.shop.entity.Shop;

/**
 * @author zby
 * 2014-9-12 下午9:56:05
 */
public class ShopListTest {

	public static void main(String[] args) {
		
		List<Shop> list = new ArrayList<Shop>();
		
		Shop shop1 = new Shop();
		shop1.setId(1L);
		
		Shop shop2 = new Shop();
		shop2.setId(2L);
		
		Shop shop3 = new Shop();
		shop3.setId(3L);
		
		list.add(shop1);
		list.add(shop2);
		list.add(shop3);
		
		System.out.println(list);
		
	}
	
}
