package com.web.store.mapper;

import com.web.store.dto.order.OrderDeleted;
import com.web.store.dto.order.OrderResponse;
import com.web.store.dto.purchase.PurchaseResponse;
import com.web.store.model.Order;
import com.web.store.model.Purchase;
import com.web.store.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
    @Mapping(target="userName", qualifiedByName="getFullName", source="order.user")
    @Mapping(target="userId", source="order.user.id")
    OrderResponse orderToOrderResponse(Order order);

    @Mapping(target="productId", source="purchase.product.id")
    @Mapping(target="productName", source="purchase.product.name")
    @Mapping(target="price", qualifiedByName="getPurchasePrice", source="purchase")
    PurchaseResponse purchaseToPurchaseResponse(Purchase purchase);

    @Mapping(target="userId", source="order.user.id")
    @Mapping(target="orderId", source="order.id")
    @Mapping(target="msg", qualifiedByName="getCancelMsg", source="order")
    OrderDeleted orderToOrderDeleted(Order order);

    @Named("getCancelMsg")
    default String getCancelMsg(Order order) {
        return "Order id " + order.getId() + " for user " + order.getUser().getId() + " has been cancelled successfully";
    }

    @Named("getPurchasePrice")
    default BigDecimal getPurchasePrice(Purchase purchase) {
        BigDecimal price = purchase.getProduct().getPrice();
        return price.multiply(new BigDecimal(purchase.getQuantity()));
    };

    @Named("getFullName")
    default String getFullName(User user) {
        return user.getFirstName() +" "+user.getLastName();
    }
}
