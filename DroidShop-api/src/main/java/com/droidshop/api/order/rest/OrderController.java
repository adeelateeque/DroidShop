package com.droidshop.api.order.rest;

import com.droidshop.api.AbstractController;


/*@Controller
@RequestMapping("/order")*/
public class OrderController extends AbstractController {
  /*  @Autowired
    OrderManager orderManager;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public Order addOrder(@RequestBody Order order) throws Exception {
        return orderManager.add(order);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) throws Exception {
        return orderManager.update(id, order);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Order fetchOrder(@PathVariable Long id) {
        return orderManager.fetch(id);
    }

    @RequestMapping(value = "/username/{userName}", method = RequestMethod.GET)
    @ResponseBody
    public Order fetchByUserName(@PathVariable String userName) {
        return orderManager.fetchByUserName(userName);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<Order> fetchAllOrders(@RequestParam(value = "include_all", required = false) boolean includeAll) {
        return orderManager.fetchAll(includeAll);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Order deleteOrder(@PathVariable Long id, @RequestBody Order order) throws Exception {
        return orderManager.delete(id, order);
    }
*/
}
