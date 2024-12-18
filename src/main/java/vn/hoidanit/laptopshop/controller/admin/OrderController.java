package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.service.OrderService;

@Controller
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/admin/order")
    public String getDashboard(Model model, @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try {
            if(pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        Pageable pageable = PageRequest.of(page - 1, 2);
        Page<Order> orders = this.orderService.getAllOrders(pageable);
        List<Order> orderList = orders.getContent();
        model.addAttribute("orders", orderList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", orders.getTotalPages());
        return "admin/order/show";
    }

    @GetMapping("/admin/order/{id}")
    public String getOrderDetail(Model model, @PathVariable long id) {
        Optional<Order> orderOptional = this.orderService.getOrderById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            List<OrderDetail> orderDetails = order.getOrderDetails();
            model.addAttribute("orderDetails", orderDetails);
            model.addAttribute("order", order);
            model.addAttribute("id", id);
        }
        return "admin/order/detail";
    }

    @GetMapping("/admin/order/delete/{id}")
    public String deleteOrder(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        Order order = new Order();
        order.setId(id);
        model.addAttribute("newOrder", order);
        return "admin/order/delete";
    }

    @PostMapping("/admin/order/delete")
    public String deleteOrder(Model model, @ModelAttribute("newOrder") Order newOrder) {
        this.orderService.deleteOrderById(newOrder.getId());
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/update/{id}")
    public String getOrderUpdatePage(Model model, @PathVariable long id) {
        Optional<Order> orderOptional = this.orderService.getOrderById(id);
        if (orderOptional.isPresent()) {
            Order currentOrder = orderOptional.get();
            model.addAttribute("newOrder", currentOrder);
            model.addAttribute("id", id);
        }
        return "admin/order/update";
    }

    @PostMapping("/admin/order/update")
    public String handleUpdateOrder(@ModelAttribute("newOrder") Order order) {
        this.orderService.updateOrder(order);
        return "redirect:/admin/order";
    }
}
