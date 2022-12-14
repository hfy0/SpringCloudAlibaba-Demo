package com.example.orderservice.controller;

import com.example.orderservice.FeignClient.WarehouseServiceFeignClient;
import com.example.orderservice.dto.Stock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class OrderController {
    //利用@Resource将IOC容器中自动实例化的实现类对象进行注入
    @Resource
    private WarehouseServiceFeignClient warehouseServiceFeignClient;
    /**
     * 创建订单业务逻辑
     * @param skuId 商品类别编号
     * @param salesQuantity 销售数量
     * @return
     */
    @GetMapping("/create_order")
    public Map createOrder(Long skuId , Long salesQuantity){
        Map result = new LinkedHashMap();
        //查询商品库存，像调用本地方法一样完成业务逻辑。
        Stock stock = warehouseServiceFeignClient.getStock(skuId);
        System.out.println(stock);
        if(salesQuantity <= stock.getQuantity()){
            //创建订单相关代码，此处省略
            //CODE=SUCCESS代表订单创建成功
            result.put("code" , "SUCCESS");
            result.put("skuId", skuId);
            result.put("message", "订单创建成功");
        }else{
            //code=NOT_ENOUGN_STOCK代表库存不足
            result.put("code", "NOT_ENOUGH_STOCK");
            result.put("skuId", skuId);
            result.put("message", "商品库存数量不足");
        }
        return result;
    }
}
