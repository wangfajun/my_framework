## file目录
### 脚本

## wangfajun-framework-pai:
###公共组件

## wangfajun-framework-core
###业务demo

## wangfajun-framework-eureka
###注册中心

## wangfajun-framework-order
###订单服务

## wangfajun-framework-pay
###支付服务

## wangfajun-framework-point
###积分服务

## wangfajun-framework-tm
### lcn、tcc分布式事务管理器(transaction manager)

#

## 分布式事务模拟代码说明：
### 1.采用tx-lcn框架:
####https://www.codingapi.com/docs/txlcn-preface

###2.项目中分布式事务3种解决方案:
####(1)本地事件消息表+消息队列(交换机采用直接模式、扇出型)+定时任务:
#####OrderEventController
######PayEventController
######com.wangfajun.framework.order.task.TxEventTask
######com.wangfajun.framework.pay.task.TxEventTask
######order、point服务的consumer包

####(2)LCN:
######PayLcnController、OrderLcnController、配合tm服务、redis

####(3)TCC:
######PayLcnController、OrderLcnController、配合tm服务、redis、eueka服务









