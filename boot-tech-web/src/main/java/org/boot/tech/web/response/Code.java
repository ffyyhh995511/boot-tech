package org.boot.tech.web.response;

/**
 * Created by jzl on 17/2/28.
 */
public class Code {
    
    public enum codeData{

        REQUESTSUCCESS("1"),

        REQUESTFAILED("0"),

        PARAMLOST("1111"),
        PARAMERROR("1112"),
        OUT_OF_COUPON("1113"),
        DEPOSITING("2011"),
        REFUNDDEPOSITE("2010"),
        TEMPPARKING("2012"),
        APPOINTMENTED("2009"),
        NOUSEABLE("2008"),
        POWERTOOLOW("2007"),
        NOBIKELISTINFO("2006"),
        NOAVAILABLEAMOUNT("2005"),
        CREDITTOOLOW("2004"),
        ACCOUNTFROZEN("2003"),
        NOREALAUTH("2002"),
        NODEPOSIT("2001"),
        REPEATENDORDER("3001"),
        AGEINVALID("3002"),
        SYSTEMERROR("9998"),
        //40用户异常信息
        USERNOTEXIST("40001"),
        //50苏宁异常信息
        SUNING_PARAM_EMPTY("50001"),
        SUNING_PHONE_INVALID("50002"),
        SUNING_CARD_BATCH_NO_ERROR("50003"),
        SUNING_CARD_RECEIVE_MAX_COUNT("50004"),
        SUNING_ACTIVITY_OFFINE("50005"),
        SUNING_CARD_NUMBER_LIMIT("50006"),
        SUNING_REAL_NAME_ERROR("50007"),

        USER_DEFINED("9999");

        public String code;
        private codeData(String code){
            this.code = code;
        }

    }
    public enum msg{
        REQUESTSUCCESS("成功"),
        REQUESTFAILED("失败"),
        OUT_OF_COUPON("券领完了"),
        TEMPPARKING("临时停车中，请使用附近小鸣"),
        APPOINTMENTED("该单车正在被使用 "),
        NOUSEABLE("我是故障车，请使用附近的小鸣"),
        POWERTOOLOW("电池电量过低，请换其它车辆"),
        NOBIKELISTINFO("该车辆没有录入，请换其它的车"),
        NOAVAILABLEAMOUNT("余额不足，请充值"),
        CREDITTOOLOW("信用分过低，不能使用车"),
        REFUNDDEPOSITE("押金退款中，不能使用车"),
        DEPOSITING("押金缴纳中，请稍后再试"),
        ACCOUNTFROZEN("账户冻结中，不能使用车"),
        NOREALAUTH("请先实名认证"),
        NODEPOSIT("请先缴纳押金"),
        REPEATENDORDER("订单已结束，请勿重复提交"),
        AGEINVALID("年龄不合要求"),
        SYSTEMERROR("系统异常，请联系管理员"),
        USERNOTEXIST("用户不存在！"),

        SUNING_PARAM_EMPTY("参数为空!"),
        SUNING_PHONE_INVALID("手机号码格式不对!"),
        SUNING_CARD_BATCH_NO_ERROR("卡批次号不对！"),
        SUNING_ACTIVITY_OFFINE("该活动已结束或未开始"),
        SUNING_CARD_NUMBER_LIMIT("您的卡发放数量已达上限!"),
        SUNING_CARD_RECEIVE_MAX_COUNT("该用户已达最大领取次数，无法再领取！"),
        SUNING_REAL_NAME_ERROR("实名认证失败，该身份证已被其它帐号使用!"),
        USER_DEFINED("%s");

        public String desc;
        private msg(String desc){
            this.desc = desc;
        }
    }
}
