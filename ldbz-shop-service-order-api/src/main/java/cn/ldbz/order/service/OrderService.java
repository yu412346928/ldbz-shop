package cn.ldbz.order.service;


import cn.ldbz.pojo.LdbzResult;

/**
 */

public interface OrderService {
    /**
     * 提交订单
     *
     * @param userCookieValue   用户登录Cookie
     * @param cartCookieValue   购物车Cookie
     * @param addrId            用户地址id
     * @param noAnnoyance       运费险
     * @param paymentType       支付方式 1、货到付款，2、在线支付，3、微信支付，4、支付宝支付
     * @param shippingName      快递名称 固定顺丰速运
     * @return
     */
    LdbzResult generateOrder(String userCookieValue, String cartCookieValue, Integer addrId, Integer noAnnoyance, Integer paymentType, String orderId, String shippingName);
}
