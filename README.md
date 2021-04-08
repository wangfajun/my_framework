### file目录
*  存放脚本
*  各个服务的脚本在此
##
### wangfajun-framework-pai:
*  公共组件
*  公用的类放在此项目下
##
### wangfajun-framework-core
*  业务demo
##
### wangfajun-framework-eureka
*  注册中心
*  用于LCN、TCC分布式事务解决方案，将pay、order注册到注册中心，通过RestTemplate服务名调用
##
### wangfajun-framework-order
*  订单服务
*  模拟下订单，保存订单流水
##
### wangfajun-framework-pay
*  支付服务
*  模拟支付、支付回调，保存支付流水，
##
### wangfajun-framework-point
*  积分服务
*  模拟给用户加积分
##
### wangfajun-framework-tm
*  lcn、tcc分布式事务管理器(transaction manager)
#
## 分布式事务说明：
* 采用tx-lcn框架:
>官网：https://www.codingapi.com/docs/txlcn-preface
>
>github：https://github.com/codingapi/tx-lcn
##
* 项目中3种解决方案:
> * 本地事件消息表+消息队列(交换机采用直接模式、扇出型)+定时任务:
>>OrderEventController
>>
>>PayEventController
>>
>>com.wangfajun.framework.order.task.TxEventTask
>>
>>com.wangfajun.framework.pay.task.TxEventTask
>>
>>order、point服务的consumer包
>
>* LCN:
>>PayLcnController
>>
>>OrderLcnController
>>
>>配合tm服务
>>
>>redis
>
>* TCC:
>>PayLcnController
>>
>>OrderLcnController
>>
>>配合tm服务、eueka服务
>>redis









