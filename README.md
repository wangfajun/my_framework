# 常见的分布式事务解决方案：
* 2PC（Two-Phase Commit）
* 3PC（Three-Phase Commit）
* 本地消息表（消息表+消息队列+定时任务）
* LCN（Lock Confirm Notify）
* TCC（Try Confirm Cancel）
* 事务消息（基于RocketMQ）
* 最大努力通知型（微信、支付宝开放平台）
* 可靠消息服务（自研消息服务）
#### 本项目中包含4种解决方案，具体往下翻：
#
#项目结构：
## file目录
*  各服务初始化脚本（mysql）
##
## wangfajun-framework-pai:
*  公共组件
*  公用的类放在此项目下
##
## wangfajun-framework-core
*  业务demo,咱无使用场景，新服务可参照此服务搭建
##
## wangfajun-framework-eureka
*  注册中心
*  各服务注册到注册中心，可通过服务名调用
##
## wangfajun-framework-order
*  订单服务
*  模拟下订单，保存订单流水
*  结合支付服务、tm服务做分布式事务（本地消息表、TX-LCN、TX-TCC）
##
## wangfajun-framework-pay
*  支付服务
*  模拟支付、支付回调，保存支付流水
*  结合订单服务、tm服务,做本地消息表分布式事务、做第三方TX分布式事务（TX-LCN、TX-TCC）
##
## wangfajun-framework-point
*  积分服务
*  模拟给用户加积分
*  结合账户服务、seata-server做第三方seata的分布式事务
##
## wangfajun-framework-account
*  账户服务
*  模拟用户账户信息
*  结合积分服务、seata-server做第三方seata的分布式事务
##
## wangfajun-framework-tm
*  TX-LCN、TX-TCC分布式事务管理器(transaction manager)
##
## wangfajun-framework-seata-server
*  seata服务端配置
*  conf目录配置好eureka及数据库配置
*  bin目录启动配置
##
## wangfajun-framework-stock
*  库存服务
*  结合物流服务、做RocketMQ的分布式事务
##
## wangfajun-framework-logistics
*  物流服务
*  结库存流服务、做RocketMQ的分布式事务
##
## wangfajun-framework-distributed-lock
*  分布式锁demo
*  多种分布式锁实现方案，包含：jvm锁、数据库(mysql)锁、redis的setnx方式、redisson、redisson红锁
##
# 第三方分布式事务框架使用说明
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
> * 本地事件消息表+消息队列(RabbitMq交换机采用直接型、扇出型，死信队列补偿机制待完善)+定时任务
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









