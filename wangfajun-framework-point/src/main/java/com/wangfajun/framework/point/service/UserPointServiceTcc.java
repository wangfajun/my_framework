package com.wangfajun.framework.point.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * TCC积分服务层
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@LocalTCC
public interface UserPointServiceTcc {

    @TwoPhaseBusinessAction(name = "pointTry" , commitMethod = "pointCommit" ,rollbackMethod = "pointRollback")
    String pointTry(BusinessActionContext businessActionContext);

    boolean pointCommit(BusinessActionContext businessActionContext);

    boolean pointRollback(BusinessActionContext businessActionContext);

}
