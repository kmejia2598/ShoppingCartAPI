package org.shoppingcart.service;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.component.MemoryDB;
import org.shoppingcart.dto.OrderDTO;
import org.shoppingcart.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final MemoryDB memoryDB;

    public OrderDTO addOrderMemory(OrderDTO order){
        return memoryDB.addOrder(order);
    }

    public Collection<OrderDTO> getAllOrdersMemory(){
        return memoryDB.getAllOrders();
    }

    public OrderDTO getOrderByIdMemory(Integer id){
        Optional<OrderDTO> order = memoryDB.getOrderById(id);
        if(order.isPresent()){
            return order.get();
        }else {
            throw new NotFoundException("Order with ID " + id + " not found");
        }
    }
}
