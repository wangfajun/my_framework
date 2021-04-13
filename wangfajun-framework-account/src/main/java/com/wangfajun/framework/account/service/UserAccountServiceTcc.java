package com.wangfajun.framework.account.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * TCC账户服务层
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@LocalTCC
public interface UserAccountServiceTcc {

    @TwoPhaseBusinessAction(name = "userAccountTry" , commitMethod = "userAccountCommit" ,rollbackMethod = "userAccountRollback")
    String userAccountTry(BusinessActionContext businessActionContext);

    boolean userAccountCommit(BusinessActionContext businessActionContext);

    boolean userAccountRollback(BusinessActionContext businessActionContext);

}
