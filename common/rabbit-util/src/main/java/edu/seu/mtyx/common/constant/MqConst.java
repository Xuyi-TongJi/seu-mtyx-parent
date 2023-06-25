package edu.seu.mtyx.common.constant;

public class MqConst {

    /**
     * 消息补偿
     */
    public static final String MQ_KEY_PREFIX = "mtyx.mq:list";
    public static final int RETRY_COUNT = 3;

    /**
     * 商品上下架
     */
    public static final String EXCHANGE_GOODS_DIRECT = "mtyx.goods.direct";
    public static final String ROUTING_GOODS_UPPER = "mtyx.goods.upper";
    public static final String ROUTING_GOODS_LOWER = "mtyx.goods.lower";
    //队列
    public static final String QUEUE_GOODS_UPPER  = "mtyx.goods.upper";
    public static final String QUEUE_GOODS_LOWER  = "mtyx.goods.lower";

    /**
     * 团长上下线
     */
    public static final String EXCHANGE_LEADER_DIRECT = "mtyx.leader.direct";
    public static final String ROUTING_LEADER_UPPER = "mtyx.leader.upper";
    public static final String ROUTING_LEADER_LOWER = "mtyx.leader.lower";
    //队列
    public static final String QUEUE_LEADER_UPPER  = "mtyx.leader.upper";
    public static final String QUEUE_LEADER_LOWER  = "mtyx.leader.lower";

    //订单
    public static final String EXCHANGE_ORDER_DIRECT = "mtyx.order.direct";
    public static final String ROUTING_ROLLBACK_STOCK = "mtyx.rollback.stock";
    public static final String ROUTING_MINUS_STOCK = "mtyx.minus.stock";

    public static final String ROUTING_DELETE_CART = "mtyx.delete.cart";
    //解锁普通商品库存
    public static final String QUEUE_ROLLBACK_STOCK = "mtyx.rollback.stock";
    public static final String QUEUE_SECKILL_ROLLBACK_STOCK = "mtyx.seckill.rollback.stock";
    public static final String QUEUE_MINUS_STOCK = "mtyx.minus.stock";
    public static final String QUEUE_DELETE_CART = "mtyx.delete.cart";

    //支付
    public static final String EXCHANGE_PAY_DIRECT = "mtyx.pay.direct";
    public static final String ROUTING_PAY_SUCCESS = "mtyx.pay.success";
    public static final String QUEUE_ORDER_PAY  = "mtyx.order.pay";
    public static final String QUEUE_LEADER_BILL  = "mtyx.leader.bill";

    //取消订单
    public static final String EXCHANGE_CANCEL_ORDER_DIRECT = "mtyx.cancel.order.direct";
    public static final String ROUTING_CANCEL_ORDER = "mtyx.cancel.order";
    //延迟取消订单队列
    public static final String QUEUE_CANCEL_ORDER  = "mtyx.cancel.order";

    /**
     * 定时任务
     */
    public static final String EXCHANGE_DIRECT_TASK = "mtyx.exchange.direct.task";
    public static final String ROUTING_TASK_23 = "mtyx.task.23";
    //队列
    public static final String QUEUE_TASK_23  = "mtyx.queue.task.23";
}
