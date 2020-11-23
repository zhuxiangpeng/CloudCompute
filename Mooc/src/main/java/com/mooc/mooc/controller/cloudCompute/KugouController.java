package com.mooc.mooc.controller.cloudCompute;

import com.mooc.mooc.model.MusicInfo;
import com.mooc.mooc.model.RankInfo;
import com.mooc.mooc.service.KugouService;
import com.mooc.mooc.vo.ResultVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/kugou")
public class KugouController {

    @Resource
    private KugouService kugouService;

    /**
     * 获取rankid=?榜单下的音乐列表
     */
    @RequestMapping("/getRankMusic/{rankid}")
    public ResultVO getRankMusic(@PathVariable Integer rankid) {
        ResultVO resultVO = new ResultVO();
        List<MusicInfo> list = new ArrayList<>();
        try {
            list = kugouService.getRankMusic(rankid);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setCode(2);
            resultVO.setMsg("调用api获取酷狗音乐" + rankid + "榜单下的音乐排行异常:" + e);
            return resultVO;
        }
        if (list.size() == 0) {
            resultVO.setCode(0);
            resultVO.setMsg("获取到的酷狗音乐" + rankid + "榜单下的音乐排行为空！");
            return resultVO;
        }
        resultVO.setCode(1);
        resultVO.setMsg("获取酷狗音乐" + rankid + "榜单下的音乐排行成功！");
        resultVO.setData(list);
        return resultVO;
    }

    /**
     * 获取最新榜单列表
     */
    @RequestMapping("/renovateRankList")
    public ResultVO renovateRankList() {
        ResultVO resultVO = new ResultVO();
        List<RankInfo> list = new ArrayList<>();
        try {
            list = kugouService.renovateRankList();
        } catch (IOException e) {
            e.printStackTrace();
            resultVO.setCode(2);
            resultVO.setMsg("调用api获取酷狗音乐最新榜单异常!");
            return resultVO;
        }
        if (list.size() == 0) {
            resultVO.setCode(0);
            resultVO.setMsg("获取到的酷狗音乐最新榜单为空！");
            return resultVO;
        }
        resultVO.setCode(1);
        resultVO.setMsg("获取酷狗音乐最新榜单成功！");
        resultVO.setData(list);
        return resultVO;
    }
}
