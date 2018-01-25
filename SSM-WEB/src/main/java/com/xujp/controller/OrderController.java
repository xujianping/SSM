package com.xujp.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.xujp.domain.Order;
import com.xujp.domain.OrderExample;
import com.xujp.service.OrderService;
import com.xujp.utils.BaseResult;
import com.xujp.utils.Pages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Xujp on 2018/1/25.
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    private static final Logger log = LogManager.getLogger(OrderController.class);

    @Autowired
    OrderService orderService;
    /**
     * 跳转页面
     *
     * @return 页面名字
     */
    @RequestMapping("/list")//为方法设置访问路径  
    public String list(){
        return "order/list";
    }

    /***
     * 初始化基础数据，主要用于测试的
     * @param num
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "initTable/{num}", method = RequestMethod.POST)
    public BaseResult initTable(@PathVariable Integer num) {
        log.debug("初始化成功用户信息");
        boolean success = true;
        String msg = null;

        int count = 0;
        Random random = new Random();
        for (int i = 1; i <= num; i++) {
            Order order = new Order();
            order.setOrderno("20180114130"+ System.currentTimeMillis());
            String str1= String.valueOf((char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1)))); ;
            String str2= String.valueOf((char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1)))); ;
            String str3= String.valueOf((char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1)))); ;

            order.setCustomer(str1+str2+str3);
            System.out.println((int)((Math.random()*9+1)*10000));
            order.setPhoneno("182280"+(int)((Math.random()*9+1)*10000));
            order.setAddress("四川省成都市成华区东丽街"+random.nextInt(200)+"号！");
            order.setOrderstate("待收货");
//            order.setPayable(Float.valueOf("2.2"));
            order.setCreatetime(new Date());
            order.setOrdertime(new Date());
            count += orderService.insertOrder(order);
            log.debug(i);
        }

        BaseResult result = new BaseResult();
        if (count <= 0) {
            success = false;
            msg = "初始化失败!";
        }

        msg = "成功初始化" + count + "条数据!";
        result.setSuccess(success);
        result.setMsg(msg);

        return result;
    }

    /***
     * 修改或者插入数据
     * @param order
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addAndUpdateOrders/{type}", method = RequestMethod.POST)
    public BaseResult addAndUpdateOrders(Order order, @PathVariable String type){
        log.debug("添加或者修改一个订单信息");
        boolean success = true;
        String msg = "添加";
        int count = 0;
        if ("add".equals(type)){
            order.setOrderno("20180114130"+ 1);
            order.setCustomer("xujp");
            order.setPhoneno("1822804300"+1);
            order.setAddress("四川省成都市成华区东丽街138号！");
            order.setOrderstate("待收货");
            order.setPayable(Double.valueOf("22"));
            order.setCreatetime(new Date());
            order.setOrdertime(new Date());
            count=orderService.insertOrder(order);
        }else {
            msg = "修改";
            count=orderService.updateByPrimaryKeySelective(order);
        }
        BaseResult result = new BaseResult();
        if(count<=0){
            success = false;
            msg+="失败";
        }else {
            msg+="成功";
        }
        result.setSuccess(success);
        result.setMsg(msg);
        return result;
    }

    /**
     * 删除一个或者多个订单
     * @param type
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteOrder/{type}",method = RequestMethod.POST)
    public BaseResult deleteOrder(@PathVariable String type, HttpServletRequest request){
        log.debug("删除一个或多个订单信息");
        boolean success = true;
        String msg = null;
        String ids = request.getParameter("id");
        BaseResult result = new BaseResult();
        OrderExample example = null;

        if ("one".equals(type) && null != ids) {
            Integer id = Integer.parseInt(ids);
            example = new OrderExample();
            OrderExample.Criteria criteria = example.createCriteria();
            criteria.andIdEqualTo(id);
        } else if ("batch".equals(type) && null != ids) {
            List<Integer> delList = new ArrayList<>();
            String[] id = ids.split(",");
            for (String s : id) {
                delList.add(Integer.valueOf(s));
            }

            example = new OrderExample();
            OrderExample.Criteria criteria = example.createCriteria();
            criteria.andIdIn(delList);
        } else {
            orderService.deleteByExample(example);
            success = true;
            msg = "清空完毕!";
            result.setSuccess(success);
            result.setMsg(msg);
            return result;
        }

        if (example == null) {
            success = false;
            msg = "没有删除条件!";
            result.setSuccess(success);
            result.setMsg(msg);
            return result;
        }

        int count = orderService.deleteByExample(example);
        msg = "成功删除" + count + "条数据!";

        if (count <= 0) {
            success = false;
            msg = "删除失败!";
        }

        result.setSuccess(success);
        result.setMsg(msg);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/showOrder", method = RequestMethod.GET)
    public Pages<Order> getShowOrder(HttpServletRequest request) {
        return showOrder(request, "GET");
    }

    @ResponseBody
    @RequestMapping(value = "/showOrder", method = RequestMethod.POST)
    public Pages<Order> postShowOrder(HttpServletRequest request) {
        return showOrder(request, "POST");
    }

    /***
     * 查看列表
     * @param request
     * @param method
     * @return
     */
    public Pages<Order> showOrder(HttpServletRequest request, String method) {
        log.debug(method + "查询所有订单信息");
        String limit = request.getParameter("limit");
        String nowPage = request.getParameter("nowPage");
        String order = request.getParameter("order");
        String address = request.getParameter("address");
        String phoneNo = request.getParameter("phoneno");
        // 当前页数
        int nowPaged = Integer.parseInt(null == nowPage ? "1" : nowPage);
        // 每页显示页数
        int limitd = Integer.parseInt(null == limit ? "10" : limit);

        Pages<Order> pages = new Pages<>();
        //开始分页,参数1为请求第几页,参数2为请求条数
        PageHelper.startPage(nowPaged, limitd);

        //查询条件
        OrderExample example = new OrderExample();
        OrderExample.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmpty(address)) {
            criteria.andAddressLike("%" + address + "%");
        }
        if (StringUtil.isNotEmpty(phoneNo)) {
            criteria.andPhonenoEqualTo(phoneNo);
        }
        //查询结果
        List<Order> orderList = orderService.getAllOrdersByExample(example);

        //取记录总条数
        PageInfo<Order> pageInfo = new PageInfo<>(orderList);
        int total = (int) pageInfo.getTotal();

        pages.setSuccess(true);
        pages.setMsg("共查询出" + total + "条数据!");
        pages.setRecords(orderList);
        pages.setTotal(total);
        pages.setStatus(0);

        example.setOrderByClause(order);
        return pages;
    }

}
