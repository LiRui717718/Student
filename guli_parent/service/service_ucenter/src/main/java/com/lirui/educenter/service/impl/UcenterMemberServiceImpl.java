package com.lirui.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lirui.commonutils.JwtUtils;
import com.lirui.commonutils.MD5;
import com.lirui.educenter.entity.UcenterMember;
import com.lirui.educenter.entity.vo.RegisterVo;
import com.lirui.educenter.mapper.UcenterMemberMapper;
import com.lirui.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lirui.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-05-29
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Override
    public String login(UcenterMember member) {

        //判断电话号码是否为空
        String mobile = member.getMobile();
        String password = member.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(2001,"登录失败");

            }
        //根据phone查询数据库
        QueryWrapper<UcenterMember> qw = new QueryWrapper<>();
        qw.eq("mobile",mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(qw);
        if (ucenterMember == null) {
            throw new GuliException(2001,"数据库没查到登录失败");
            }
        String mobile1 = ucenterMember.getMobile();
        String password1 = ucenterMember.getPassword();
        //判断密码和手机号是否正确
        if (!MD5.encrypt(password).equals(password1)) {
            throw new GuliException(2001,"密码不对登录失败");
            }

       //判断是否为禁用的用户
        if (ucenterMember.getIsDisabled()) {
            throw new GuliException(2001,"禁用登录失败");
            }
        //登录成功
        //生成tocken字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(),ucenterMember.getNickname());
        return jwtToken;
    }

    @Override
    public void register(RegisterVo registerVo) {

        //获得页面中的值
        String password = registerVo.getPassword();
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String code = registerVo.getCode();

        //判断页面传输过来的值是否为空
        if(!StringUtils.isEmpty(password) || !StringUtils.isEmpty(nickname) || !StringUtils.isEmpty(mobile) || !StringUtils.isEmpty(code)){
            String redisCode = redisTemplate.opsForValue().get(mobile);
            if (!redisCode.equals(code)) {
                throw new GuliException(2001,"验证码不对");
            }
            QueryWrapper<UcenterMember> qw = new QueryWrapper<>();
            qw.eq("mobile",mobile);
            Integer integer = baseMapper.selectCount(qw);
            if (integer > 0) {
                throw new GuliException(2001,"已注册过");
            }
            String encrypt = MD5.encrypt(password);
            UcenterMember ucenterMember = new UcenterMember();
            BeanUtils.copyProperties(registerVo,ucenterMember);
            ucenterMember.setPassword(encrypt);
            baseMapper.insert(ucenterMember);
        }

    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> qw = new QueryWrapper<>();
        qw.eq("openid0",openid);
        UcenterMember ucenterMember = baseMapper.selectOne(qw);
        return ucenterMember;
    }
}
