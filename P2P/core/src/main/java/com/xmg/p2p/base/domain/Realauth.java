package com.xmg.p2p.base.domain;

import com.alibaba.druid.support.json.JSONUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Realauth extends BaseAuditDomain{
    public static final int SEX_MALE = 0;
    public static final int SEX_FEMALE = 1;

//实名认证内容
    private int sex;
    private String realName;
    private String bornDate;

    private String address;

    private String image1;

    private String image2;

    private String idNumber;


    public String getSexDisplay(){
        return sex == SEX_FEMALE?"女":"男";
    }

    public String getJsonString(){
        Map<String,Object> map = new HashMap<>();
        map.put("id",super.getId());
        map.put("applier",super.getApplier().getUsername());
        map.put("realName",realName);
        map.put("idNumber",idNumber);
        map.put("sex",this.getSexDisplay());
        map.put("bornDate",bornDate);
        map.put("address",address);
        map.put("image1",image1);
        map.put("image2",image2);
        return JSONUtils.toJSONString(map);

    }


    /**
     * 获取用户真实名字的隐藏字符串，只显示姓氏
     *
     * @param realName
     *            真实名字
     * @return
     */
    public String getAnonymousRealName() {
        if (StringUtils.hasLength(this.realName)) {
            int len = realName.length();
            String replace = "";
            replace += realName.charAt(0);
            for (int i = 1; i < len; i++) {
                replace += "*";
            }
            return replace;
        }
        return realName;
    }

    /**
     * 获取用户身份号码的隐藏字符串
     *
     * @param idNumber
     * @return
     */
    public String getAnonymousIdNumber() {
        if (StringUtils.hasLength(idNumber)) {
            int len = idNumber.length();
            String replace = "";
            for (int i = 0; i < len; i++) {
                if ((i > 5 && i < 10) || (i > len - 5)) {
                    replace += "*";
                } else {
                    replace += idNumber.charAt(i);
                }
            }
            return replace;
        }
        return idNumber;
    }

    /**
     * 获取用户住址的隐藏字符串
     *
     * @param currentAddress
     *            用户住址
     * @return
     */
    public String getAnonymousAddress() {
        if (StringUtils.hasLength(address) && address.length() > 4) {
            String last = address.substring(address.length() - 4,
                    address.length());
            String stars = "";
            for (int i = 0; i < address.length() - 4; i++) {
                stars += "*";
            }
            return stars + last;
        }
        return address;
    }





}