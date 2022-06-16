package com.scut.space.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scut.common.constant.MQConstant;
import com.scut.common.dto.request.MomentCommentListParam;
import com.scut.common.dto.request.MomentCommentParam;
import com.scut.common.dto.response.InformDto;
import com.scut.common.dto.response.MomentCommentDto;
import com.scut.space.entity.Moment;
import com.scut.space.entity.MomentComment;
import com.scut.space.feign.UserFeignService;
import com.scut.space.mapper.MomentCommentMapper;
import com.scut.space.mapper.MomentMapper;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MomentCommentService {
    @Resource
    private MomentCommentMapper momentCommentMapper;
    @Resource
    private MomentMapper momentMapper;
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Resource
    private UserFeignService userFeignService;

    @Transactional
    public MomentCommentDto submit(MomentCommentParam momentCommentParam, long userId) {
        MomentComment momentComment = new MomentComment(userId, momentCommentParam);
        Long momentId = momentComment.getMomentId();

        Moment moment = momentMapper.selectById(momentId);
        if (moment == null)
            return new MomentCommentDto(-1);

        Long replyId = momentCommentParam.getReplyId();
        MomentComment replyComment = momentCommentMapper.selectById(replyId);
        if (replyId == null || momentCommentParam.getReplyId() == -1)
            momentComment.setReplyId(-1L);
        else if (replyComment == null)
            return new MomentCommentDto(-1);
        else if (replyComment.getReplyId() != -1)
            return new MomentCommentDto(-3);

        int count = momentCommentMapper.insert(momentComment);
        if (count == 1) {
            if (replyId != null && replyId != -1) {//二级评论
                momentCommentMapper.updateReplyCount(replyId, 1);//更新父评论的回复数
                InformDto informDto = new InformDto(replyComment.getUserId(),
                        "评论通知",
                        "你在动态" + moment.getContent() + "》中的评论有了新的回复");
                rocketMQTemplate.convertAndSend(MQConstant.TOPIC_PUSH_INFORM, JSON.toJSONBytes(informDto));
            } else {//一级评论
                InformDto informDto = new InformDto(
                        moment.getUserId(),
                        "评论通知",
                        "你的动态有新的评论");
                rocketMQTemplate.convertAndSend(MQConstant.TOPIC_PUSH_INFORM, JSON.toJSONBytes(informDto));
            }

            return momentComment.getDto(userFeignService.getAvatarAndUsername(userId).getData());
        }
        return null;
    }

    public MomentCommentDto get(long id) {
        MomentComment momentComment = momentCommentMapper.selectById(id);
        if (momentComment == null)
            return null;
        return momentComment.getDto(userFeignService.getAvatarAndUsername(momentComment.getUserId()).getData());
    }

    @Transactional
    public int delete(long id, long userId) {
        MomentComment momentComment = momentCommentMapper.selectById(id);
        if (momentComment == null)
            return 0;
        if (momentComment.getUserId() != userId)
            return -1;

        int count = momentCommentMapper.deleteByParentId(id);
        momentCommentMapper.deleteById(id);
        return count + 1;
    }

    public List<MomentCommentDto> list(MomentCommentListParam momentCommentListParam, long momentId) {
        Page<MomentComment> momentPage = new Page<>(momentCommentListParam.getPage(), momentCommentListParam.getSize());
        Page<MomentComment> page = new LambdaQueryChainWrapper<>(momentCommentMapper)
                .eq(MomentComment::getMomentId, momentId)
                .eq(MomentComment::getReplyId, -1L)
                .orderByDesc(MomentComment::getCreateTime)
                .page(momentPage);
        List<MomentComment> momentComments = page.getRecords();
        List<MomentCommentDto> momentCommentDtos = new ArrayList<>();
        for (MomentComment articleComment : momentComments) {
            momentCommentDtos.add(articleComment.getDto(userFeignService.getAvatarAndUsername(articleComment.getUserId()).getData()));
        }
        return momentCommentDtos;
    }

    public List<MomentCommentDto> listByParentId(MomentCommentListParam momentCommentListParam, long momentId, long replyId) {
        Page<MomentComment> momentPage = new Page<>(momentCommentListParam.getPage(), momentCommentListParam.getSize());
        Page<MomentComment> page = new LambdaQueryChainWrapper<>(momentCommentMapper)
                .eq(MomentComment::getMomentId, momentId)
                .eq(MomentComment::getReplyId, replyId)
                .orderByDesc(MomentComment::getCreateTime)
                .page(momentPage);
        List<MomentComment> momentComments = page.getRecords();
        List<MomentCommentDto> momentCommentDtos = new ArrayList<>();
        for (MomentComment articleComment : momentComments) {
            momentCommentDtos.add(articleComment.getDto(userFeignService.getAvatarAndUsername(articleComment.getUserId()).getData()));
        }
        return momentCommentDtos;
    }
}
