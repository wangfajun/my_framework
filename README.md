## file目录
*  各服务的脚本
##
## wangfajun-framework-pai:
*  公共组件
*  公用的类放在此项目下
##
## wangfajun-framework-core
*  业务demo,新服务可参照此服务搭建
##
## wangfajun-framework-eureka
*  注册中心
*  用于LCN、TCC分布式事务解决方案，将pay、order注册到注册中心，通过RestTemplate服务名调用
##
## wangfajun-framework-order
*  订单服务
*  模拟下订单，保存订单流水
##
## wangfajun-framework-pay
*  支付服务
*  模拟支付、支付回调，保存支付流水
##
## wangfajun-framework-point
*  积分服务
*  模拟给用户加积分
##
## wangfajun-framework-tm
*  TX-LCN、TX-TCC分布式事务管理器(transaction manager)
##
## wangfajun-framework-seata-server
*  seata服务端配置
##
## wangfajun-framework-stock
*  库存服务
##
## wangfajun-framework-logistics
*  物流服务
##
## 分布式事务说明
* tx-lcn框架
>官网：https://www.codingapi.com/docs/txlcn-preface
>
>github：https://github.com/codingapi/tx-lcn
>
* seata框架
>官网：http://seata.io/zh-cn/docs/overview/what-is-seata.html
>
>github：https://github.com/seata/seata
##
* 项目中包含4种解决方案
> * 本地事件消息表+消息队列(交换机采用直接型、扇出型，死信队列补偿机制待完善)+定时任务
>>OrderEventController、PayEventController
>>
>>com.wangfajun.framework.order.task.TxEventTask
>>
>>com.wangfajun.framework.pay.task.TxEventTask
>>
>>order、point服务的consumer包
>
>* TX-LCN、TX-TCC
>>PayLcnController、OrderLcnController
>>
>>PayTccController、OrderTccController
>>
>>启动pay、order之前，先启动tm服务
>
>* seata AT模式、TCC模式
>>AccountSeataController
>>
>>PointSeataController
>>
>>启动account、point服务前，先启动wangfajun-framework-seata-server中的bin下的文件
>
>* RocketMq事务消息
>>库存服务(stock)
>>>StockController
>>>
>>>com.wangfajun.framework.stock.config包
>>
>>物流服务(logistics)
>>>com.wangfajun.framework.logistics.consumer包









