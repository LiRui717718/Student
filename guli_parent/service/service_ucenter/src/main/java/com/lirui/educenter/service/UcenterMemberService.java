package com.lirui.educenter.service;

import com.lirui.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lirui.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-05-29
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid);
}
