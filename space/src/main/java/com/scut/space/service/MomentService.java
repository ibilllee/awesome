package com.scut.space.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scut.common.constant.MQConstant;
import com.scut.common.dto.request.MomentListParam;
import com.scut.common.dto.request.MomentParam;
import com.scut.common.dto.response.ArticleDto;
import com.scut.common.dto.response.InformDto;
import com.scut.common.dto.response.MomentDto;
import com.scut.space.entity.Moment;
import com.scut.space.entity.MomentLike;
import com.scut.space.feign.UserFeignService;
import com.scut.space.mapper.MomentCommentMapper;
import com.scut.space.mapper.MomentLikeMapper;
import com.scut.space.mapper.MomentMapper;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MomentService {
    @Resource
    private MomentMapper momentMapper;
    @Resource
    private MomentCommentMapper momentCommentMapper;
    @Resource
    private MomentLikeMapper momentLikeMapper;
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private UserFeignService userFeignService;

    @Transactional
    public MomentDto submit(MomentParam momentParam, Long userId) {
        Moment moment = new Moment(userId, momentParam);
        int count = momentMapper.insert(moment);
        if (count == 1) {
            return moment.getDto(userFeignService.getAvatarAndUsername(userId).getData());
        }
        return null;
    }

    public MomentDto get(long id) {
        Moment moment = momentMapper.selectById(id);
        if (moment == null)
            return null;
        return moment.getDto(userFeignService.getAvatarAndUsername(moment.getUserId()).getData());
    }

    public List<MomentDto> getList(MomentListParam momentListParam, Long userId) {
        Page<Moment> momentPage = new Page<>(momentListParam.getPage(), momentListParam.getSize());
        Page<Moment> page = new LambdaQueryChainWrapper<>(momentMapper)
                .exists(" SELECT 1 " +
                        " FROM user_follow " +
                        " WHERE (follow_user_id = moment.user_id AND user_id = " + userId + ") " +
                        " OR " + userId + " = moment.user_id ")
                .orderByDesc(Moment::getCreateTime)
                .page(momentPage);
        return momentsToMomentDtos(page.getRecords());
    }

    public List<MomentDto> getMyList(MomentListParam momentListParam, Long userId) {
        Page<Moment> momentPage = new Page<>(momentListParam.getPage(), momentListParam.getSize());
        Page<Moment> page = new LambdaQueryChainWrapper<>(momentMapper)
                .eq(Moment::getUserId, userId)
                .orderByDesc(Moment::getCreateTime)
                .page(momentPage);
        return momentsToMomentDtos(page.getRecords());
    }

    private List<MomentDto> momentsToMomentDtos(List<Moment> moments) {
        ArrayList<MomentDto> momentDtos = new ArrayList<>();
        for (Moment moment : moments) {
            momentDtos.add(moment.getDto(userFeignService.getAvatarAndUsername(moment.getUserId()).getData()));
        }
        return momentDtos;
    }

    public int delete(long id, long userId) {
        Moment moment = momentMapper.selectById(id);
        if (moment == null)
            return 0;
        if (moment.getUserId() != userId)
            return -1;
        return momentMapper.deleteById(id);
    }

    public int like(long id, Long userId) {
        Moment moment = momentMapper.selectById(id);
        if (moment == null)
            return -1;
        MomentLike momentLike = this.momentLikeMapper.selectOne(new QueryWrapper<MomentLike>().
                eq("moment_id", id)
                .eq("user_id", userId));
        if (momentLike == null) {
            momentLike = new MomentLike(0L, userId, id);
            if (momentLikeMapper.insert(momentLike) == 1) {
                momentLikeMapper.updateLikeCount(id, 1);

                InformDto informDto = new InformDto(moment.getUserId(),
                        "点赞通知",
                        "您的动态有新增点赞");
                rocketMQTemplate.convertAndSend(MQConstant.TOPIC_PUSH_INFORM, informDto);
                return 1;
            }else {
                return 0;
            }
        } else {
            return -2;
        }
    }

    public int unlike(long id, Long userId) {
        if(momentMapper.selectById(id)==null)
            return -1;
        MomentLike momentLike = this.momentLikeMapper.selectOne(new QueryWrapper<MomentLike>().
                eq("moment_id", id)
                .eq("user_id", userId));
        if (momentLike == null) {
            return -2;
        } else {
            if(momentLikeMapper.delete(new QueryWrapper<MomentLike>().
                    eq("moment_id", id)
                    .eq("user_id", userId))==1){
                momentLikeMapper.updateLikeCount(id, -1);
                return 1;
            }else {
                return 0;
            }
        }
    }

    public boolean isLike(long userId,long id){
        return momentLikeMapper.selectOne(new QueryWrapper<MomentLike>().
                eq("moment_id", id)
                .eq("user_id", userId))!=null;

    }

}
