package com.lirui.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lirui.educms.entity.CrmBanner;
import com.lirui.educms.mapper.CrmBannerMapper;
import com.lirui.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-05-25
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    @Cacheable(value = "banner" , key = "'selectIndexList'") //第一次进行查数据库，然后将数据放到缓存中，下一次就可以在redis中查询了
    public List<CrmBanner> selectAllBanner() {
        //根据id进行降序排序，显示排序之后前两条记录
        QueryWrapper<CrmBanner> qw = new QueryWrapper<>();
        qw.orderByDesc("id");
        //拼接sql语句
        qw.last("limit 2");
        List<CrmBanner> list = baseMapper.selectList(null);
        return list;
    }

}
